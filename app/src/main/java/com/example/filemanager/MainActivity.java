package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DialogAddFile.Create{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = getExternalFilesDir(null);
loadFiles(file.getPath(),false);
    }


    public void loadFiles(String path,boolean addToBackStack){
        FragmentFiles fragmentFiles = new FragmentFiles();
        Bundle bundle = new Bundle();
        bundle.putString("path",path);
        fragmentFiles.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_files,fragmentFiles);
        if (addToBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void loadFiles(String path){
        this.loadFiles(path,true);
    }

    @Override
    public void createFolder(String name) {

    }
}