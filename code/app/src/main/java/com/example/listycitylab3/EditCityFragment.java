package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public interface OnEditCityListener {
        void onCityEdited(int position, String newName, String newProvince);
    }

    private static final String ARG_POSITION = "arg_position";
    private static final String ARG_CITY = "arg_city";

    private OnEditCityListener listener;

    // Factory method using City object
    public static EditCityFragment newInstance(int position, City city) {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putSerializable(ARG_CITY, city);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnEditCityListener) {
            listener = (OnEditCityListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEditCityListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = requireArguments();
        final int position = args.getInt(ARG_POSITION);
        City city = (City) args.getSerializable(ARG_CITY);

        String currentName = (city != null) ? city.getName() : "";
        String currentProvince = (city != null) ? city.getProvince() : "";

        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill with existing city info
        editCityName.setText(currentName);
        editProvinceName.setText(currentProvince);

        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit City")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = editCityName.getText().toString().trim();
                    String newProv = editProvinceName.getText().toString().trim();
                    if (listener != null) {
                        listener.onCityEdited(position, newName, newProv);
                    }
                })
                .create();
    }
}
