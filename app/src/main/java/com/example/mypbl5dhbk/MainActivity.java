package com.example.mypbl5dhbk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mypbl5dhbk.Model.User;
import com.example.mypbl5dhbk.View.MainApp;
import com.example.mypbl5dhbk.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private  FirebaseAuth fAuth;
    private ArrayList<User> listUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        fAuth= FirebaseAuth.getInstance();
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
                                    for (DataSnapshot i:snapshot.getChildren()){
                                        User user=i.getValue(User.class);
                                        listUser.add(user);
                                    }
                                    Intent intent=new Intent(getApplicationContext(), MainApp.class);

                                    Bundle bundle=new Bundle();
                                    bundle.putParcelableArrayList("listUser",listUser);
                                    Log.d("Debug","sendData");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this,"ThatBai",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}