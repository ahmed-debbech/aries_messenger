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
import com.ahmeddebbech.aries_messenger.contracts.ContractMain;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.presenter.ConnectionsFragPresenter;
import com.ahmeddebbech.aries_messenger.presenter.MessengerManager;
import com.ahmeddebbech.aries_messenger.presenter.UserManager;
import com.ahmeddebbech.aries_messenger.views.activities.MainActivity;
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

    public ConnectionsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        pres.loadContacts(UserManager.getInstance().getUserModel().getUid());
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
            srl.setRefreshing(false);
             if (list == null) {
                return;
            } else {
                if (list.isEmpty()) {
                    connections_grid.setAdapter(adapter);
                } else {
                    connections_grid.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void sendResult(Bundle result) {
        getParentFragmentManager().setFragmentResult("result", result);
    }
}