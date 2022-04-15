package com.example.covid19tracker.Call_Doctor;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Thread image_download;
    Runnable runnable;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image_handler = new Handler();
        firestore_data=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(" Getting Doctors Data...");
        progressDialog.show();
        setContentView(R.layout.activity_call_doctor);
        inidata();
        iniRecylerview();


    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    private void iniRecylerview() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adpter = new doctor_items_adapter(doctor_data);
        recyclerView.setAdapter(adpter);

    }

    private void inidata() {
        doctor_data = new ArrayList<>();
//       doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
//        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
//    doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
//     doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
//       doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));
//        doctor_data.add(new doctor_data(R.drawable.doctor1, "Ahmed NAfea", "+0111113332"));



           firestore_data.collection("doctors").get(). addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
               @Override
               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if(task.isSuccessful())
                   {
                       Runnable runnable;
                         Thread thread= new Thread();
                        Bitmap[] bitmapa = new Bitmap[1];

                       for(QueryDocumentSnapshot decument : task.getResult())
                       {
                           Map data_firestore = decument.getData();

//                           fetchimage image=new fetchimage(data_firestore.get("Url").toString());
//                           image.run();

//                           runnable=new Runnable() {
//                               @Override
//                               public void run() {
//                                 bitmapa[0] = get_image(data_firestore.get("Url").toString());
//
//
//                               }
//                           };
//                           thread=new Thread(runnable);
//                           thread.start();


                           doctor_data.add(new doctor_data(data_firestore.get("Url").toString(), decument.getData().get("Name").toString(),data_firestore.get("Phone").toString() ));

                       }
                       adpter.notifyDataSetChanged();
                      if(progressDialog.isShowing())
                          progressDialog.dismiss();




                   }else 
                   {

                       Log.i(TAG, "onComplete: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                   }



               }
           });







    }




    private Bitmap get_image(String URL) {

        Bitmap bitmap=null;
        try {
            InputStream inputStream=new java.net.URL(URL).openStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.i(TAG, "run: aaaaaaaaaa");
            e.printStackTrace();
        }

     return bitmap;

    }


    class fetchimage extends Thread {
        private String URL;
        InputStream inputStream = null;
        private Bitmap bitmap=null;



        fetchimage(String URL) {
            this.URL = URL;
        }


        @Override
        public void run() {


            try {
                inputStream=new java.net.URL(URL).openStream();
                bitmap= BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                Log.i(TAG, "run: aaaaaaaaaa");
                e.printStackTrace();
               }



        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }





}