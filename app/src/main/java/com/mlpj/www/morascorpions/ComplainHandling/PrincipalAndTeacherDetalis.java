package com.mlpj.www.morascorpions.ComplainHandling;

/**
 * Created by DELL on 1/22/2018.
 * All Rights Reserved by MLPJ©
 */

public class PrincipalAndTeacherDetalis {
    private String teacherId;
    private String teacherName;
    private String principalId;
    private  String principalName;

    public PrincipalAndTeacherDetalis(String teacherId, String teacherName, String principalId, String principalName) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.principalId = principalId;
        this.principalName = principalName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public String getPrincipalName() {
        return principalName;
    }
}
