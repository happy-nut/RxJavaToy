package com.dev.poqw.rxjavatoy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dev.poqw.rxjavatoy.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
  private final OkHttpClient CLIENT = new OkHttpClient();
  private static final String OWNER = "poqw";
  private RepoAdapter adapter;
  private Disposable disposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    adapter = new RepoAdapter(new ArrayList<>(), this);
    binding.recyclerView.setAdapter(adapter);
    disposable = createGithubAPI().getRepos(OWNER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe( items -> adapter.updateItems(items));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    disposable.dispose();
  }

  private GithubApi createGithubAPI() {
    Gson gson = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .registerTypeAdapter(GithubRepo.class, new GithubRepoDeserializer())
        .create();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(GithubApi.BASE_URL)
        .client(CLIENT)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    return retrofit.create(GithubApi.class);
  }
}