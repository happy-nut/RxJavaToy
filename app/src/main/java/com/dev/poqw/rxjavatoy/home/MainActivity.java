package com.dev.poqw.rxjavatoy.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dev.poqw.rxjavatoy.R;
import com.dev.poqw.rxjavatoy.databinding.ActivityMainBinding;
import com.dev.poqw.rxjavatoy.github.GithubClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private static final String OWNER = "poqw";

  private RepoItemAdapter adapter;
  private Disposable disposable;
  private GithubClient github;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    adapter = new RepoItemAdapter(new ArrayList<>());
    binding.recyclerView.setAdapter(adapter);

    github = new GithubClient();
    disposable = github.getApi().getRepos(OWNER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe( items -> adapter.updateItems(items));
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    disposable.dispose();
  }
}