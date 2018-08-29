package com.ksntechnology.nongfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ksntechnology.nongfirebase.R;

import java.util.Random;

public class GallaryFragment extends Fragment {
    private ImageView imageView;
    private Uri uri;
    private boolean aBoolean = true;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Gallary
        gallaryController();

//        Create Toolbar
        createToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemCloud) {
            uploadPictureToFirebase();
            return true;
        }

        if (item.getItemId() == R.id.itemDatabase) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentFragmentMain, new DatabaseFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadPictureToFirebase() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Process upload");
        progressDialog.setMessage("Please wait...");


        if (aBoolean) {
            Toast.makeText(getActivity(),
                    "Please choose image",
                    Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();

            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();

            Random random = new Random();
            int i = random.nextInt(1000);
            String nameString = "picture_" + Integer.toString(i);
            StorageReference storageReference1 =
                    storageReference.child("MyPicture/" + nameString);

            storageReference1.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageView.setImageResource(R.drawable.gallary);
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("29AugV1", "Cannot upload image e ==> "
                                    + e.toString());
                            progressDialog.dismiss();
                        }
                    });
        }
    } // Upload

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_gallary, menu);
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarGallary);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Choose image");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please choose image");

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            uri = data.getData();
            aBoolean = false;
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(
                        getActivity().getContentResolver().openInputStream(uri));
                Bitmap bitmap1 = Bitmap.createScaledBitmap(
                        bitmap,
                        800,
                        600, true);
                imageView.setImageBitmap(bitmap1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(),
                    "Please image",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void gallaryController() {
        imageView = getView().findViewById(R.id.imgGallary);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent,
                                "Please choose app for image"),
                        99);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_upload_photo,
                container, false);
        return view;
    }
}
