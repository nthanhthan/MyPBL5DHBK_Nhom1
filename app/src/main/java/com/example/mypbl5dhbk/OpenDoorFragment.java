package com.example.mypbl5dhbk;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OpenDoorFragment extends Fragment {
    ToggleButton toggleButton;
   // ImageView imageView;
    private View mView;
    private MainActivity mainActivity;
    public OpenDoorFragment() {
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_open_door, container, false);
        Log.d("Door","Run");
        toggleButton = mView.findViewById(R.id.toggle);
        if(toggleButton.isChecked()){
            mView.setBackgroundResource(R.drawable.img_1);
        }else {
            mView.setBackgroundResource(R.drawable.img);
        }
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("Door");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String checkDoor= snapshot.getValue().toString();
                Log.d("Door",checkDoor);
                Boolean opendoor=Boolean.parseBoolean(checkDoor);
                toggleButton.setChecked(opendoor);
                if(toggleButton.isChecked()){
                    mView.setBackgroundResource(R.drawable.img_1);
                }else {
                    mView.setBackgroundResource(R.drawable.img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mView.setBackgroundResource(R.drawable.img);
                boolean checkOpen=toggleButton.isChecked();
                if(checkOpen){
                    myRef.setValue(checkOpen);
                    mView.setBackgroundResource(R.drawable.img_1);
                }else {
                    mView.setBackgroundResource(R.drawable.img);
                }

            }
        });

        return mView;
    }
}