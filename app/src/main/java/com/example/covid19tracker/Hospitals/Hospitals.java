package com.example.covid19tracker.Hospitals;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.covid19tracker.Call_Doctor.call_doctor;
import com.example.covid19tracker.Call_Doctor.doctor_data;
import com.example.covid19tracker.Hospitals.Hospitals_Data;
import com.example.covid19tracker.Hospitals.Hos_Addpter;
import com.example.covid19tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hospitals extends AppCompatActivity {



    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<com.example.covid19tracker.Hospitals.Hospitals_Data> hospitals_data;
    Hos_Addpter adpter;
    FirebaseFirestore firestore_data;
    Handler image_handler;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_handler = new Handler();
        firestore_data = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(" Getting Hospitals Data...");
        progressDialog.show();
        setContentView(R.layout.activity_hospitals);
        inidata();
        iniRecylerview();
    }




    private void iniRecylerview() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_hos);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adpter = new Hos_Addpter(hospitals_data);
        recyclerView.setAdapter(adpter);
//        adpter.setOnItemClickListener(new Hos_Addpter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int poisition) {
//                Uri uri = Uri.parse("tel:" + hospitals_data.get(poisition).getPhone_hospital());
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_DIAL);
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        });
    }


    private void inidata() {
        hospitals_data = new ArrayList<>();
        firestore_data.collection("hospitals").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot decument : task.getResult()) {
                                Map data_firestore = decument.getData();
                                hospitals_data.add(new Hospitals_Data(
                                        data_firestore.get("Name").toString(),
                                        decument.getData().get("Location").toString(),
                                        data_firestore.get("Url").toString(),data_firestore.get("Phone").toString()));
                            }
                            adpter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else
                            Toast.makeText(Hospitals.this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
                    }
                });
    }







}