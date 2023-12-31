package com.example.thecoffeehouse.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.thecoffeehouse.AppDatabase;
import com.example.thecoffeehouse.R;
import com.example.thecoffeehouse.SharedViewModel;
import com.example.thecoffeehouse.entities.ProfileEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;



public class ProfileFragment extends Fragment implements EditDialogFragment.OnTextEditedListener {

    TextView profileNameTextView;
    TextView profileEmailTextView;
    TextView profilePhoneTextView;

    TextView profileAddressTextView;

    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileNameTextView = view.findViewById(R.id.profileName);
        profileEmailTextView = view.findViewById(R.id.profileEmail);
        profilePhoneTextView = view.findViewById(R.id.profilePhone);
        profileAddressTextView = view.findViewById(R.id.profileAddress);

        // Get the latest address from the SharedViewModel and set it to the TextView
        String latestAddress = sharedViewModel.getLatestAddressLiveData().getValue();
        if (latestAddress != null) {
            profileAddressTextView.setText(latestAddress);
        }


        new LoadProfileDataAsyncTask().execute();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
                BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        ImageButton editNameButton = view.findViewById(R.id.editName);
        ImageButton editEmailButton = view.findViewById(R.id.editEmail);
        ImageButton editPhoneButton = view.findViewById(R.id.editPhone);
        ImageButton editAddressButton = view.findViewById(R.id.editAddress);

        editNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to open the edit dialog for the profile name
                openEditDialog("name");
            }
        });

        editEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to open the edit dialog for the profile email
                openEditDialog("email");
            }
        });

        editPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to open the edit dialog for the profile phone
                openEditDialog("phone");
            }
        });

        editAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event to open the edit dialog for the profile address
                openEditDialog("address");
            }
        });
    }

    @Override
    public void onTextEdited(String field, String editedText) {
        requireActivity().runOnUiThread(() -> {
            if ("name".equals(field)) {
                profileNameTextView.setText(editedText);
            } else if ("email".equals(field)) {
                profileEmailTextView.setText(editedText);
            } else if ("phone".equals(field)) {
                profilePhoneTextView.setText(editedText);
            } else if ("address".equals(field)) {
                profileAddressTextView.setText(editedText);
                sharedViewModel.setLatestAddress(editedText);
            }
        });


        new Thread(() -> {
            AppDatabase database = AppDatabase.getInstance(requireContext());
            ProfileEntity profileEntity = new ProfileEntity(field, editedText);
            database.profileDao().insertProfile(profileEntity);
        }).start();
    }

    private class LoadProfileDataAsyncTask extends AsyncTask<Void, Void, List<ProfileEntity>> {

        @Override
        protected List<ProfileEntity> doInBackground(Void... voids) {
            AppDatabase database = AppDatabase.getInstance(requireContext());
            return database.profileDao().getAllProfiles();
        }

        @Override
        protected void onPostExecute(List<ProfileEntity> profileEntities) {
            super.onPostExecute(profileEntities);
            if (profileEntities != null && !profileEntities.isEmpty()) {
                for (ProfileEntity entity : profileEntities) {
                    String field = entity.getField();
                    String editedText = entity.getEditedText();

                    if ("name".equals(field)) {
                        profileNameTextView.setText(editedText);
                    } else if ("email".equals(field)) {
                        profileEmailTextView.setText(editedText);
                    } else if ("phone".equals(field)) {
                        profilePhoneTextView.setText(editedText);
                    } else if ("address".equals(field)) {
                        profileAddressTextView.setText(editedText);
                    }
                }
            }
        }
    }

    private void openEditDialog(String field) {
        FragmentManager fragmentManager = getChildFragmentManager();
        String currentText = "";

        // Get the current text for the corresponding field and pass it to the dialog
        if ("name".equals(field)) {
            currentText = profileNameTextView.getText().toString();
        } else if ("email".equals(field)) {
            currentText = profileEmailTextView.getText().toString();
        } else if ("phone".equals(field)) {
            currentText = profilePhoneTextView.getText().toString();
        } else if ("address".equals(field)) {
            currentText = profileAddressTextView.getText().toString();
        }


        EditDialogFragment dialog = EditDialogFragment.newInstance(field, currentText);
        dialog.show(fragmentManager, "EditDialogFragment");
    }
}