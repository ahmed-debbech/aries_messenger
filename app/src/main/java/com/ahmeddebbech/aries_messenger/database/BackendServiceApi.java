package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.model.ItemUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendServiceApi {

    @GET("/search/{user}/{text}")
    Call<ArrayList<ItemUser>> searchAllUsersByName(@Path("user") String userUid, @Path("text") String text);
}
