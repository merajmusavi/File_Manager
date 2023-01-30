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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class FragmentFiles extends Fragment implements ItemsAdaptor.onItemClick {
    private String path;
    private ItemsAdaptor itemsAdaptor;
    RecyclerView recyclerView;

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

        if (currentFolder.getName().equalsIgnoreCase("files")) {
            tv_path.setText("external storage");
        } else {
            tv_path.setText(currentFolder.getName());
        }

        ImageView back = view.findViewById(R.id.back_ic);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        recyclerView = view.findViewById(R.id.rec_files);
        itemsAdaptor = new ItemsAdaptor(Arrays.asList(files), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(itemsAdaptor);
        return view;
    }

    @Override
    public void onFileClicked(File file) {
        if (file.isDirectory()) {
            ((MainActivity) getActivity()).loadFiles(file.getPath());
        }
    }

    @Override
    public void onDeleteClicked(File file) {
        if (file.delete()) {
            itemsAdaptor.delete_file(file);
        }
    }

    @Override
    public void onCopyClicked(File file) {
        try {
            copy(file,getPath(file.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMoveClicked(File file) {
        try {
            copy(file,getPath(file.getName()));
            onDeleteClicked(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createFolder(String folderName) {
        File file = new File(path + File.separator + folderName);

        if (!file.exists()) {
            if (file.mkdir()) {
                itemsAdaptor.createFolder(file);
                recyclerView.scrollToPosition(0);
            }
        }
    }

    public void copy(File source, File destination) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, length);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
    private File getPath(String fileName){
        return new File(getContext().getExternalFilesDir(null).getPath()+File.separator+"xo"+ File.separator+fileName);
    }
}
