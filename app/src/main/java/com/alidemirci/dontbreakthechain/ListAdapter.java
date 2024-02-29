package com.alidemirci.dontbreakthechain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Scanner;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    TextView textView;
    ArrayList<ListsVariablesClass> listsVariablesClassArray;

    boolean isFromFirstFragment;
    public static int a=0;

    public ListAdapter(Context context, ArrayList<ListsVariablesClass> listsVariablesClassArray,boolean isFromFirstFragment){
        this.listsVariablesClassArray=listsVariablesClassArray;
        this.isFromFirstFragment = isFromFirstFragment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        textView=holder.itemView.findViewById(R.id.recyclerViewTextView);
        textView.setText(listsVariablesClassArray.get(position).name);

        holder.itemView.setOnClickListener(new View.OnClickListener() { //Her bir elemana tıklandığında
            @Override
            public void onClick(View view) {

                if(isFromFirstFragment){ //ListPageFragmenttan geliyorsam old'a

                    a=1;

                    Bundle bundle = new Bundle();
                    bundle.putString("info","old");
                    int id = listsVariablesClassArray.get(position).id;

                    bundle.putInt("id", id);
                    ListPageFragment listPageFragment = new ListPageFragment();
                    listPageFragment.setArguments(bundle);
                    Navigation.findNavController(view).navigate(R.id.action_thirdFragment_to_listPageFragment, bundle);

                }else{
                    a=2;
                    Bundle bundle = new Bundle();
                    bundle.putString("info","old");
                    int id = listsVariablesClassArray.get(position).id;


                    bundle.putInt("id", id);
                    ListPageFragment listPageFragment = new ListPageFragment();
                    listPageFragment.setArguments(bundle); //Buraya yolladığımız id deki veirleri listpage'den first fragmenta aktarıcaz
                    Navigation.findNavController(view).navigate(R.id.action_listPage_to_listPageFragment, bundle);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listsVariablesClassArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) { //Burayı recylcerViewBinding ile yapmadığımdan bir sorun çıkarsa buraya dön
            super(itemView);
        }
    }

}