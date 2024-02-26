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
import android.widget.Toast;

import com.alidemirci.dontbreakthechain.databinding.FragmentFirstBinding;
import com.alidemirci.dontbreakthechain.databinding.FragmentSignInBinding;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class SignInFragment extends Fragment {

    //private FirebaseAuth auth;
    FloatingActionButton floatingActionButtonBackSignIn;
    private FragmentSignInBinding binding;
    //Button button;
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //auth= FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSignInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        button = binding.button;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kayitOl(view);
            }
        });
            */


        floatingActionButtonBackSignIn=binding.floatingActionButtonBack;
        floatingActionButtonBackSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingActionButtonBackSignIn(view);
            }
        });

    }

    public void floatingActionButtonBackSignIn(View view){
        NavDirections action=SignInFragmentDirections.actionSignInFragmentToAccountAndSettingsPage();
        Navigation.findNavController(view).navigate(action);
    }

    /*


    public void kayitOl(View view){

        String email = binding.editTextEmailKayitOl.getText().toString();
        String password = binding.editTextSifreKayitOl.getText().toString();
        String adSoyad = binding.editTextAdSoyad.getText().toString();

        if(email.equals("") || password.equals("") || adSoyad.equals("")){
            Toast.makeText(requireContext(), "Lütfen Ad-Soyad, Email veya Şifreyi Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Toast.makeText(requireContext(), "Tebrikler. Hesap başarıyla oluşturuldu!", Toast.LENGTH_SHORT).show();
                    NavDirections action=SignInFragmentDirections.actionSignInFragmentToAccountAndSettingsPage();
                    Navigation.findNavController(view).navigate(action);


                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                    Böyle yapınca direkt uygulamadan attığı için yapacak bir şey yok, silmem lazım


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }*/

}