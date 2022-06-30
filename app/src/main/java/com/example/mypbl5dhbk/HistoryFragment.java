package com.example.mypbl5dhbk;



import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class HistoryFragment extends Fragment implements HistoryAdapter.OnPersonListener, PopupMenu.OnMenuItemClickListener {
    private FragmentHistoryBinding binding;
    private ArrayList<User> listUser;
    private HistoryAdapter historyAdapter;
    private  static  ArrayList<User> newList;
    public static final String TAG = MyFirebaseService.class.getName();
    private RecyclerView rcv;
    private View btnCalendar;
    private View mView;
    private EditText editDay;
    private View menu;
    private View btnSearch;
    public MainActivity mainActivity;
    String date=null;
    public HistoryFragment() {
        // Required empty public constructor
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

        btnCalendar =mView.findViewById(R.id.btn_Calendar);
        editDay =mView.findViewById(R.id.edit_search);
        editDay.setEnabled(false);
        rcv = mView.findViewById(R.id.rv_history);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        listUser=new ArrayList<>();
        historyAdapter = new HistoryAdapter(listUser, (HistoryAdapter.OnPersonListener) getContext());
        rcv.setAdapter(historyAdapter);
        Bundle bundle = getActivity().getIntent().getExtras();
        Log.d("DEBUG","ERROR");
        newList=bundle.getParcelableArrayList("listUser");
        btnSearch =mView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(btnSearch);
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month=month+1;
                        if(month<10){
                            String month_new="0"+month;
                            date =String.format("%d/%s/%d",dayOfMonth,month_new,year);
                        }
                        else{
                            date =String.format("%d/%d/%d",dayOfMonth,month,year);
                        }

                        editDay.setText(date);
                        Toast.makeText(getContext(), String.format("Selected Date: %d/%d/%d",dayOfMonth,month,year),Toast.LENGTH_SHORT).show();
                        Log.d("Debug",date);
                        listUser.clear();
                        if(newList.size()>0){
                            for (int i=newList.size()-1;i>=0;i--){
                                if(date==null){
                                    listUser.add(newList.get(i));
                                    }
                                else{
                                    Log.d("Debug",i+newList.get(i).getTime());
                                    if(newList.get(i).getTime().contains(date)){
                                        Log.d("Debug","khong co2");
                                        listUser.add(newList.get(i));
                                    }
                                }
                            }
                            historyAdapter.notifyDataSetChanged();
                        }
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

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
    @Override
    public void onCreate(Bundle savedInstanceState) {
       setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);


    }

    public void showPopup(View v){
        PopupMenu popupMenu = new PopupMenu(getContext(),v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.item_menu_search);
        popupMenu.show();
    }
    public void searchByItem(int style){
        listUser.clear();
        if(newList.size()>0){
                if(style==0){
                    for (int i=newList.size()-1;i>=0;i--){
                    listUser.add(newList.get(i));}

                }else if(style==1) {
                       // Log.d("Debug",i+newList.get(i).getName());
                    for (int i=newList.size()-1;i>=0;i--){
                        if(newList.get(i).getName().equals("Unknown")){
                            listUser.add(newList.get(i));
                        }
                    }

                    }else{
                    for (int i=newList.size()-1;i>=0;i--) {
                        if (!newList.get(i).getName().equals("Unknown")) {
                            listUser.add(newList.get(i));
                        }
                    }
                    }

        }
        if(date!=null && listUser.size()>0){
            Log.d("Debug1"," da vo");
            ArrayList<User> listByDate= new ArrayList<>();
            for (int i=0;i<listUser.size();i++){
                if(listUser.get(i).getTime().contains(date)){
                    listByDate.add(listUser.get(i));
                }
            }
                   listUser.clear();
            for(User i: listByDate){
                   listUser.add(i);
            }
        }
            historyAdapter.notifyDataSetChanged();
        }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int style;
        switch (menuItem.getItemId()) {
            case R.id.all:
                Toast.makeText(getActivity(), "all", Toast.LENGTH_SHORT).show();
                style=0;
                searchByItem(style);
                return true;
            case R.id.name:
                Toast.makeText(getActivity(), "name", Toast.LENGTH_SHORT).show();
               style=2;
                searchByItem(style);
                return true;
            case R.id.unknown:
                Toast.makeText(getActivity(), "unknown", Toast.LENGTH_SHORT).show();
                style=1;
                searchByItem(style);
                return true;
            default:
                return false;

        }
    }
}