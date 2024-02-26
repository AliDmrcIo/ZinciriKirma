package com.alidemirci.dontbreakthechain;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;


import com.alidemirci.dontbreakthechain.databinding.FragmentFirstBinding;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class FirstFragment extends Fragment {
    TextView textVieww,textVieww2,textVieww3,textVieww4,textVieww5;
    Handler handler;
    Runnable runnable;
    FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3,floatingActionButton4,floatingActionButtonListe;
    private FragmentFirstBinding binding;
    PageViewModel pageViewModel;
    PageViewModel2 pageViewModel2;
    String sDegiskeni;
    long deger1,deger2;
    int arrayListDegeri;
    String degeriAlStringIcin;
    SharedPreferences sharedPreferences,sharedPreferences2,sharedPreferences3,sharedPreferences4;
    private static final String TAG = "FirstFragment";


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel= new ViewModelProvider(requireActivity()).get(PageViewModel.class); //Viewlarımıza diğer classlardan da ulaşabilmemizi sağlayan ViewModel'ımızı burada initialize etmiş olduk. Bunun initialize'ı diğer classlardan farklı olarak bu şekilde yapılıyor
        pageViewModel2 = new ViewModelProvider(requireActivity()).get(PageViewModel2.class);

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences2 = requireActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences3 = requireActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences4 = requireActivity().getPreferences(Context.MODE_PRIVATE);


        sDegiskeni = sharedPreferences.getString("syiAl", "Eklenen Hedefi Girin!");
        deger1 = sharedPreferences2.getLong("longuAl", 0);
        deger2 = sharedPreferences3.getLong("longuAl2", 0);
        degeriAlStringIcin = sharedPreferences4.getString("guzelSoz","\"Büyük sonuçlar, küçük sürekli çabaların birikimidir.\"");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        textVieww = binding.textView3;
        textVieww2 = binding.textView4;
        textVieww3 = binding.textView5;
        textVieww4=binding.textView;
        textVieww5=binding.textView2;

        String[] goodSentences = new String[25];
        goodSentences[0]=("\"Taşı delen suyun kuvveti değil, damlaların sürekliliğidir.\"");
        goodSentences[1]=("\"Büyük sonuçlar, küçük sürekli çabaların birikimidir.\"");
        goodSentences[2]=("\"Bir işe başlamak kolaydır; devam etmek ise çok zordur.\"");
        goodSentences[3]=("\"Yalnızca devam ve kararlılık her şeyi yapabilecek güce sahiptir.\"");
        goodSentences[4]=("\"İnsan sahip olduklarının toplamı değil, fakat henüz gerçekleştiremediklerinin toplamıdır.\"");
        goodSentences[5]=("\"Başarı bir yolculuktur, bir varış noktası değil.\"");
        goodSentences[6]=("\"Baş+arı: “Baş” olmak için “arı” gibi çalışmak gerekir.\"");
        goodSentences[7]=("\"Devamlılık, sadece bir rutini sürdürmek değil, aynı zamanda bu rutini anlamaktır.\"");
        goodSentences[8]=("\"Başarı, sürekli çaba ve azimle yakalanır.\"");
        goodSentences[9]=("\"Ne başarı bir sondur, ne de kaybetmek ölümcül; önemli olan devam etme cesaretidir.\"");
        goodSentences[10]=("\"Başarının yolu, konuşmayı bırakmak ve yapmaya başlamaktır.\"");
        goodSentences[11]=("\"Devler gibi eseler bırakmak için, karıncalar gibi çalışmak lazım.\"");
        goodSentences[12]=("\"Bahane değil, çare bulun.\"");
        goodSentences[13]=("\"Aradığını bilmeyen, bulduğunu anlayamaz.\"");
        goodSentences[14]=("\"Ders alınmış başarısızlık başarı demektir.\"");
        goodSentences[15]=("\"Fırsatlar durup dururken karşınıza çıkmaz, onları siz yaratırsınız.\"");
        goodSentences[16]=("\"Hiçbir şeyden vazgeçme, çünkü sadece kaybedenler vazgeçer.\"");
        goodSentences[17]=("\"Bundan bir yıl sonra, bugün başlamış olmayı dileyeceksin.\"");
        goodSentences[18]=("\"Durmadan devam ettiğin sürece, ne kadar yavaş gittiğinin bir önemi yoktur.\"");
        goodSentences[19]=("\"Kaybetmekten yılmayan, kazanmanın eşiğindedir.\"");
        goodSentences[20]=("\"Hiçbir zaman vazgeçmeyen bir insanı yenmek çok zordur.\"");
        goodSentences[21]=("\"Dünya, vazgeçenleri değil, azmedenleri hatırlar.\"");
        goodSentences[22]=("\"Gökkuşağına ulaşmak istiyorsan yağmura katlanmak zorundasın.\"");
        goodSentences[23]=("\"Eğer ortasında vazgeçerseniz, sonunu asla göremezsiniz.\"");
        goodSentences[24]=("\"Yarışı kazananlar, daima yavaş ve devamlı gidenlerdir.\"");

        textVieww5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textVieww5.setText(goodSentences[arrayListDegeri]);

                SharedPreferences.Editor editor4 = sharedPreferences4.edit();
                editor4.putString("guzelSoz", goodSentences[arrayListDegeri]);
                editor4.apply();

                arrayListDegeri+=1;

                if(arrayListDegeri>24){
                    arrayListDegeri=0;
                }

            }
        });
        textVieww5.setText(degeriAlStringIcin);


        floatingActionButton1=binding.floatingActionButton1;
        floatingActionButton2=binding.floatingActionButton2;
        floatingActionButton3=binding.floatingActionButton3;
        floatingActionButton4=binding.floatingActionButton4;
        floatingActionButtonListe=binding.floatingActionButtonListe;

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

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton4(view);
            }
        });

        floatingActionButtonListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonListe(view);
            }
        });

        pageViewModel.getName().observe(requireActivity(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                textVieww.setText(aLong.toString());
                textVieww3.setText(String.valueOf(365 -aLong));

                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.putLong("longuAl", aLong);
                editor2.apply();

                SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                editor3.putLong("longuAl2", 365-aLong);
                editor3.apply();

                pageViewModel2.getName().observe(requireActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        textVieww4.setText("-- "+s+" --");

                        SharedPreferences.Editor editor = sharedPreferences.edit(); //bu ve hemen yukarıdaki 2 tane daha sharedprefler gün geçtiğinde tekrar liste elemanına tıklanmazsa güncellenmiyor.
                        editor.putString("syiAl", s);
                        editor.apply();
                    }
                });
            }
        });

        handler=new Handler();
        runnable=new Runnable(){
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                LocalTime currentTime = null;

                textVieww4.setText("-- "+sDegiskeni+" --");
                textVieww.setText(String.valueOf(deger1));
                textVieww3.setText(String.valueOf(deger2));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentTime = LocalTime.now();
                }

                DateTimeFormatter formatter = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //formatter.format(currentTime)
                    textVieww2.setText((23-currentTime.getHour())+":"+(59-currentTime.getMinute())+":"+(59-currentTime.getSecond()));
                }
                handler.postDelayed(runnable,1000);
            }
        };
        handler.post(runnable);

        super.onViewCreated(view, savedInstanceState);
    }


    public void floatingActionButton2(View view){
        NavDirections action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton3(View view){
        NavDirections action = FirstFragmentDirections.actionFirstFragmentToThirdFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton4(View view){
        NavDirections action = FirstFragmentDirections.actionFirstFragmentToAccountAndSettingsPage();
        Navigation.findNavController(view).navigate(action);
    }

        public void floatingActionButtonListe(View view){
        NavDirections action = FirstFragmentDirections.actionFirstFragmentToListPage();
        Navigation.findNavController(view).navigate(action);
    }

}