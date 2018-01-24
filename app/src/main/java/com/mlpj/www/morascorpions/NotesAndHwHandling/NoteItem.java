package com.mlpj.www.morascorpions.NotesAndHwHandling;

import java.io.Serializable;

/**
 * Created by DELL on 12/17/2017.
 * All Rights Reserved by MLPJ©
 */

public class NoteItem implements Serializable{

    private int noteId;
    private String noteTitle;
    private String noteUrl;
    private String description;
    private boolean visibility;
    private String uploadedDate;
    private String lastUpdatedDate;

    public NoteItem(int noteId, String noteTitle, String noteUrl, String description, boolean visibility, String uploadedDate, String lastUpdatedDate) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteUrl = noteUrl;
        this.description = description;
        this.visibility = visibility;
        this.uploadedDate = uploadedDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public NoteItem(int noteId, String noteTitle, String noteUrl, String description, String uploadedDate, String lastUpdatedDate) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteUrl = noteUrl;
        this.description = description;
        this.uploadedDate = uploadedDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteUrl() {
        return noteUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public String getUploadedDate() {
        String[] parts = uploadedDate.split("T");
        return parts[0];
    }

    public String getLastUpdatedDate() {
        String[] parts = lastUpdatedDate.split("T");
        return parts[0];
    }
}
