package com.ahmeddebbech.aries_messenger.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractConnectionsFrag;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.ConnectionsFragPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;

import java.util.List;

public class ConnectionsFragment extends Fragment implements ContractConnectionsFrag.View {

    private ContractConnectionsFrag.Presenter pres = new ConnectionsFragPresenter(this);

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConnectionsFragment() {
        // Required empty public constructor
    }
    public static ConnectionsFragment newInstance(String param1, String param2) {
        ConnectionsFragment fragment = new ConnectionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pres.loadContacts(UserManager.getInstance().getUserModel().getUid());
        return inflater.inflate(R.layout.fragment_connections, container, false);
    }

    @Override
    public void showContacts(List<ItemUser> list) {
        if(list.isEmpty()){
            System.out.println("fer8aaaaaaa");
        }
        for(ItemUser u : list){
            System.out.println("4444444" + u.getDisplayName());
        }
    }
}