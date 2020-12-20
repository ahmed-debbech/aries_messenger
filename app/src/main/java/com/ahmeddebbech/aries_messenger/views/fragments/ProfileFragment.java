package com.ahmeddebbech.aries_messenger.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.model.User;
import com.ahmeddebbech.aries_messenger.views.activities.EditBio;
import com.ahmeddebbech.aries_messenger.views.activities.EditDispName;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    TextView disp;
    TextView usr;
    TextView bio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        disp = getView().findViewById(R.id.profile_disp_name);
        usr = getView().findViewById(R.id.profile_username);

        disp.setText(User.getInstance().getDisplayName());
        usr.setText(User.getInstance().getUsername());

        ImageView ig = getView().findViewById(R.id.profile_photo);
        Picasso.get().load(User.getInstance().getPhotoURL()).into(ig);

        bio = getView().findViewById(R.id.profile_bio);
        bio.setText(User.getInstance().getBio());

        Button btn = getView().findViewById(R.id.profile_editDispName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditDispName.class);
                startActivity(intent);
            }
        });
        Button btn1 = getView().findViewById(R.id.profile_editBio);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditBio.class);
                intent.putExtra("bio", bio.getText().toString());
                startActivity(intent);
            }
        });

    }
}
