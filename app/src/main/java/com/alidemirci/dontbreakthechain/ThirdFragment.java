package com.alidemirci.dontbreakthechain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alidemirci.dontbreakthechain.databinding.FragmentSecondBinding;
import com.alidemirci.dontbreakthechain.databinding.FragmentThirdBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {
    //bu sayfa ArtBook projemdeki MainActivity sayfasına denk geliyor
    FloatingActionButton floatingActionButton,floatingActionButton2,floatingActionButton4,floatingActionButtonAdd;
    ArrayList<ListsVariablesClass> listsVariablesClassArray;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    private FragmentThirdBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Bu sayfada listeler olacak ve buraya tıkladığımızda bizi ListPageFragment sayfasına yönlendirecek. O sayfada da Liste elemanının içerisini düzenleyebilcez

        listsVariablesClassArray=new ArrayList<>();

        getData();

        listAdapter=new ListAdapter(requireContext(),listsVariablesClassArray,true); //Burayı da değiştirdim

        super.onCreate(savedInstanceState);

    }
    @SuppressLint("NotifyDataSetChanged")
    private void getData(){ //ListPageFragment sayfasında yazdığımız sql kayıtlarını burada alıcaz
        try{
            SQLiteDatabase sqLiteDatabase = getActivity().getApplicationContext().openOrCreateDatabase("MyDatabase", Context.MODE_PRIVATE,null); //ListPageFragmenttaki almak istediğimiz verilerin bulunduğu sql serverin adını tam olarak aynı şekilde yazmak gerek

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mydatabase",null); //Burada query(yani okuma) yapıyoruz

            int nameIx = cursor.getColumnIndex("hedef");
            int idIx = cursor.getColumnIndex("id");

            while(cursor.moveToNext()){
                String name = cursor.getString(nameIx);
                int id = cursor.getInt(idIx);

                ListsVariablesClass listsVariablesClass = new ListsVariablesClass(name,id);

                listsVariablesClassArray.add(listsVariablesClass);
            }
            listAdapter.notifyDataSetChanged();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentThirdBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(listAdapter);


        floatingActionButton=binding.floatingActionButton1;
        floatingActionButton2=binding.floatingActionButton2;
        floatingActionButton4=binding.floatingActionButton4;
        floatingActionButtonAdd=binding.floatingActionButtonAddList;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton(view);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton2(view);
            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButton4(view);
            }
        });

        floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("info","new");
                Navigation.findNavController(view).navigate(R.id.action_thirdFragment_to_listPageFragment, bundle);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void floatingActionButton(View view){
        NavDirections action = ThirdFragmentDirections.actionThirdFragmentToFirstFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton2(View view){
        NavDirections action = ThirdFragmentDirections.actionThirdFragmentToSecondFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void floatingActionButton4(View view){
        NavDirections action = ThirdFragmentDirections.actionThirdFragmentToAccountAndSettingsPage();
        Navigation.findNavController(view).navigate(action);
    }
}