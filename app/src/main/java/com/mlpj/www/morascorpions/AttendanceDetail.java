package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 9/21/2017.
 * All Rights Reserved by MLPJ©
 */

public class AttendanceDetail implements Serializable{

    int studentId;
    public String name;
    boolean checked;
    String date;

    AttendanceDetail(int studentId, String name, boolean checked, String date) {
        this.studentId = studentId;
        this.name = name;
        this.checked = checked;
        this.date = date;
    }
}
