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

    @GET("/api/TeacherDetails/ClassesOfTeacher/{teacherId}")
    Call<ArrayList<ClassRoomItem>> getClassListOfTeacher(@Path("teacherId") String teacherId);

    @GET("/api/TeacherDetails/GetAllParentOfAClass/{className}")
    Call<ArrayList<ChatItem>> getAllParentsOfClass(@Path("className") String className);

    @GET("/api/Parent/GetTeachersOfAStudent/{teacherPID}")
    Call<ArrayList<ChatItem>> getAllTeachersOfStudent(@Path("teacherPID") String teacherPID);

    @GET("/api/TeacherDetails/SubjectClassesOfTeacher/{teacherPID}")
    Call<ArrayList<ClassSubjectOfTeacher>> getSubjectClassesOfTeacher(@Path("teacherPID") String teacherPID);

    @GET("/api/Homework/GetHomeworkMobileApp/{ternaryId}")
    Call<ArrayList<HwItem>> getHomeworksForTeacher(@Path("ternaryId") int ternaryId);

    @GET("/api/Homework/GetHomeworkOfAStudent/{p_Id}")
    Call<ArrayList<HwItem>> getHomeworksForStudent(@Path("p_Id") String p_Id);

    @GET("/api/Homework/GetHomeworkOfAParent/{p_Id}")
    Call<ArrayList<HwItem>> getHomeworksForParent(@Path("p_Id") String p_Id);

    @GET("/api/AttendanceDetails/GetAttendancePercentageOfAStudent/{p_Id}")
    Call<Float> getAttendancePercentageStudent(@Path("p_Id") String p_Id);

    @GET("/api/AttendanceDetails/GetAttendancePercentageOfAParentStd/{p_Id}")
    Call<Float> getAttendancePercentageStudentForParent(@Path("p_Id") String p_Id);

}
