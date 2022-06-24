package com.example.mypbl5dhbk.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mypbl5dhbk.HistoryFragment;
import com.example.mypbl5dhbk.HomeFragment;
import com.example.mypbl5dhbk.MainActivity;
import com.example.mypbl5dhbk.Model.User;
import com.example.mypbl5dhbk.OpenDoorFragment;
import com.example.mypbl5dhbk.R;
import com.example.mypbl5dhbk.ViewModel.HistoryAdapter;
import com.example.mypbl5dhbk.ViewModel.MyFirebaseService;
import com.example.mypbl5dhbk.databinding.ActivityMainAppBinding;

import java.util.ArrayList;

public class MainApp extends AppCompatActivity implements HistoryAdapter.OnPersonListener {
    private ActivityMainAppBinding binding;
    private ArrayList<User> listUser;
    private HistoryAdapter historyAdapter;
    private  static  ArrayList<User> newList;
    public static final String TAG = MyFirebaseService.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAppBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        replaceFragment(new HomeFragment());
       binding.bottomNavigationView.setOnItemSelectedListener(item -> {
           switch (item.getItemId()){
               case R.id.home:
                   replaceFragment(new HistoryFragment());
                   break;
               case R.id.history:
                   replaceFragment(new HistoryFragment());
                   Log.d("msg", "history");
                   break;
               case R.id.OpenDoor:
                   replaceFragment(new OpenDoorFragment());
                   Log.d("msg", "open door");
                   break;
               case R.id.Logout:
                   Intent a =new Intent(MainApp.this,MainActivity.class );
                   startActivity(a);
                   Log.d("msg", "logout");
                   break;
           }
           return true;
       });

    }

    @Override
    public void onPersonClick(int position) {

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}