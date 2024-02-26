package com.alidemirci.dontbreakthechain;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alidemirci.dontbreakthechain.databinding.FragmentAccountAndSettingsPageBinding;
import com.alidemirci.dontbreakthechain.databinding.FragmentFirstBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

@RequiresApi(Build.VERSION_CODES.M)
public class AccountAndSettingsPage extends Fragment {

    FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3,floatingActionButton4;
    Button button1,button2;
    int id=1;
    ImageView imageView1,imageView2;
    TextView textView;
    Bitmap selectedImage;
    SQLiteDatabase database;
    ActivityResultLauncher<Intent> activityResultLauncher; //Galeriye gitmek için
    ActivityResultLauncher<String> permissionLauncher; //izni istemek için
    private FragmentAccountAndSettingsPageBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerLauncher();

        try{
            database = requireContext().openOrCreateDatabase("Photos", Context.MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS photos (id INTEGER, image BLOB)");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentAccountAndSettingsPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1=binding.button;
        button2=binding.button2;

        floatingActionButton1=binding.floatingActionButton1;
        floatingActionButton2=binding.floatingActionButton2;
        floatingActionButton3=binding.floatingActionButton3;
        floatingActionButton4=binding.floatingActionButton4;

        imageView1=binding.imageViewSelectPhoto;
        imageView2=binding.imageView15;

        textView=binding.textViewMagaza;

        Cursor cursor = database.rawQuery("SELECT * FROM photos",null);

        int imageIx = cursor.getColumnIndex("image");

        while (cursor.moveToNext()) {
            byte[] imageData2 = cursor.getBlob(imageIx);

            // imageData byte dizisi üzerinden Bitmap oluşturdum
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData2, 0, imageData2.length);

            // Oluşturulan Bitmap'i ImageView'a set ettim
            imageView1.setImageBitmap(bitmap);
        }

        cursor.close();


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton1(view);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton2(view);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton3(view);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1(view);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2(view);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage(view);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOrText(view);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOrText(view);
            }
        });

    }

    public Bitmap makeSmallerImage(Bitmap image123,int maximumSize){ //sqlite'a 1mb'tan büyük fotolar kaydedemediğimizden dolayı resmi küçültme metodu yazdık ve boyutunu küçülttük

        int width = image123.getWidth();
        int height = image123.getHeight();

        float bitmapRatio =(float) width / (float) height;

        if(bitmapRatio>1){
            //landscape image
            width=maximumSize;
            height=(int) (width/bitmapRatio);
        }else{
            //portrait image
            height=maximumSize;
            width = (int) (height*bitmapRatio);
        }

        return image123.createScaledBitmap(image123,width,height,true);
    }

    public void selectImage(View view){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_MEDIA_IMAGES)){//Kullanıcı hayır demiş, ona açıklama yapıp neden istediğimizi göstercez. Bunun devreye girip girmeyeceğine android sistemi karar veriyor. Kullanıcı ikna olmamış, buna mantığı gösterelim diyor
                    //Eğer izin verilmemiş. İzin iste
                    Snackbar.make(view,"Galeri için izin gerekli!",Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //izin verilmiş. Galeriye git
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                        }
                    }).show();
                }else{
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }


            }else{
                //izin verilmiş. Galeriye git
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);

            }
        }else{
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){//Kullanıcı hayır demiş, ona açıklama yapıp neden istediğimizi göstercez. Bunun devreye girip girmeyeceğine android sistemi karar veriyor. Kullanıcı ikna olmamış, buna mantığı gösterelim diyor
                    //Eğer izin verilmemiş. İzin iste
                    Snackbar.make(view,"Galeri için izin gerekli!",Snackbar.LENGTH_INDEFINITE).setAction("İzin ver", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //izin verilmiş. Galeriye git
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);


                        }
                    }).show();
                }else{
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }


            }else{
                //izin verilmiş. Galeriye git
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);

            }
        }
    }

    private void registerLauncher(){ //Activity result launcherlara ne olacağını, onlarla ne yapacağımızı burada tanımlayacağım

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intentFromResult = result.getData();
                    if (intentFromResult != null) {
                        Uri imageData = intentFromResult.getData();

                        // CropImage kütüphanesini başlatmadan önce kontrol yapın
                        CropImage.activity(imageData)
                                .start(requireActivity(), AccountAndSettingsPage.this);

                    } else {
                        // intentFromResult null ise bir hata durumu oluştuğunda olacaklar
                    }
                } else {
                    // Kullanıcı işlemi iptal etti veya hata oluştuğunda olacaklar
                }
            }
        });


        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    //izin verildi
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);


                }else{
                    //izin verilmedi
                    Toast.makeText(getActivity(),"Lütfen izin veriniz!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                // Aldığımız resmi bitmap'e çevirip database'de tutulabilir hale getireceğiz.
                try {
                    if (Build.VERSION.SDK_INT >= 28) {
                        ImageDecoder.Source source = ImageDecoder.createSource(requireActivity().getContentResolver(), resultUri);
                        selectedImage = ImageDecoder.decodeBitmap(source);
                        imageView1.setImageBitmap(selectedImage);

                        Bitmap smallImage = makeSmallerImage(selectedImage,300);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //byte dizisine çevirmeye yarayan metod
                        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
                        byte[] byteArray = outputStream.toByteArray(); //görseli byte dizisine(1lere sıfırlara çevirmiş olduk). Neden bunu yaptık. Çünkü sqlite'a kaydetmek için birlere sıfırlara çevirmemiz gerekiyordu.

                        try{
                            String sqlString = "INSERT INTO photos(id,image) VALUES(?,?)";
                            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                            sqLiteStatement.bindLong(1, id);
                            sqLiteStatement.bindBlob(2, byteArray);
                            sqLiteStatement.execute();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                         if(id>1){
                             database.execSQL("DELETE FROM photos WHERE id=?", new String[]{String.valueOf(id-1)});
                         }
                        id++;


                    } else {
                        selectedImage = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), resultUri);
                        imageView1.setImageBitmap(selectedImage);

                        Bitmap smallImage = makeSmallerImage(selectedImage,300);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
                        byte[] byteArray = outputStream.toByteArray();


                        try{
                            String sqlString = "INSERT INTO photos(id,image) VALUES(?,?)";
                            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                            sqLiteStatement.bindLong(1, id);
                            sqLiteStatement.bindBlob(2, byteArray);
                            sqLiteStatement.execute();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if(id>1){
                            database.execSQL("DELETE FROM photos WHERE id=?", new String[]{String.valueOf(id-1)});
                        }
                        id++;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                // CropImage kütüphanesi hatayla sonuçlandı
                Exception error = result.getError();
                // Hata işlemlerini
            }
        }
    }



    public void floatingActionButton1(View view){
        NavDirections action=AccountAndSettingsPageDirections.actionAccountAndSettingsPageToFirstFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton2(View view){
        NavDirections action = AccountAndSettingsPageDirections.actionAccountAndSettingsPageToSecondFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton3(View view){
        NavDirections action= AccountAndSettingsPageDirections.actionAccountAndSettingsPageToThirdFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void button1(View view){
        NavDirections action = AccountAndSettingsPageDirections.actionAccountAndSettingsPageToFourthFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void button2(View view){
        NavDirections action = AccountAndSettingsPageDirections.actionAccountAndSettingsPageToSignInFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void imageOrText(View view){
        NavDirections action = AccountAndSettingsPageDirections.actionAccountAndSettingsPageToDonateAndRemoveAds();
        Navigation.findNavController(view).navigate(action);
    }

}