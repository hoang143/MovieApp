package com.example.mockprojectv3.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mockprojectv3.response.MovieSearchResponse;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class UpcomingViewModel extends ViewModel {
    public MutableLiveData<MovieSearchResponse> upcomingMutableLiveData = new MutableLiveData<MovieSearchResponse>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
