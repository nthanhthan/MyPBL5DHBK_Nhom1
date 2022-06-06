package com.example.mypbl5dhbk.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.mypbl5dhbk.Model.User;
import com.example.mypbl5dhbk.R;
import com.example.mypbl5dhbk.ViewModel.HistoryAdapter;
import com.example.mypbl5dhbk.ViewModel.MyFirebaseService;
import com.example.mypbl5dhbk.databinding.ActivityMainAppBinding;
import com.example.mypbl5dhbk.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

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
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();

                        // Log and toast
                        Log.d("DEBUG", token);
                    }
                });


        binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));
        listUser=new ArrayList<>();
        historyAdapter=new HistoryAdapter(listUser,this);
        binding.rvHistory.setAdapter(historyAdapter);
        Bundle bundle=getIntent().getExtras();
        Log.d("DEBUG","ERROR");
        newList=bundle.getParcelableArrayList("listUser");

         if(newList.size()>0){
//        for (int i=newList.size()-1;i>=0;i--){
//            listUser.add(newList.get(i));
//        }
             for(User i:newList){
                 listUser.add(i);
             }
             historyAdapter.notifyDataSetChanged();
         }

         else{
             Log.d("Debug","khong co");
         }

    }

    @Override
    public void onPersonClick(int position) {

    }
}