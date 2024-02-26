package com.alidemirci.dontbreakthechain;

import android.text.method.MultiTapKeyListener;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel2 extends ViewModel {
    //ViewModel'ın amacı. Bir class'tan bir değeri buraya kaydedicez. Başka bir classtan o değeri kullanmamız gerektiğinde gelip buradan alıcaz. Bura emanetçi gibi yani. View emanetçisi, yani ViewModel'ı. Yani Fragment'lar arası iletişimi sağlamış oluyoruz.

    private MutableLiveData<String> dayInput = new MutableLiveData<>();

    public void setName(String day){
        dayInput.setValue(day);
    }

    public LiveData<String> getName(){
        return dayInput;
    }

}
