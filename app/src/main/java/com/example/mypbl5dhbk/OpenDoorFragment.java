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
    //private FragmentOpenDoorBinding binding;
    ToggleButton toggleButton;
    ImageView imageView;
    private View mView;
    private MainActivity mainActivity;
    public OpenDoorFragment() {
        // Required empty public constructor
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_open_door, container, false);

        toggleButton = mView.findViewById(R.id.toggle);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkOpen=toggleButton.isChecked();
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myRef=database.getReference("Door");
                myRef.setValue(checkOpen);
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                        Boolean checkDoor=(Boolean) snapshot.getValue();
                        Log.d("DEBUG1",checkDoor.toString());
                        toggleButton.setChecked(checkDoor);

                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });

        return mView;
    }
}