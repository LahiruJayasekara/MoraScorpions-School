package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 1/17/2018.
 * All Rights Reserved by MLPJ©
 */

public class HwItem implements Serializable{
    private int hwId = -1;
    private int classId;        //need?
    private String hwUploadedDate;
    private String hwDeadline;
    private String hwHeading;
    private String hwFileName;
    private String hwUrl;
    private String hwVisibilityStartDate;
    private String hwVisibilityEndDate;

    public HwItem(int hwId, int classId, String hwUploadedDate, String hwDeadline, String hwHeading, String hwFileName, String hwUrl, String hwVisibilityStartDate, String hwVisibilityEndDate) {
        this.hwId = hwId;
        this.classId = classId;
        this.hwUploadedDate = hwUploadedDate;
        this.hwDeadline = hwDeadline;
        this.hwHeading = hwHeading;
        this.hwFileName = hwFileName;
        this.hwUrl = hwUrl;
        this.hwVisibilityStartDate = hwVisibilityStartDate;
        this.hwVisibilityEndDate = hwVisibilityEndDate;
    }

    public HwItem(String hwDeadline, String hwHeading, String hwFileName, String hwVisibilityStartDate, String hwVisibilityEndDate) {
        this.hwDeadline = hwDeadline;
        this.hwHeading = hwHeading;
        this.hwFileName = hwFileName;
        this.hwVisibilityStartDate = hwVisibilityStartDate;
        this.hwVisibilityEndDate = hwVisibilityEndDate;
    }

    public int getHwId() {
        return hwId;
    }

    public int getClassId() {
        return classId;
    }

    public String getHwUploadedDate() {
        return hwUploadedDate;
    }

    public String getHwDeadline() {
        return hwDeadline;
    }

    public String getHwHeading() {
        return hwHeading;
    }

    public String getHwFileName() {
        return hwFileName;
    }

    public String getHwUrl() {
        return hwUrl;
    }

    public String getHwVisibilityStartDate() {
        return hwVisibilityStartDate;
    }

    public String getHwVisibilityEndDate() {
        return hwVisibilityEndDate;
    }
}
