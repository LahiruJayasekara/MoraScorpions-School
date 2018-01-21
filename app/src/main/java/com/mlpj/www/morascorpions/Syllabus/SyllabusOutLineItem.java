package com.mlpj.www.morascorpions.Syllabus;

/**
 * Created by DELL on 1/21/2018.
 * All Rights Reserved by MLPJ©
 */

public class SyllabusOutLineItem {
    private String outline;
    private boolean doneOrNot;

    public SyllabusOutLineItem(String outline, boolean doneOrNot) {
        this.outline = outline;
        this.doneOrNot = doneOrNot;
    }

    public String getOutline() {
        return outline;
    }

    public boolean isDoneOrNot() {
        return doneOrNot;
    }
}
