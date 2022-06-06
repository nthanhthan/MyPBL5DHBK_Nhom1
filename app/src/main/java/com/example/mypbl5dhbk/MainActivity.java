package com.example.mypbl5dhbk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mypbl5dhbk.Model.User;
import com.example.mypbl5dhbk.View.MainApp;
import com.example.mypbl5dhbk.ViewModel.MyFirebaseService;
import com.example.mypbl5dhbk.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private  FirebaseAuth fAuth;
    public ArrayList<User> listUser;
    private  static  ArrayList<User> newList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        fAuth= FirebaseAuth.getInstance();
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.edEmail.setText(null);
                binding.edPassword.setText(null);
            }
        });
        binding.btnGetIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name=binding.edEmail.getText().toString();
                String pass=binding.edPassword.getText().toString();

                listUser=new ArrayList<>();
                fAuth.signInWithEmailAndPassword(name,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"ThanhCong",Toast.LENGTH_SHORT).show();
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            DatabaseReference myRef=database.getReference("User");
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   listUser.clear();
                                    for (DataSnapshot i:snapshot.getChildren()){
                                        User user=i.getValue(User.class);
                                        listUser.add(user);
                                    }
                                    try {
                                        ShowList(listUser);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if(newList.get(0).getName().compareTo("Unknown")==0)
                                    notification();
////                                    if(listUser.size()>0){
//                                        sort(0,listUser.size()-1);
////                                        nameUser=newList.get(0).getTime();}
                                    Intent intent=new Intent(getApplicationContext(), MainApp.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putParcelableArrayList("listUser",newList);
                                    Log.d("Debug","sendData");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            Log.d("DEBUG","okee");
                            Toast.makeText(MainActivity.this,"ThatBai",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }



    private  void notification(){
        Intent intent = new Intent(this, MainApp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "mypbl5-88973";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                        .setContentTitle("Thong bao")
                        .setContentText("Unknown")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    } private void ShowList(ArrayList<User> newList1) throws ParseException {
        if(newList1.size()>0){
            newList.clear();
            for(User i:newList1){
              newList.add(i);
           }
            sort(0,newList.size()-1);

        }
        else{
            Log.d("Debug","khong co");
        }
    }
    public static void sort(int low, int high) throws ParseException {
        if (low < high) {
            Log.d("DEBUG","SORT");
            int pi = partition( low, high);

            sort( low, pi - 1);
            sort( pi + 1, high);
        }
    }

    static int partition(int low, int high) throws ParseException {
        Log.d("DEBUG","PARTITION");
        User pivot = newList.get(high);
        int i = (low - 1); // index of smaller element

        for (int j = low; j < high; j++) {
            String []s1=newList.get(j).getTime().split(":",2);
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

            String []s2=pivot.getTime().split(":",2);
            Date d1=sdf.parse(s1[0]);
            Date d2=sdf.parse(s2[0]);
            // Nếu phần tử hiện tại nhỏ hơn chốt
            if (d2.compareTo(d1)<0) {
                if (s2[1].compareTo(s1[1]) < 0) {
                    i++;
                    User temp = new User(newList.get(i).getTime(), newList.get(i).getName(), newList.get(i).getAvatar());
                    newList.get(i).setName(newList.get(j).getName());
                    newList.get(i).setAvatar(newList.get(j).getAvatar());
                    newList.get(i).setTime(newList.get(j).getTime());
                    newList.get(j).setName(temp.getName());
                    newList.get(j).setAvatar(temp.getAvatar());
                    newList.get(j).setTime(temp.getTime());
                }
            }
        }
        User temp = new User(newList.get(i + 1).getTime(),newList.get(i + 1).getName(),newList.get(i + 1).getAvatar());
        newList.get(i+ 1).setName(newList.get(high).getName());
        newList.get(i+1).setAvatar(newList.get(high).getAvatar());
        newList.get(i+1).setTime(newList.get(high).getTime());
        newList.get(high).setName(temp.getName());
        newList.get(high).setAvatar(temp.getAvatar());
        newList.get(high).setTime(temp.getTime());
        return i + 1;
    }
}