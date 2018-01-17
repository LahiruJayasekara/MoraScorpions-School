package com.mlpj.www.morascorpions;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by DELL on 11/24/2017.
 * All Rights Reserved by MLPJ©
 */

public interface ApiClient {
    @GET("/api/Login/{email}/{password}")
    Call<ArrayList<User>> login(@Path("email") String email, @Path("password") String password);

    @POST("/api/AttendanceDetails")
    Call<Void> markAttendance(@Body ArrayList<AttendanceDetail> markedList);

    @GET("/api/AttendanceDetails/{date}/{className}")
    Call<ArrayList<AttendanceDetail>> getAttendanceSheet(@Path("date") String date, @Path("className") String className);

    @Multipart
    @POST("uploadFile")
    Call<ResponseBody> uploadFile(@Part("noteTitle") String noteTitle, @Part MultipartBody.Part file);
}
