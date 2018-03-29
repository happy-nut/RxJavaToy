package com.dev.poqw.rxjavatoy.github;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {
  private static final String BASE_URL = "https://api.github.com";

  private static final class GithubRepoDeserializer implements JsonDeserializer<GithubRepo> {
    @Override
    public GithubRepo deserialize(JsonElement json, Type type, JsonDeserializationContext context)
        throws JsonParseException {
      GithubRepo githubRepo = new GithubRepo();
      JsonObject repoJsonObject =  json.getAsJsonObject();
      JsonElement ownerJsonElement = repoJsonObject.get("owner");
      JsonObject ownerJsonObject = ownerJsonElement.getAsJsonObject();

      githubRepo.name = repoJsonObject.get("name").getAsString();
      githubRepo.url = repoJsonObject.get("url").getAsString();
      githubRepo.owner = ownerJsonObject.get("login").getAsString();
      return githubRepo;
    }
  }

  public GithubApi getApi() {
    Gson gson = new GsonBuilder()
        .registerTypeAdapter(GithubRepo.class, new GithubRepoDeserializer())
        .create();
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(new OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GithubApi.class);
  }
}
