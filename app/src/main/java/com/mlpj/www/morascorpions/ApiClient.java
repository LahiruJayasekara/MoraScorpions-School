package com.mlpj.www.morascorpions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by DELL on 11/24/2017.
 * All Rights Reserved by MLPJ©
 */

public interface ApiClient {

    @POST("login")
    Call<User> login(@Body User user);

    @FormUrlEncoded
    @POST("getAttendanceSheet")
    Call<ArrayList<AttendanceDetail>> getAttendanceSheet(@Field("className") String className, @Field("date") String date);

    @POST("markAttendance")
    Call<Void> markAttendance(@Body ArrayList<AttendanceDetail> markedList);
}
