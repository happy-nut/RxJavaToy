package com.dev.poqw.rxjavatoy.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.dev.poqw.rxjavatoy.databinding.ItemRepoBinding;
import com.dev.poqw.rxjavatoy.github.GithubRepo;
import java.util.List;

public class RepoItemAdapter extends RecyclerView.Adapter<RepoItemAdapter.ViewHolder> {

  private final List<GithubRepo> repos;

  RepoItemAdapter(List<GithubRepo> repos) {
    this.repos = repos;
  }

  void updateItems(List<GithubRepo> items) {
    this.repos.clear();
    this.repos.addAll(items);
    notifyDataSetChanged();
  }

  @Override
  public RepoItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RepoItemAdapter.ViewHolder(ItemRepoBinding.inflate(
        LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(RepoItemAdapter.ViewHolder holder, int position) {
    holder.bind(repos.get(position));
  }

  @Override
  public int getItemCount() {
    return repos.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemRepoBinding binding;

    ViewHolder(ItemRepoBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bind(GithubRepo repo) {
      binding.ownerView.setText(repo.owner);
      binding.nameView.setText(repo.name);
      binding.urlView.setText(repo.url);
    }
  }
}
