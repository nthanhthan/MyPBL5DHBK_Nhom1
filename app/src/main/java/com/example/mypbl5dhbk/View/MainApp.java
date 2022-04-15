package com.example.mypbl5dhbk.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.mypbl5dhbk.Model.User;
import com.example.mypbl5dhbk.ViewModel.HistoryAdapter;
import com.example.mypbl5dhbk.ViewModel.MyFirebaseService;
import com.example.mypbl5dhbk.databinding.ActivityMainAppBinding;
import com.example.mypbl5dhbk.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class MainApp extends AppCompatActivity implements HistoryAdapter.OnPersonListener {
    private ActivityMainAppBinding binding;
    private ArrayList<User> listUser;
    private HistoryAdapter historyAdapter;
    private  ArrayList<User> newList;
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

                        // Get new FCM registration token
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
          newList=bundle.getParcelableArrayList("listUser");
         if(newList.size()>0){
             sort(0,newList.size()-1);
        for (User i: newList){
            listUser.add(i);
        }
             historyAdapter.notifyDataSetChanged();
         }

         else{
             Log.d("Debug","khong co");
         }
//        listUser=new ArrayList<>();
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference myRef=database.getReference("User");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot i:snapshot.getChildren()){
//                    User user=i.getValue(User.class);
//                    listUser.add(user);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    void sort(int low, int high) {
        if (low < high) {
            Log.d("DEBUG","SORT");
            int pi = partition( low, high);

            sort( low, pi - 1);
            sort( pi + 1, high);
        }
    }

    int partition( int low, int high) {
         Log.d("DEBUG","PARTITION");
        User pivot = newList.get(high);
        int i = (low - 1); // index of smaller element

        for (int j = low; j < high; j++) {

            // Nếu phần tử hiện tại nhỏ hơn chốt
            if (newList.get(j).getId() > pivot.getId()) {
                i++;
                // swap arr[i] và arr[j]

                User temp = new User(newList.get(i).getId(),newList.get(i).getName(),newList.get(i).getAvatar());
                newList.get(i).setName(newList.get(j).getName());
                newList.get(i).setAvatar(newList.get(j).getAvatar());
                newList.get(i).setId(newList.get(j).getId());
                newList.get(j).setName(temp.getName());
                newList.get(j).setAvatar(temp.getAvatar());
                newList.get(j).setId(temp.getId());
            }
        }

        // swap arr[i+1] và arr[high] (hoặc pivot)
        User temp = new User(newList.get(i + 1).getId(),newList.get(i + 1).getName(),newList.get(i + 1).getAvatar());
        newList.get(i+ 1).setName(newList.get(high).getName());
        newList.get(i+1).setAvatar(newList.get(high).getAvatar());
        newList.get(i+1).setId(newList.get(high).getId());
        newList.get(high).setName(temp.getName());
        newList.get(high).setAvatar(temp.getAvatar());
        newList.get(high).setId(temp.getId());
        return i + 1;
    }
    @Override
    public void onPersonClick(int position) {

    }
}