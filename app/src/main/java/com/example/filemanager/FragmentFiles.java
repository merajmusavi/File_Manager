package com.example.filemanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Arrays;

public class FragmentFiles extends Fragment implements ItemsAdaptor.onItemClick {
    private String path;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.files_frg, container, false);
        File currentFolder = new File(path);
        File[] files = currentFolder.listFiles();
        TextView tv_path = view.findViewById(R.id.text_path);
        tv_path.setText(path);
        ImageView back = view.findViewById(R.id.back_ic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.rec_files);
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(new ItemsAdaptor(Arrays.asList(files),this));
        return  view;
    }

    @Override
    public void onFileClicked(File file) {
        if (file.isDirectory()) {
            ((MainActivity) getActivity()).loadFiles(file.getPath());
        }
    }
}
