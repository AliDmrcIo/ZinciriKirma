package com.alidemirci.dontbreakthechain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alidemirci.dontbreakthechain.databinding.FragmentAccountAndSettingsPageBinding;
import com.alidemirci.dontbreakthechain.databinding.FragmentDonateAndRemoveAdsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DonateAndRemoveAds extends Fragment {

    FloatingActionButton floatingActionButton;
    private FragmentDonateAndRemoveAdsBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentDonateAndRemoveAdsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton=binding.floatingActionButtonBack;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton1(view);
            }
        });


    }

    public void floatingActionButton1(View view){
        NavDirections action = DonateAndRemoveAdsDirections.actionDonateAndRemoveAdsToAccountAndSettingsPage();
        Navigation.findNavController(view).navigate(action);
    }
}