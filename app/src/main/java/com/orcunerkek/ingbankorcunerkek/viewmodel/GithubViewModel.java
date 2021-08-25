package com.orcunerkek.ingbankorcunerkek.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.orcunerkek.ingbankorcunerkek.model.GithubModel;
import com.orcunerkek.ingbankorcunerkek.network.APIService;
import com.orcunerkek.ingbankorcunerkek.network.RetroInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubViewModel extends ViewModel {
    private MutableLiveData<List<GithubModel>> githubList;

    public GithubViewModel()  {
        githubList = new MutableLiveData<>();
    }

    public MutableLiveData<List<GithubModel>> getGithubListObserver(){
        return githubList;
    }

    public void makeApiCall(String username){
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<GithubModel>> call  = apiService.getGithubList(username);
        call.enqueue(new Callback<List<GithubModel>>() {
            @Override
            public void onResponse(Call<List<GithubModel>> call, Response<List<GithubModel>> response) {
                githubList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GithubModel>> call, Throwable t) {
                githubList.postValue(null);
            }
        });
    }

}
