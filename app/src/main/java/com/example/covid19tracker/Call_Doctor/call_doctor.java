package com.example.covid19tracker.Call_Doctor;

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

public class call_doctor extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<doctor_data> doctor_data;
    doctor_items_adapter adpter;
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
        progressDialog.setMessage(" Getting Doctors Data...");
        progressDialog.show();
        setContentView(R.layout.activity_call_doctor);
        inidata();
        iniRecylerview();
    }


    private void iniRecylerview() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adpter = new doctor_items_adapter(doctor_data);
        recyclerView.setAdapter(adpter);
        adpter.setOnItemClickListener(new doctor_items_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int poisition) {
                Uri uri = Uri.parse("tel:" + doctor_data.get(poisition).getPhone_doctor());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    private void inidata() {
        doctor_data = new ArrayList<>();
        firestore_data.collection("doctors").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot decument : task.getResult()) {
                                Map data_firestore = decument.getData();
                                doctor_data.add(new doctor_data(
                                        data_firestore.get("Url").toString(),
                                        decument.getData().get("Name").toString(),
                                        data_firestore.get("Phone").toString()));
                            }
                            adpter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else
                            Toast.makeText(call_doctor.this, "Please Check Your Connection", Toast.LENGTH_LONG).show();
                    }
                });
    }
}