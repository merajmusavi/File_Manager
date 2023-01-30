package com.example.filemanager;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdaptor extends RecyclerView.Adapter<ItemsAdaptor.ViewHolder> {
    private List<File> files;
    private List<File> filtered;
    private onItemClick onItemClicked;


    ItemsAdaptor(List<File> files, onItemClick onItemClicked) {
        this.files = new ArrayList<>(files);
        this.filtered = this.files;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(filtered.get(position));
    }

    @Override
    public int getItemCount() {
        return filtered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon_file;
        TextView tv_file_name;
        View iv_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon_file = itemView.findViewById(R.id.iv_icon);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
            iv_more = itemView.findViewById(R.id.iv_more_ic);
        }

        public void bind(File file) {
            iv_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    onItemClicked.onDeleteClicked(file);
                                    break;
                                case R.id.move:
                                    onItemClicked.onMoveClicked(file);
                                case R.id.copy:
                                    onItemClicked.onCopyClicked(file);
                            }
                            return false;
                        }
                    });
                }
            });
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

    public interface onItemClick {
        void onFileClicked(File file);
        void onDeleteClicked(File file);
        void onCopyClicked(File file);
        void onMoveClicked(File file);
    }

    public void createFolder(File file) {
        files.add(file);
        notifyItemInserted(0);
    }

    public void delete_file(File file){
      int index = files.indexOf(file);
      if (index>-1){
          files.remove(index);
          notifyItemRemoved(index);
      }
    }
    public void search(String search){
if (search.length()>0){
List<File> files1 = new ArrayList<>();
    for (File file : this.files) {
if (file.getName().toLowerCase().contains(search.toLowerCase())){
    files1.add(file);
}
this.filtered = files1;
notifyDataSetChanged();
    }
}else {
    this.filtered = files;
}
    }
}
