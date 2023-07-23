package com.example.thecoffeehouse.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditDialogFragment extends DialogFragment {

    private static final String ARG_FIELD = "field";

    private String field;

    private static final String ARG_EDITED_TEXT = "edited_text";

    private String editedText;



    public static EditDialogFragment newInstance(String field, String currentText) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FIELD, field);
        args.putString("currentText", currentText);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            field = getArguments().getString(ARG_FIELD);
        }
    }

    public interface OnTextEditedListener {
        void onTextEdited(String field, String editedText);
    }
    private OnTextEditedListener onTextEditedListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure that the parent fragment (ProfileFragment) implements the callback interface
        try {
            onTextEditedListener = (OnTextEditedListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Parent fragment must implement OnTextEditedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Edit " + field);

        final EditText editText = new EditText(requireContext());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.requestFocus();

        // Get the current text from the arguments and set it as the default text in the EditText
        Bundle args = getArguments();
        if (args != null) {
            String currentText = args.getString("currentText");
            if (currentText != null && !currentText.isEmpty()) {
                editText.setText(currentText);
                editText.setSelection(currentText.length());
            }
        }


        builder.setView(editText);

        if (savedInstanceState != null) {
            editedText = savedInstanceState.getString(ARG_EDITED_TEXT);
            editText.setText(editedText);
        }

        builder.setPositiveButton("OK", (dialog, which) -> {
            editedText = editText.getText().toString().trim();
            Log.d("EditDialogFragment", "Field: " + field + ", Edited Text: " + editedText);
            if (!editedText.isEmpty() && onTextEditedListener != null) {
                // Send the edited text back to the parent fragment (ProfileFragment)
                onTextEditedListener.onTextEdited(field, editedText);
            }
        });

        builder.setNegativeButton("Cancel", null);

        return builder.create();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_EDITED_TEXT, editedText);
    }
}
