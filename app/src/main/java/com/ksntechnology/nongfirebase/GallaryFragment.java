package com.ksntechnology.nongfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ksntechnology.nongfirebase.R;

public class GallaryFragment extends Fragment {
    private ImageView imageView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Gallary
        gallaryController();
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
