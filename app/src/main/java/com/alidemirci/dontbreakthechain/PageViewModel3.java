package com.alidemirci.dontbreakthechain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PageViewModel3  extends ViewModel { //String olacak ve ListPageFragment sayfasından takvim sayfasına tarih değerini yollayacak

    private MutableLiveData<String> dayInput = new MutableLiveData<>();

    public void setName(String day){
        dayInput.setValue(day);
    }

    public LiveData<String> getName(){
        return dayInput;
    }


}
