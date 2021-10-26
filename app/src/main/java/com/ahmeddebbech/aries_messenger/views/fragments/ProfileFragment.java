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
import com.ahmeddebbech.aries_messenger.contracts.ContractProfileF;
import com.ahmeddebbech.aries_messenger.presenter.ProfilePresenter;
import com.ahmeddebbech.aries_messenger.views.activities.EditBioActivity;
import com.ahmeddebbech.aries_messenger.views.activities.EditDispNameActivity;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements ContractProfileF.View {

    ProfilePresenter presenter;

    TextView disp;
    TextView usr;
    TextView bio;
    ImageView photo;
    TextView num_con;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProfilePresenter(this);

        setupUi();
        addListeners();
    }
    public void setupUi(){
        disp = getView().findViewById(R.id.profile_disp_name);
        usr = getView().findViewById(R.id.profile_username);
        photo = getView().findViewById(R.id.profile_photo);
        bio = getView().findViewById(R.id.profile_bio);
        num_con = getView().findViewById(R.id.profile_numconnect);
        presenter.getData();
    }
    public void addListeners(){
        Button btn = getView().findViewById(R.id.profile_editDispName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditDispNameActivity.class);
                startActivity(intent);
            }
        });
        Button btn1 = getView().findViewById(R.id.profile_editBio);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditBioActivity.class);
                intent.putExtra("bio", bio.getText().toString());
                startActivity(intent);
            }
        });
    }
    @Override
    public void setTextsForViews(String disp, String usr, String bio, String photo, int number){
        Picasso.get().load(photo).resize(512,512).into(this.photo);
        this.disp.setText(disp);
        this.usr.setText(usr);
        this.bio.setText(bio);
        String o = getResources().getString(R.string.connection_number, String.valueOf(number));
        this.num_con.setText(o);
    }
}
