package com.example.mypbl5dhbk.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.mypbl5dhbk.databinding.ActivityOpenDoorBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenDoor extends AppCompatActivity {
    private ActivityOpenDoorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOpenDoorBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkOpen=binding.toggle.isChecked();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myRef=database.getReference("Door");
                myRef.setValue(checkOpen);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                        Boolean checkDoor=(Boolean) snapshot.getValue();
                        binding.toggle.setChecked(checkDoor);
                        Log.d("DEBUG1",checkDoor.toString());
                    }

                    @Override
                    public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}