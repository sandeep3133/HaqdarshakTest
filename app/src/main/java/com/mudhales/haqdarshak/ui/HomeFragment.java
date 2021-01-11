package com.mudhales.haqdarshak.ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.mudhales.haqdarshak.R;
import com.mudhales.haqdarshak.adapter.HomeAdapter;
import com.mudhales.haqdarshak.data.Article;
import com.mudhales.haqdarshak.utils.LocalDatabase;
import com.mudhales.haqdarshak.utils.Utils;

import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView rvItems;
    private HomeAdapter rowDataAdapter;
    private ProgressDialog progressDialog;
    public ListDataViewModel listViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setUpUI();
        return view;
    }


    // To set up view by SM
    private void setUpUI() {
        listViewModel = ViewModelProviders.of(this).get(ListDataViewModel.class);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        rvItems = view.findViewById(R.id.rvItems);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getActivity().getString(R.string.Loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvItems.setLayoutManager(layoutManager);
        rvItems.setNestedScrollingEnabled(false);
        rvItems.setHasFixedSize(true);
        swipeContainer.setOnRefreshListener(this);
        loadListData();
    }



    @Override
    public void onRefresh() {
        checkRecordsData();
    }

    // To fetch data from server by SM
    private void loadListData() {
        progressDialog.show();
        listViewModel.getListResponseLiveData().observe(getActivity(), response -> {
            if (response != null) {
                showMessage("Success");
             //   ((MainActivity) getActivity()).setActionBarTitle(response.getTitle());
                if (response.getArticles()!= null) {
                    if (rowDataAdapter != null) {
                        rowDataAdapter.refreshList(response.getArticles());
                    }
                    else {
                        rowDataAdapter = new HomeAdapter(getActivity(), response.getArticles());
                        rvItems.setAdapter(rowDataAdapter);
                    }
                }

            } else {
                showMessage("Failed. try again.");
            }

            progressDialog.cancel();
        });
    }// To fetch data from server by SM 201912111355

    private void checkRecordsData() {
        swipeContainer.setRefreshing(true);
        listViewModel.getListResponseLiveData().observe(this, response -> {
            if (response != null) {
                showMessage("Success");
                if (response.getArticles() != null) {
                    if (rowDataAdapter != null) {
                        rowDataAdapter.refreshList(response.getArticles());
                    } else {
                        rowDataAdapter = new HomeAdapter(getActivity(), response.getArticles());
                        rvItems.setAdapter(rowDataAdapter);
                    }
                }

            } else {
                showMessage("Failed. try again.");
            }

            swipeContainer.setRefreshing(false);
        });
    }

    private void showMessage(String strMessage) {
        Snackbar snackbar = Snackbar
                .make(view, strMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}