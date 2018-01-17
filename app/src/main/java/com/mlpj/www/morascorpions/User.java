package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 9/4/2017.
 * All Rights Reserved by MLPJ©
 */

public class User implements Serializable{
    private String p_Id;
    private String roleName;
    private String name;
    private String picUrl;
    private String tpNumber;
    private String email;
    private String admissionNumber;
    private String admissionDate;
    private String teacherGrade;
    private String principalGrade;
    private String password;
    private String studentId;
    private String classRoomName;


    public User(String p_Id, String roleName, String name, String picUrl, String tpNumber, String email, String admissionNumber, String admissionDate, String teacherGrade, String principalGrade, String studentId, String classRoomName) {
        this.p_Id = p_Id;
        this.roleName = roleName;
        this.name = name;
        this.picUrl = picUrl;
        this.tpNumber = tpNumber;
        this.email = email;
        this.admissionNumber = admissionNumber;
        this.admissionDate = admissionDate;
        this.teacherGrade = teacherGrade;
        this.principalGrade = principalGrade;
        this.studentId = studentId;
        this.classRoomName = classRoomName;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getP_Id() {
        return p_Id;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getName() {
        return name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getTpNumber() {
        return tpNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public String getTeacherGrade() {
        return teacherGrade;
    }

    public String getPrincipalGrade() {
        return principalGrade;
    }

    public String getPassword() {
        return password;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

}
