package com.alidemirci.dontbreakthechain;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alidemirci.dontbreakthechain.databinding.FragmentSecondBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondFragment extends Fragment {
    MaterialCalendarView calendarView;
    FloatingActionButton floatingActionButton1,floatingActionButton3,floatingActionButton4,buttonTamamlandi, buttonTamamlanmadi;
    private FragmentSecondBinding binding;
    PageViewModel2 pageViewModel2;
    PageViewModel3 pageViewModel3;
    PageViewModel4 pageViewModel4;
    Calendar selectedDate,selectedCalendar,firstCalendar;
    CalendarDay selectorsCalendarDay,firstCalendarDay,selectedCalendarDay,pageViewModelCalendarDay,pageViewModelCalendarDay2;
    TextView textView;
    int day,month,year,turuncu,yesil,kirmizi,color,color2;
    Bundle arguments;
    String formattedDate1,formattedDate2,formattedDate3;
    RangeDecorator rangeDecorator1,rangeDecorator2,rangeDecorator3,rangeDecorator4;
    SQLiteDatabase database2;
    long aLong;
    int a,b;
    Cursor cursor,cursor2;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static final String TAG = "FirstFragment";
    AdRequest adRequest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageViewModel2 = new ViewModelProvider(requireActivity()).get(PageViewModel2.class);
        pageViewModel3 = new ViewModelProvider(requireActivity()).get(PageViewModel3.class);
        pageViewModel4 = new ViewModelProvider(requireActivity()).get(PageViewModel4.class);

        try{
            database2 = requireContext().openOrCreateDatabase("myseconddatabase", Context.MODE_PRIVATE, null);
            database2.execSQL("CREATE TABLE IF NOT EXISTS myseconddatabase(aLong INTEGER, formattedDate1 VARCHAR, formattedDate2 VARCHAR, color INTEGER)");

        }catch (Exception e){
            e.printStackTrace();
        }

        adRequest = new AdRequest.Builder().build(); //interstitial reklamı burada göstermek için yükleme yaptım

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mAdView = binding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        InterstitialAd.load(requireActivity(),"ca-app-pub-1289222653932331/8524665691", adRequest,
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

        floatingActionButton1=binding.floatingActionButton1;
        floatingActionButton3=binding.floatingActionButton3;
        floatingActionButton4=binding.floatingActionButton4;

        buttonTamamlandi=binding.buttonTamamlandi;
        buttonTamamlanmadi=binding.butonTamamlanmadi;

        calendarView=binding.calendarView;

        textView=binding.textView;

        firstCalendar = Calendar.getInstance();

        firstCalendarDay = CalendarDay.from( //Bugün'ün tarihi. Uygulama ilk açıldığında daha hedef'e tıklanmadığı zaman bugünün tarihi göstermek için
                firstCalendar.get(Calendar.YEAR),
                firstCalendar.get(Calendar.MONTH) + 1,
                firstCalendar.get(Calendar.DAY_OF_MONTH)
        );

        formattedDate3 = formatDate(firstCalendarDay);//Sqlite'a kaydedebilmek için string türüne çevirdik

        calendarView.setDateSelected(firstCalendarDay, true);

        buttonTamamlandi.setEnabled(false);
        buttonTamamlanmadi.setEnabled(false);

        selectedDate = Calendar.getInstance();

        long aLong=ListPageFragment.longYolla; //bu aslında pageViewModel4 yerine'te ki aLong kullandığımda herşeyin mahvolduğu senaryonun çözümü
        String s=ListPageFragment.stringYolla; //bu da pageViewModel3 kullanmak yerine kullandığım ve basit bir şeyilde hayvan gibi sorunu çözen bir şey


                pageViewModel2.getName().observe(requireActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        textView.setText("Zincir Takvimi"+"\n-- "+s+" --");
                    }
                });


            if(s!=null){
                pageViewModelCalendarDay = parseStringToCalendarDay(s);

                //Burada SQlite Database'inde Create(sarıları kaydet) ve Select yapıcaz. Yani aşağıda sqlite'ta kaydettiğimiz günleri burada göstericez
                //CalendarDay calendarDay = CalendarUtils.parseStringToCalendarDay(formattedDate); burada select atarken gelen string tarihini calendar'e çevirio o şekilde cursor'da göstericez falan
                buttonTamamlandi.setEnabled(true);
                buttonTamamlanmadi.setEnabled(true);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = null;

                try {
                    date = dateFormat.parse(s);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                selectedDate = Calendar.getInstance();
                selectedDate.setTime(date);


                // Seçilen tarihi CalendarDay'e dönüştür
                selectedCalendarDay = CalendarDay.from( //Hedef ne zaman oluşturulduysa o zamanın tarihi
                        selectedDate.get(Calendar.YEAR),
                        selectedDate.get(Calendar.MONTH) + 1,
                        selectedDate.get(Calendar.DAY_OF_MONTH)
                );

                formattedDate1 = formatDate(pageViewModelCalendarDay);//Sqlite'a kaydedebilmek için string türüne çevirdik

                calendarView.setDateSelected(firstCalendarDay, true);

                turuncu=Color.rgb(255, 165, 0);
                yesil=Color.GREEN;
                kirmizi=Color.RED;
                color=kirmizi;
                color2=yesil;

                //Buradan aşağısı her 1 ögeyi 1 kere kaydetmek için var
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();

                //Burasının amacı kullancıının zinciri oluşturduğu günden bugüne kadar olan günleri turuncu yapmak ve turuncu olarak kaydetmek
                try {
                    // formattedDate1'i Calendar türüne çevir
                    calendar.setTime(dateFormat1.parse(formattedDate1));
                    while (formattedDate1.compareTo(formattedDate3) <= 0) {
                        // Kaydın zaten var mı kontrol et
                        Cursor checkCursor = database2.rawQuery("SELECT * FROM myseconddatabase WHERE formattedDate1 = ? AND aLong=?", new String[]{formattedDate1,String.valueOf(aLong)});
                        if (checkCursor.getCount() == 0) {
                            // Kayıt yoksa yeni kayıt ekle

                            String sqlString = "INSERT INTO myseconddatabase(aLong, formattedDate1, formattedDate2, color) VALUES(?, ?, ?, ?)";
                            SQLiteStatement sqLiteStatement = database2.compileStatement(sqlString);
                            sqLiteStatement.bindLong(1, aLong);
                            sqLiteStatement.bindString(2, formattedDate1);
                            sqLiteStatement.bindString(3, formattedDate1);
                            sqLiteStatement.bindLong(4, turuncu);
                            sqLiteStatement.execute();
                        }
                        checkCursor.close();

                        // Bir sonraki günü işle
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        formattedDate1 = dateFormat1.format(calendar.getTime());
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                calendarView.addDecorator(new RangeDecorator(turuncu, pageViewModelCalendarDay, firstCalendarDay)); //selectedDate'den bugüne kadar olan günleri turuncuya boya
                butonlarIcin(view);

                buttonTamamlandi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedCalendar != null) {
                            // Varolan gün seçildi
                            rangeDecorator1 = new RangeDecorator(yesil, selectorsCalendarDay, selectorsCalendarDay);
                            butonlarIcin(view);
                            calendarView.addDecorator(rangeDecorator1);
                            Toast.makeText(getActivity().getApplicationContext(), "Tebrikler!", Toast.LENGTH_SHORT).show();
                            playVictorySound();


                            try {

                                database2.execSQL("UPDATE myseconddatabase SET color = ? WHERE formattedDate1 = ? AND aLong = ?",
                                        new String[]{String.valueOf(yesil), formattedDate2,String.valueOf(aLong)});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Hiçbir gün seçilmedi, ilk günü güncelle
                            rangeDecorator2 = new RangeDecorator(yesil, firstCalendarDay, firstCalendarDay);
                            calendarView.addDecorator(rangeDecorator2);
                            Toast.makeText(getActivity().getApplicationContext(), "Tebrikler!", Toast.LENGTH_SHORT).show();
                            playVictorySound();

                            try {
                                // Doğru rengi içeren mevcut satırı güncelle
                                database2.execSQL("UPDATE myseconddatabase SET color = ? WHERE formattedDate1 = ? AND aLong = ?",
                                        new String[]{String.valueOf(yesil), formattedDate3, String.valueOf(aLong)});
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                buttonTamamlanmadi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedCalendar != null) {
                            // Varolan gün seçildi
                            rangeDecorator3 = new RangeDecorator(kirmizi, selectorsCalendarDay, selectorsCalendarDay);
                            butonlarIcin(view);
                            calendarView.addDecorator(rangeDecorator3);

                            try {
                                // Doğru rengi içeren mevcut satırı güncelle

                                database2.execSQL("UPDATE myseconddatabase SET color = ? WHERE formattedDate1 = ? AND aLong = ?",
                                        new String[]{String.valueOf(kirmizi), formattedDate2, String.valueOf(aLong)});
                                playDefeateSound();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            // Hiçbir gün seçilmedi, ilk günü güncelle
                            rangeDecorator4 = new RangeDecorator(kirmizi, firstCalendarDay, firstCalendarDay);
                            calendarView.addDecorator(rangeDecorator4);

                            try {
                                // Doğru rengi içeren mevcut satırı güncelle
                                database2.execSQL("UPDATE myseconddatabase SET color = ? WHERE formattedDate1 = ? AND aLong = ?",
                                        new String[]{String.valueOf(kirmizi), formattedDate3, String.valueOf(aLong)});
                                playDefeateSound();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


                cursor = database2.rawQuery("SELECT * FROM myseconddatabase WHERE color = ? AND aLong=?",
                        new String[]{String.valueOf(yesil), String.valueOf(aLong)});

                int calendarDay1Ix = cursor.getColumnIndex("formattedDate1");
                int calendarDay2Ix = cursor.getColumnIndex("formattedDate2");
                int colorIx = cursor.getColumnIndex("color");

                if (cursor.moveToFirst()) {
                    do {
                        CalendarDay bir = parseStringToCalendarDay(cursor.getString(calendarDay1Ix));
                        CalendarDay iki = parseStringToCalendarDay(cursor.getString(calendarDay2Ix));
                        int renk = cursor.getInt(colorIx);

                        RangeDecorator rangeDecorator = new RangeDecorator(renk, bir, iki);
                        calendarView.addDecorator(rangeDecorator);
                    } while (cursor.moveToNext());
                } else {
                    // Kayıt bulunamadı
                }
                cursor.close();

                cursor2 = database2.rawQuery("SELECT * FROM myseconddatabase WHERE color = ? AND aLong=?",
                        new String[]{String.valueOf(kirmizi),String.valueOf(aLong)});

                int calendarDay1IxCursor2 = cursor2.getColumnIndex("formattedDate1");
                int calendarDay2IxCursor2 = cursor2.getColumnIndex("formattedDate2");
                int colorIxCursor2 = cursor2.getColumnIndex("color");

                if (cursor2.moveToFirst()) {
                    do {
                        CalendarDay bir = parseStringToCalendarDay(cursor2.getString(calendarDay1IxCursor2));
                        CalendarDay iki = parseStringToCalendarDay(cursor2.getString(calendarDay2IxCursor2));
                        int renk = cursor2.getInt(colorIxCursor2);

                        RangeDecorator rangeDecorator = new RangeDecorator(renk, bir, iki);
                        calendarView.addDecorator(rangeDecorator);
                    } while (cursor2.moveToNext());
                } else {
                    // Kayıt bulunamadı
                }
                cursor2.close();
            }


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton(view);

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(requireActivity());
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton3(view);

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(requireActivity());
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton4(view);

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(requireActivity());
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }

            }
        });

        super.onViewCreated(view, savedInstanceState);

    }

    private void playVictorySound() {
        MediaPlayer music = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.win);
        music.start();
    }

    private void playDefeateSound() {
        MediaPlayer music = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.lose);
        music.start();
    }

    public void butonlarIcin(View view){

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Calendar anan=Calendar.getInstance();

                day=date.getDay();
                month=date.getMonth();
                year=date.getYear();

                pageViewModel3.getName().observe(requireActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        pageViewModelCalendarDay2 = parseStringToCalendarDay(s);
                        Calendar conversion2=convertCalendarDayToCalendar(date);
                        Calendar conversion=convertCalendarDayToCalendar(pageViewModelCalendarDay2);

                        //Bu selectedDatelere pageViewModel'dan gelen s stringini ver
                        if(conversion2.compareTo(conversion)>=0 && conversion2.compareTo(anan)<=0){

                            buttonTamamlandi.setEnabled(true);
                            buttonTamamlanmadi.setEnabled(true);

                            selectedCalendar = Calendar.getInstance();
                            selectedCalendar.set(year, month - 1, day);

                            selectorsCalendarDay = CalendarDay.from( //Kullanıcının takvim'de selector ile seçtiği gün
                                    selectedCalendar.get(Calendar.YEAR),
                                    selectedCalendar.get(Calendar.MONTH) + 1,
                                    selectedCalendar.get(Calendar.DAY_OF_MONTH));


                            formattedDate2=formatDate(selectorsCalendarDay);

                            //Bu selectedDatelere pageViewModel'dan gelen s stringini ver
                        }else {
                            buttonTamamlandi.setEnabled(false);
                            buttonTamamlanmadi.setEnabled(false);
                        }
                    }
                });
            }
        });
    }

    public static Calendar convertCalendarDayToCalendar(CalendarDay calendarDay) { //CalendarDay'i calendar'e çevirmek için
        if (calendarDay != null) {
            int year = calendarDay.getYear();
            int month = calendarDay.getMonth() - 1; // Ay, 0-11 aralığında olduğu için 1 çıkardım
            int day = calendarDay.getDay();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            return calendar;
        } else {
            return null;
        }
    }
    private String formatDate(CalendarDay calendarDay) { //CalendarDay'i, String'e çevirme
        int year2 = calendarDay.getYear();
        int month2 = calendarDay.getMonth();
        int day2 = calendarDay.getDay();

        // Örneğin "yyyy-MM-dd" formatında bir CalendarDay'i, String'e döndürme
        return String.format(Locale.getDefault(), "%04d-%02d-%02d", year2, month2, day2);
    }

    public static CalendarDay parseStringToCalendarDay(String dateString) { //String'i, CalendarDay'e çevirme
        try {
            String[] dateParts = dateString.split("-");
            int year3 = Integer.parseInt(dateParts[0]);
            int month3 = Integer.parseInt(dateParts[1]);
            int day3 = Integer.parseInt(dateParts[2]);

            return CalendarDay.from(year3, month3, day3);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hata durumunda null dönebilirsiniz veya başka bir işlem yapabilirsiniz.
        }
    }
    public void floatingActionButton(View view){
        NavDirections action= (NavDirections) SecondFragmentDirections.actionSecondFragmentToFirstFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton3(View view){
        NavDirections action = SecondFragmentDirections.actionSecondFragmentToThirdFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton4(View view){
        NavDirections action = SecondFragmentDirections.actionSecondFragmentToAccountAndSettingsPage();
        Navigation.findNavController(view).navigate(action);
    }


}