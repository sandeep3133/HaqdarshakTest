package com.mudhales.haqdarshak.ui;

import android.app.Application;


import com.mudhales.haqdarshak.data.FeedData;
import com.mudhales.haqdarshak.repository.ListRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ListDataViewModel extends ViewModel {
    private ListRepository listRepository;
    private LiveData<FeedData> listResponseLiveData;

    //    public ListDataViewModel(@NonNull Application application) {
//        //super(application);
//        listRepository = new ListRepository();
//    }
    public ListDataViewModel() {
        listRepository = new ListRepository();
    }

    public LiveData<FeedData> getListResponseLiveData() {
        listResponseLiveData = listRepository.checkInfoDetails();
        return listResponseLiveData;
    }
}
