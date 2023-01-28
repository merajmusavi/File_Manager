package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = getExternalFilesDir(null);
        FragmentFiles fragmentFiles = new FragmentFiles();
        Bundle bundle = new Bundle();
        bundle.putString("path",file.getPath());
        fragmentFiles.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_files,fragmentFiles);
        fragmentTransaction.commit();
    }
}