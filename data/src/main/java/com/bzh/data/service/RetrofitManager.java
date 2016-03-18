package com.bzh.data.service;

import android.content.Context;

import com.bzh.common.context.GlobalContext;
import com.bzh.data.film.service.IFilmService;
import com.bzh.data.home.IHomePageService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class RetrofitManager {

    private final IComicService comicService;
    private final IFilmService filmService;
    private final IGameService gameService;
    private final ITvService tvService;
    private final IVarietyService varietyService;
    private final IHomePageService homePageService;

    private static RetrofitManager retrofitManager;

    private RetrofitManager(Context context) {

        int cacheSize = 10 * 1024 * 1024;

        String cachePath;
        if (null == context) {
            cachePath = "cache.dytt";
        } else {
            cachePath = context.getCacheDir().getAbsolutePath() + File.separator + "cache.dytt";
        }
        Cache cache = new Cache(new File(cachePath), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ygdy8.net")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        homePageService = retrofit.create(IHomePageService.class);
        comicService = retrofit.create(IComicService.class);
        filmService = retrofit.create(IFilmService.class);
        gameService = retrofit.create(IGameService.class);
        tvService = retrofit.create(ITvService.class);
        varietyService = retrofit.create(IVarietyService.class);
    }

    public static RetrofitManager getInstance() {
        if (retrofitManager == null && GlobalContext.getInstance() != null) {
            retrofitManager = new RetrofitManager(GlobalContext.getInstance());
        }
        return retrofitManager;
    }

    public IHomePageService getHomePageService() {
        return homePageService;
    }

    public IComicService getComicService() {
        return comicService;
    }

    public IFilmService getFilmService() {
        return filmService;
    }

    public IGameService getGameService() {
        return gameService;
    }

    public ITvService getTvService() {
        return tvService;
    }

    public IVarietyService getVarietyService() {
        return varietyService;
    }
}


