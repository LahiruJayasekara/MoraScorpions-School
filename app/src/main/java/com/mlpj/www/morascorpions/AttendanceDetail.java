package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 9/21/2017.
 * All Rights Reserved by MLPJ©
 */

public class AttendanceDetail implements Serializable{

    private String p_Id;
    private String name;
    private boolean presentAbsent;
    private String date;

    public AttendanceDetail(String p_Id, String name, boolean presentAbsent, String date) {
        this.p_Id = p_Id;
        this.name = name;
        this.presentAbsent = presentAbsent;
        this.date = date;
    }

    public AttendanceDetail(String p_Id, boolean presentAbsent, String date) {
        this.p_Id = p_Id;
        this.presentAbsent = presentAbsent;
        this.date = date;
    }

    public AttendanceDetail(String p_Id, String name) {
        this.p_Id = p_Id;
        this.name = name;
        this.presentAbsent = false;
    }

    public String getP_Id() {
        return p_Id;
    }

    public String getName() {
        return name;
    }

    public boolean isPresentAbsent() {
        return presentAbsent;
    }

    public String getDate() {
        return date;
    }

    public void setPresentAbsent(boolean presentAbsent) {
        this.presentAbsent = presentAbsent;
    }

}
