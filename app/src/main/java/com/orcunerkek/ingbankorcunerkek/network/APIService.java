package com.orcunerkek.ingbankorcunerkek.network;

import com.orcunerkek.ingbankorcunerkek.model.GithubModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("users/{user}/repos")
    Call<List<GithubModel>> getGithubList(@Path(value = "user",encoded = false) String user);
}
