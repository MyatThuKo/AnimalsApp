package com.example.animalsapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.animalsapp.R;
import com.example.animalsapp.viewmodel.ListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    @BindView(R.id.animalRefreshLayout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.animalList)
    RecyclerView animalList;

    @BindView(R.id.errorMessage)
    TextView errorMessage;

    @BindView(R.id.loadingView)
    ProgressBar loadingView;

    private ListViewModel viewModel;
    private AnimalListAdapter listAdapter = new AnimalListAdapter();

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        viewModel.animals.observe(this, list -> {
            if (list != null) {
                animalList.setVisibility(View.VISIBLE);
                listAdapter.updateAnimalList(list);
            }
        });

        viewModel.loading.observe(this, loading -> {
            loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
            if (loading) {
                errorMessage.setVisibility(View.GONE);
                animalList.setVisibility(View.GONE);
            }
        });

        viewModel.error.observe(this, error -> {
            errorMessage.setVisibility(error ? View.VISIBLE : View.GONE);
        });

        viewModel.refresh();

        if (animalList != null) {
            animalList.setLayoutManager(new GridLayoutManager(getContext(), 2));
            animalList.setAdapter(listAdapter);
        }

        refreshLayout.setOnRefreshListener(() -> {
            animalList.setVisibility(View.GONE);
            errorMessage.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
            viewModel.hardRefresh();
            refreshLayout.setRefreshing(false);
        });
    }
}
