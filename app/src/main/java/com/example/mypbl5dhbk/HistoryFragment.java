package com.example.mypbl5dhbk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = FragmentHistoryBinding.inflate(getLayoutInflater());
//        View viewRoot = binding.getRoot();
//        setContentView(viewRoot);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//                        String token = task.getResult();
//
//                        // Log and toast
//                        Log.d("DEBUG", token);
//                    }
//                });
//
//
//       binding.rvHistory.setLayoutManager(new LinearLayoutManager(this));
//        listUser=new ArrayList<>();
//        historyAdapter=new HistoryAdapter(listUser,this);
//        binding.rvHistory.setAdapter(historyAdapter);
//        Bundle bundle=getIntent().getExtras();
//        Log.d("DEBUG","ERROR");
//        newList=bundle.getParcelableArrayList("listUser");
//
//        if(newList.size()>0){
////        for (int i=newList.size()-1;i>=0;i--){
////            listUser.add(newList.get(i));
////        }
//            for(User i:newList){
//                listUser.add(i);
//            }
//            historyAdapter.notifyDataSetChanged();
//        }
//
//        else{
//            Log.d("Debug","khong co");
//        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onPersonClick(int position) {
        
    }
}