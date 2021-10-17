package com.ahmeddebbech.aries_messenger.database;

import com.ahmeddebbech.aries_messenger.database.model.MessagePersist;
import com.ahmeddebbech.aries_messenger.model.Feedback;
import com.ahmeddebbech.aries_messenger.model.ItemUser;
import com.ahmeddebbech.aries_messenger.model.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BackendServiceApi {

    @GET("/search/{text}")
    Call<ArrayList<ItemUser>> searchAllUsersByName(@Header("authorization") String token, @Path("text") String text);

    @POST("/send_msg")
    Call<Message> sendMessage(@Body MessagePersist msg);

    @POST("/push_feedback")
    Call<Boolean> pushFeedback(@Body Feedback fdb);
}
