package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.File;

public class MainActivity extends AppCompatActivity implements DialogAddFile.Create {
    ImageView add_new_folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = getExternalFilesDir(null);
        loadFiles(file.getPath(), false);
        EditText editText = findViewById(R.id.et_main_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_files);
                if (fragment instanceof FragmentFiles) {
                    ((FragmentFiles) fragment).search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggle_gp);
        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_files);

                if (checkedId == R.id.main_l && isChecked){
                    if (fragment instanceof FragmentFiles){
                        ((FragmentFiles) fragment).setConstant(Constant.ROW);
                    }else if (checkedId==R.id.grid_l && isChecked){
                        ((FragmentFiles) fragment).setConstant(Constant.GRID);

                    }

                }
            }
        });
        add_new_folder = findViewById(R.id.add_new_folder);
        add_new_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAddFile().show(getSupportFragmentManager(), null);
            }
        });
    }


    public void loadFiles(String path, boolean addToBackStack) {
        FragmentFiles fragmentFiles = new FragmentFiles();
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        fragmentFiles.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_files, fragmentFiles);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public void loadFiles(String path) {
        this.loadFiles(path, true);
    }

    @Override
    public void createFolder(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_files);
        if (fragment instanceof FragmentFiles) {
            ((FragmentFiles) fragment).createFolder(name);
        }
    }
}