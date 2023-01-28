package com.example.filemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogAddFile extends DialogFragment {
    private Create create;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        create = (Create) context;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_new_folder, null);
        TextInputLayout textInputLayout = view.findViewById(R.id.til);
        TextInputEditText textInputEditText = view.findViewById(R.id.edit_text_add);
        MaterialButton materialButton = view.findViewById(R.id.btn_create_folder);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputEditText.length() > 0) {
                    create.createFolder(textInputEditText.getText().toString());
                    dismiss();
                } else {
                    textInputLayout.setError("folder name can not be empty");

                }
            }
        });
        return alertDialog.setView(view).create();
    }

    public interface Create {
        void createFolder(String name);
    }
}
