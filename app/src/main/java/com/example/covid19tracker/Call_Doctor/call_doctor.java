package com.example.covid19tracker.Call_Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import com.example.covid19tracker.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class call_doctor extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<doctor_data> doctor_data;
    doctor_items_adapter adpter;
    FirebaseFirestore firestore_data;
    Handler image_handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_handler=new Handler();
        setContentView(R.layout.activity_call_doctor);

        inidata();
        iniRecylerview();


    }


    @Override
    protected void onStart()
    {
        super.onStart();




    }

    private void iniRecylerview() {
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adpter = new doctor_items_adapter(doctor_data);
        recyclerView.setAdapter(adpter);
        adpter.notifyDataSetChanged();
    }

    private void inidata() {
        doctor_data = new ArrayList<>();
        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));

    }
class fetchimage extends Thread
{
    private String URL;
    InputStream inputStream=null;
    Bitmap bitmap;
    fetchimage(String URL){
        this.URL=URL;
    }




    @Override
    public void run() {




    }
}


}