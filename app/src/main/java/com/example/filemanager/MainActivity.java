package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DialogAddFile.Create{
ImageView add_new_folder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = getExternalFilesDir(null);
loadFiles(file.getPath(),false);
add_new_folder = findViewById(R.id.add_new_folder);
add_new_folder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new DialogAddFile().show(getSupportFragmentManager(),null);
    }
});
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_files);
        if (fragment instanceof FragmentFiles){
            ((FragmentFiles)fragment).createFolder(name);
        }
    }
}