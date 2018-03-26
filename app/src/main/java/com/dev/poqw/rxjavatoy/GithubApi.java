package com.dev.poqw.rxjavatoy;

import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
  String BASE_URL = "https://api.github.com";

  @GET("users/{owner}/repos")
  Single<List<GithubRepo>> getRepos(@Path("owner") String owner);
}
