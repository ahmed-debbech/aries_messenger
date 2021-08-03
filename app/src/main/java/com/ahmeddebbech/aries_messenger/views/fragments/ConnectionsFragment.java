package com.ahmeddebbech.aries_messenger.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmeddebbech.aries_messenger.R;
import com.ahmeddebbech.aries_messenger.contracts.ContractConnectionsFrag;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.ConnectionsFragPresenter;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.adapters.ContactsGridAdapter;
import com.ahmeddebbech.aries_messenger.views.adapters.UserItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsFragment extends Fragment implements ContractConnectionsFrag.View {

    private ContractConnectionsFrag.Presenter pres = new ConnectionsFragPresenter(this);
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView connections_grid;
    private ContactsGridAdapter adapter;
    private SwipeRefreshLayout srl;
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
        // Inflate the layout for this fragments
        pres.loadContacts(UserManager.getInstance().getUserModel().getUid());
        return inflater.inflate(R.layout.fragment_connections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        srl = (SwipeRefreshLayout) getActivity().findViewById(R.id.connections_swipe_refresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pres.loadContacts(UserManager.getInstance().getUserModel().getUid());
            }
        });
    }

    @Override
    public void showContacts(List<ItemUser> list) {
        if(getActivity() != null) {
            srl = (SwipeRefreshLayout) getActivity().findViewById(R.id.connections_swipe_refresh);
            srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    pres.loadContacts(UserManager.getInstance().getUserModel().getUid());
                }
            });
            connections_grid = (RecyclerView) getActivity().findViewById(R.id.connections_grid);
            layoutManager = new GridLayoutManager(getActivity(), 3);
            connections_grid.setLayoutManager(layoutManager);

            adapter = new ContactsGridAdapter(list, getContext());
            if (list == null) {
                return;
            } else {
                if (list.isEmpty()) {
                    return;
                } else {
                    connections_grid.setAdapter(adapter);
                    srl.setRefreshing(false);
                }
            }
            //pres.checkNewMessages();
        }
    }

    @Override
    public void showNewMessageBadge() {

    }
}