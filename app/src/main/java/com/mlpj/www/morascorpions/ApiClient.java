package com.mlpj.www.morascorpions;

import com.mlpj.www.morascorpions.Attendance.AttendanceDetail;
import com.mlpj.www.morascorpions.Chat.ChatItem;
import com.mlpj.www.morascorpions.Comment.CommentItem;
import com.mlpj.www.morascorpions.ComplainHandling.ComplainItem;
import com.mlpj.www.morascorpions.ComplainHandling.PrincipalAndTeacherDetalis;
import com.mlpj.www.morascorpions.NotesAndHwHandling.HwItem;
import com.mlpj.www.morascorpions.NotesAndHwHandling.NoteItem;
import com.mlpj.www.morascorpions.NoticeHandling.NoticeItem;
import com.mlpj.www.morascorpions.Syllabus.SubjectItem;
import com.mlpj.www.morascorpions.Syllabus.SyllabusOutLineItem;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("/api/Parent/GetTeachersOfAStudent/{parentPID}")
    Call<ArrayList<ChatItem>> getAllTeachersOfStudent(@Path("parentPID") String parentPID);

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

    @GET("/api/Subject/GetAllSubjectOfAStudent/{p_Id}")
    Call<ArrayList<SubjectItem>> getSubjectsOfAStudent(@Path("p_Id") String p_Id);

    @GET("/api/OutlineSyllabus/GetSyllabusesByStudent/{subject}/{className}")
    Call<ArrayList<SyllabusOutLineItem>> getSyllabusForStudent(@Path("subject") String subject, @Path("className") String className);

    @GET("/api/OutlineSyllabus/GetSyllabusesByParent/{p_Id}/{subject}")
    Call<ArrayList<SyllabusOutLineItem>> getSyllabusForParent(@Path("p_Id") String p_Id, @Path("subject") String subject);

    @GET("/api/complain/GetAllComplainByComplainerId/{p_Id}")
    Call<ArrayList<ComplainItem>> getComplainsForParent(@Path("p_Id") String p_Id);

    @GET("/api/complain/GetByParentId/{p_Id}")
    Call<PrincipalAndTeacherDetalis> getTeacherAndPrincipalForComplain(@Path("p_Id") String p_Id);

    @POST("/api/complain")
    Call<Void> makeComplain(@Body ComplainItem complainItem);

    @GET("/api/complain/GetAllComplainByComplaineeId/{p_Id}")
    Call<ArrayList<ComplainItem>> getComplainsForPrincipalAndTeacher(@Path("p_Id") String p_Id);

    @POST("/api/complain/UpdateAction")
    Call<Void> takeAction(@Body ComplainItem complainItem);

    @GET("/api/notice/")
    Call<ArrayList<NoticeItem>> getNoticesForPrincipal();

    @POST("/api/notice")
    Call<Void> addNotice(@Body NoticeItem noticeItem);

    @GET("/api/notice/NoticesGetById/{p_Id}")
    Call<ArrayList<NoticeItem>> getNoticesForUsers(@Path("p_Id") String p_Id);

    @GET("/api/note/GetByTernaryId/{ternaryId}")
    Call<ArrayList<NoteItem>> getNotesForTeacher(@Path("ternaryId") int ternaryId);

    @GET("/api/note/GetCommentsByNoteId/{noteId}")
    Call<ArrayList<CommentItem>> getComments(@Path("noteId") int noteId);

    @POST("/api/note/AddAComment")
    Call<Void> submitComment(@Body CommentItem commentItem);

    @GET("/api/note/GetNotesByStudentClassandSub/{className}/{subName}")
    Call<ArrayList<NoteItem>> getNotesForStudent(@Path("className") String className, @Path("subName") String subName);

}
