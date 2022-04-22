package com.example.covid19tracker.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.covid19tracker.Authentication.LoginActivity;
import com.example.covid19tracker.R;
import com.example.covid19tracker.userSession.UserDataa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class ProfileFragment extends Fragment implements EasyPermissions.PermissionCallbacks , View.OnClickListener {

    private TextView nameTv, phoneTv, emailTv, ageTv, genderTv, dateLastTv, resultLastTv, symtomsLastTv, tvLogOut,tvChange;
    private FrameLayout pb;
    private UserDataa mDataa;
    private ImageView userImg;

    private FirebaseStorage storage;
    private StorageReference storageReference;


    public static final int GALLERY = 1;
    public static final int CAMERA = 2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        initialization(v);
        getProfileData();
        return v;
    }

    private void initialization(View v) {
        nameTv = v.findViewById(R.id.tv_name);
        phoneTv = v.findViewById(R.id.tv_phone);
        emailTv = v.findViewById(R.id.tv_email);
        ageTv = v.findViewById(R.id.tv_age);
        genderTv = v.findViewById(R.id.tv_gender);
        dateLastTv = v.findViewById(R.id.tv_date);
        resultLastTv = v.findViewById(R.id.tv_result);
        symtomsLastTv = v.findViewById(R.id.tv_symptoms);
        pb = v.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        userImg = v.findViewById(R.id.iv_user);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mDataa = new UserDataa(getContext());
        tvChange=v.findViewById(R.id.tv_change);
        tvChange.setOnClickListener(this);
        tvLogOut = v.findViewById(R.id.tv_logout);
        tvLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_logout:
                FirebaseAuth.getInstance().signOut();
                mDataa.deleteSavedData();
                break;
            case R.id.tv_change:
                choosePhotoFromGallery();
                break;
        }

    }
    private void getProfileData() {
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            //hold data into views
                            String imgUrl = documentSnapshot.getString("user_image_url");
                            if (!imgUrl.equals("")) {
                                Glide.with(userImg).load(imgUrl).into(userImg);
                            }
                            nameTv.setText(documentSnapshot.getString("fullName"));
                            phoneTv.setText(documentSnapshot.getString("phone"));
                            emailTv.setText(documentSnapshot.getString("email"));
                            ageTv.setText(documentSnapshot.getString("age"));
                            genderTv.setText(documentSnapshot.getString("gender"));
                            dateLastTv.setText(documentSnapshot.getString("date_of_last_check"));
                            resultLastTv.setText(documentSnapshot.getString("result_of_last_check"));
                            symtomsLastTv.setText(documentSnapshot.getString("symtoms_of_last_check"));
                            pb.setVisibility(View.GONE);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "SomeThing wrong Please Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // Permissions
    private void showSelectionDialog() {
        AlertDialog.Builder selectDialog = new AlertDialog.Builder(getContext());
        selectDialog.setTitle("Select Action");
        String[] selectionDialogIteams = {
                "Select Photo from Gallery",
                "Capture Photo From Camera"};
        selectDialog.setItems(selectionDialogIteams,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        selectDialog.show();
    }

    @AfterPermissionGranted(123)
    private void takePhotoFromCamera() {
        String[] cameraPermissions = new String[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(getContext(), cameraPermissions)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } else {
            EasyPermissions.requestPermissions(this, "Access for camera",
                    123, cameraPermissions);
        }
    }

    @AfterPermissionGranted(101)
    public void choosePhotoFromGallery() {
        String[] galleryPermissions = new String[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            galleryPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(getContext(), galleryPermissions)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, GALLERY);
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (grantResults.length > 0) {
            if (grantResults.toString().equals(GALLERY)) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY);
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    //get the photo
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentUri = data.getData();
                pb.setVisibility(View.VISIBLE);
                userImg.setImageURI(contentUri);
                uploadUriImage(contentUri);
            }
        }
    }

    // upload to storage
    private void uploadUriImage(Uri contentUri) {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference pathReference = storageReference.child("userImages/" + randomKey);

        pathReference.putFile(contentUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        updateUserImage(pathReference);
                        Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Something went wrong please try again later" + e, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateUserImage(StorageReference pathReference) {
        pathReference
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //firestore and update url img ling
                        String userId = FirebaseAuth.getInstance().getUid();
                        Map<String, Object> x = new HashMap<>();
                        x.put("user_image_url", uri.toString());
                        FirebaseFirestore.getInstance().collection("users").document(userId).update(x);
                        Toast.makeText(getContext(), "Image Updated Successfully", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        Log.d("TAG", "onSuccess: " + uri);

                    }
                });
    }



}