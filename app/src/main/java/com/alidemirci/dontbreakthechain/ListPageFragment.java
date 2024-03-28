package com.alidemirci.dontbreakthechain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.alidemirci.dontbreakthechain.databinding.FragmentListPageBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ListPageFragment extends Fragment {

    SQLiteDatabase database;
    FloatingActionButton floatingActionButton;
    Button button, button2;
    String hedef, rutin, info;
    int id;
    EditText editText1, editText2;
    Bundle arguments;
    private FragmentListPageBinding binding;
    PageViewModel pageViewModel;
    PageViewModel2 pageViewModel2;
    PageViewModel3 pageViewModel3;
    PageViewModel4 pageViewModel4;
    TextView myTextView;
    Handler handler;
    SimpleDateFormat dateFormat;
    SharedPreferences sharedPreferences;
    long id2;
    public static long longYolla;
    public static String stringYolla;
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "FirstFragment";
    AdRequest adRequest;
    PeriodicWorkRequest workRequest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = getActivity().getApplicationContext().openOrCreateDatabase("MyDatabase", Context.MODE_PRIVATE, null);

        pageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
        pageViewModel2 = new ViewModelProvider(requireActivity()).get(PageViewModel2.class);
        pageViewModel3 = new ViewModelProvider(requireActivity()).get(PageViewModel3.class);
        pageViewModel4 = new ViewModelProvider(requireActivity()).get(PageViewModel4.class);

        handler = new Handler(Looper.getMainLooper());

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        adRequest = new AdRequest.Builder().build(); //interstitial reklamı burada göstermek için yükleme yaptım

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InterstitialAd.load(requireActivity(),"ca-app-pub-1289222653932331/6610468707", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });

        floatingActionButton = binding.floatingActionButtonBack;

        editText1 = binding.editTextName;
        editText2 = binding.editTextRoutine;
        myTextView = binding.textView15;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonBack(view);
            }
        });

        button = binding.signInButton;

        arguments = getArguments();
        System.out.println("listadapter'dan listpagefragment'a veri geldi");



        if (arguments != null) {
            info = arguments.getString("info");
            id = arguments.getInt("id", 0);
            id2=arguments.getLong("id",0);

            if ("new".equals(info)) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hedef = editText1.getText().toString();
                        rutin = editText2.getText().toString();

                        if (hedef.isEmpty() || rutin.isEmpty()) {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.hedef_rutin), Toast.LENGTH_SHORT).show();
                        } else {
                            if(hedef.length()<=25){
                                save(view);
                                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.kaydedildi), Toast.LENGTH_SHORT).show();

                                if(workRequest!=null){

                                }else{
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(Calendar.HOUR_OF_DAY, 22);
                                    calendar.set(Calendar.MINUTE, 0);
                                    calendar.set(Calendar.SECOND, 0);

                                    long currentTime = System.currentTimeMillis();

                                    // Şu anki zamanı baz alarak initialDelay'i belirleme
                                    long initialDelay = calendar.getTimeInMillis() - currentTime;

                                    workRequest = new PeriodicWorkRequest.Builder(
                                            PushNotification.class,
                                            24,
                                            TimeUnit.HOURS
                                    )
                                            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                                            .build();

                                    WorkManager.getInstance(requireActivity()).enqueue(workRequest);
                                }

                                if (mInterstitialAd != null) {
                                    mInterstitialAd.show(requireActivity());
                                } else {
                                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                                }
                            }
                            else{
                                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.karakter), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            } else if ("old".equals(info)) {

                if(ListAdapter.a==1){


                    try {
                        Cursor cursor = database.rawQuery("SELECT * FROM mydatabase WHERE id = ?", new String[]{String.valueOf(id)});

                        int hedefIx = cursor.getColumnIndex("hedef");
                        int rutinIx = cursor.getColumnIndex("rutin");

                        while (cursor.moveToNext()) {
                            editText1.setText(cursor.getString(hedefIx));
                            editText2.setText(cursor.getString(rutinIx));
                        }

                        hedef = editText1.getText().toString();
                        rutin = editText2.getText().toString();

                        longYolla=(long)id;
                        pageViewModel4.setName(longYolla);
                        pageViewModel2.setName(hedef);

                        System.out.println("ListPageFragmenttaki id1: "+longYolla);
                        System.out.println("ListPageFragmenttaki hedef1: "+hedef);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hedef = editText1.getText().toString();
                                rutin = editText2.getText().toString();

                                if (hedef.isEmpty() || rutin.isEmpty()) {
                                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.hedef_rutin), Toast.LENGTH_SHORT).show();
                                } else {
                                    if(hedef.length()<=25){
                                        save(view);
                                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.kaydedildi), Toast.LENGTH_SHORT).show();

                                        if (mInterstitialAd != null) {
                                            mInterstitialAd.show(requireActivity());
                                        } else {
                                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                                        }
                                    }
                                    else{
                                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.karakter), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{


                    try {
                        Cursor cursor = database.rawQuery("SELECT * FROM mydatabase WHERE id = ?", new String[]{String.valueOf(id)});

                        int hedefIx = cursor.getColumnIndex("hedef");
                        int rutinIx = cursor.getColumnIndex("rutin");

                        while (cursor.moveToNext()) {
                            editText1.setText(cursor.getString(hedefIx));
                            editText2.setText(cursor.getString(rutinIx));
                        }

                        hedef = editText1.getText().toString();
                        rutin = editText2.getText().toString();

                        longYolla=(long)id;
                        pageViewModel4.setName(longYolla);
                        pageViewModel2.setName(hedef);

                        System.out.println("ListPageFragmenttaki id2: "+longYolla);
                        System.out.println("ListPageFragmenttaki hedef2: "+hedef);

                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                hedef = editText1.getText().toString();
                                rutin = editText2.getText().toString();

                                if (hedef.isEmpty() || rutin.isEmpty()) {
                                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.hedef_rutin), Toast.LENGTH_SHORT).show();
                                } else {
                                    if(hedef.length()<=25){
                                        save(view);
                                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.kaydedildi), Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.karakter), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    NavDirections action = ListPageFragmentDirections.actionListPageFragmentToSecondFragment();
                    Navigation.findNavController(view).navigate(action);

                }
            }
        }

        button2 = binding.deleteButton;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hedef = editText1.getText().toString();
                rutin = editText2.getText().toString();

                if (hedef.isEmpty() || rutin.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.hedef_rutin), Toast.LENGTH_SHORT).show();
                } else {
                    delete(view);
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.silindi), Toast.LENGTH_SHORT).show();

                    if (mInterstitialAd != null) {
                        mInterstitialAd.show(requireActivity());
                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }
                }
            }
        });


        if ("old".equals(info)) {
            showElapsedTime();
        }
    }

    public void floatingActionButtonBack(View view) {
        NavDirections action = ListPageFragmentDirections.actionListPageFragmentToThirdFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void save(View view) {
        if ("new".equals(info)) {
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS mydatabase(id INTEGER PRIMARY KEY, hedef VARCHAR, rutin VARCHAR, start_date TEXT)");
                String sqlString = "INSERT INTO mydatabase(hedef, rutin, start_date) VALUES(?, ?, ?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                sqLiteStatement.bindString(1, hedef);
                sqLiteStatement.bindString(2, rutin);
                sqLiteStatement.bindString(3, getCurrentDate());
                sqLiteStatement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                hedef = editText1.getText().toString();
                rutin = editText2.getText().toString();
                database.execSQL("UPDATE mydatabase SET hedef = ? WHERE id = ?", new String[]{hedef, String.valueOf(id)});
                database.execSQL("UPDATE mydatabase SET rutin = ? WHERE id = ?", new String[]{rutin, String.valueOf(id)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        NavDirections action = ListPageFragmentDirections.actionListPageFragmentToThirdFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void delete(View view) {
        database.execSQL("DELETE FROM mydatabase WHERE id=?", new String[]{String.valueOf(id)});
        NavDirections action = ListPageFragmentDirections.actionListPageFragmentToThirdFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private String getCurrentDate() { //Şu anki zamanı yıl-ay-gün şeklinde (date format sayesinde) string olarak veren bir fonksiyon
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    private void showElapsedTime() {
        try {
            Cursor cursor = database.rawQuery("SELECT start_date FROM mydatabase WHERE id = ?", new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String startDateString = cursor.getString(cursor.getColumnIndex("start_date")); //mevcut idli start_date column'ında ne yazıyorsa onu al

                Date startDate = dateFormat.parse(startDateString);
                Date currentDate = Calendar.getInstance().getTime();

                long elapsedTime = currentDate.getTime() - startDate.getTime();
                long days = TimeUnit.MILLISECONDS.toDays(elapsedTime);

                pageViewModel.setName(days);

                stringYolla=startDateString;
                pageViewModel3.setName(startDateString);//buradan takvime tarihi yolla ve orada veritipini değiştirip o günü maviyye boya

                myTextView.setText(getString(R.string.gecen_gun)+" " + days);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}