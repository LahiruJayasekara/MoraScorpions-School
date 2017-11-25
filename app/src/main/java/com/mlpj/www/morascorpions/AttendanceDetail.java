package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 9/21/2017.
 * All Rights Reserved by MLPJ©
 */

public class AttendanceDetail implements Serializable{

    int studentId;
    public String name;
    boolean present;
    String date;

    public AttendanceDetail(int studentId, boolean present, String date) {
        this.studentId = studentId;
        this.present = present;
        this.date = date;
    }

    AttendanceDetail(int studentId, String name, boolean present, String date) {
        this.studentId = studentId;
        this.name = name;
        this.present = present;
        this.date = date;
    }
}
