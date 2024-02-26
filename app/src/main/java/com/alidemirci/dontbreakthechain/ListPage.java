package com.alidemirci.dontbreakthechain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alidemirci.dontbreakthechain.ListAdapter;

import com.alidemirci.dontbreakthechain.databinding.FragmentListPage2Binding;
import com.alidemirci.dontbreakthechain.databinding.FragmentThirdBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListPage extends DialogFragment {
    FloatingActionButton floatingActionButton1,floatingActionButton2,floatingActionButton3,floatingActionButton4,floatingActionButton5;

    ArrayList<ListsVariablesClass> listsVariablesClassArray;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    private FragmentListPage2Binding binding;
    PageViewModel2 pageViewModel2;
    PageViewModel4 pageViewModel4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listsVariablesClassArray = new ArrayList<>();
        getData();

        listAdapter = new ListAdapter(requireContext(), listsVariablesClassArray,false);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);

        pageViewModel2 = new ViewModelProvider(requireActivity()).get(PageViewModel2.class);
        pageViewModel4 = new ViewModelProvider(requireActivity()).get(PageViewModel4.class);

        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getData() {
        try {
            SQLiteDatabase sqLiteDatabase = getActivity().getApplicationContext().openOrCreateDatabase("MyDatabase", Context.MODE_PRIVATE, null);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mydatabase", null);

            int nameIx = cursor.getColumnIndex("hedef");
            int idIx = cursor.getColumnIndex("id");

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIx);
                int id = cursor.getInt(idIx);

                ListsVariablesClass listsVariablesClass = new ListsVariablesClass(name, id);

                listsVariablesClassArray.add(listsVariablesClass);
            }

            listAdapter.notifyDataSetChanged();
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListPage2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = binding.recyclerView15;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(listAdapter);

        floatingActionButton1=binding.floatingActionButton1;
        floatingActionButton2=binding.floatingActionButton2;
        floatingActionButton3=binding.floatingActionButton3;
        floatingActionButton4=binding.floatingActionButton4;
        floatingActionButton5=binding.floatingActionButtonListe;

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = ListPageDirections.actionListPageToFirstFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = ListPageDirections.actionListPageToSecondFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = ListPageDirections.actionListPageToThirdFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = ListPageDirections.actionListPageToAccountAndSettingsPage();
                Navigation.findNavController(view).navigate(action);
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = ListPageDirections.actionListPageToFirstFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }
}
