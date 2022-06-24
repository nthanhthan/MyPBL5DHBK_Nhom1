package com.example.mypbl5dhbk;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypbl5dhbk.Model.User;
import com.example.mypbl5dhbk.ViewModel.HistoryAdapter;
import com.example.mypbl5dhbk.ViewModel.MyFirebaseService;
import com.example.mypbl5dhbk.databinding.FragmentHistoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;


public class HistoryFragment extends Fragment implements HistoryAdapter.OnPersonListener{
    private FragmentHistoryBinding binding;
    private ArrayList<User> listUser;
    private HistoryAdapter historyAdapter;
    private  static  ArrayList<User> newList;
    public static final String TAG = MyFirebaseService.class.getName();

    private RecyclerView rcv;
    private View mView;
    public MainActivity mainActivity;
    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

      // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_history, container, false);

        rcv = mView.findViewById(R.id.rv_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        listUser=new ArrayList<>();
        historyAdapter = new HistoryAdapter(listUser, (HistoryAdapter.OnPersonListener) getContext());
        rcv.setAdapter(historyAdapter);
        Bundle bundle = getActivity().getIntent().getExtras();
        Log.d("DEBUG","ERROR");
        newList=bundle.getParcelableArrayList("listUser");

        if(newList.size()>0){
        for (int i=newList.size()-1;i>=0;i--){
            listUser.add(newList.get(i));
        }
            historyAdapter.notifyDataSetChanged();
        }

        else{
            Log.d("Debug","khong co");
        }

        return mView;


    }

    @Override
    public void onPersonClick(int position) {

    }



}