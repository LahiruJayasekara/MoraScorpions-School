package com.mlpj.www.morascorpions;

/**
 * Created by DELL on 1/18/2018.
 * All Rights Reserved by MLPJ©
 */

public class ClassSubjectOfTeacher {
    int id;
    String classRoomName;
    String subjectName;

    public ClassSubjectOfTeacher(int id, String classRoomName, String subjectName) {
        this.id = id;
        this.classRoomName = classRoomName;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
