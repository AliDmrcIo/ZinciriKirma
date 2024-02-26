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
import android.widget.Button;

import com.alidemirci.dontbreakthechain.databinding.FragmentFirstBinding;
import com.alidemirci.dontbreakthechain.databinding.FragmentFourthBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FourthFragment extends Fragment {

    FloatingActionButton floatingActionButtonBack;
    private FragmentFourthBinding binding;

    public FourthFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentFourthBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButtonBack=binding.floatingActionButtonBack;

        floatingActionButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonBack(view);
            }
        });
    }

    public void floatingActionButtonBack(View view){
        NavDirections action = FourthFragmentDirections.actionFourthFragmentToAccountAndSettingsPage();
        Navigation.findNavController(view).navigate(action);
    }


}