package com.example.filemanager;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class ItemsAdaptor extends RecyclerView.Adapter<ItemsAdaptor.ViewHolder> {
    List<File> files;
    private onItemClick onItemClicked;


    ItemsAdaptor(List<File> files,onItemClick onItemClicked) {
        this.files = files;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(files.get(position));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon_file;
        TextView tv_file_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon_file = itemView.findViewById(R.id.iv_icon);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);

        }

        public void bind(File file) {
            if (file.isDirectory()) {
                icon_file.setImageResource(R.drawable.ic_folder_black_32dp);
            } else {
                icon_file.setImageResource(R.drawable.ic_file_black_32dp);

            }
            tv_file_name.setText(file.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClicked.onFileClicked(file);
                }
            });
        }
    }
    public interface onItemClick{
        void onFileClicked(File file);
    }
}
