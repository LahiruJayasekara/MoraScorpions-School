package com.mlpj.www.morascorpions.Syllabus;

/**
 * Created by DELL on 1/21/2018.
 * All Rights Reserved by MLPJ©
 */

public class SubjectItem {
    private int subjectId;
    private String subjectName;

    public SubjectItem(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
