package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 12/17/2017.
 * All Rights Reserved by MLPJ©
 */

public class NoteItem implements Serializable{

    private int noteId = -1;
    private int classId;        //need?
    private String noteDate;
    private String noteTitle;
    private String noteDescription;
    private String noteFileName;
    private String noteUrl;
    private boolean noteVisible;

    public NoteItem(int noteId, int classId, String noteDate, String noteTitle, String noteDescription, String noteFileName, boolean noteVisible) {
        this.noteId = noteId;
        this.classId = classId;
        this.noteDate = noteDate;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteFileName = noteFileName;
        this.noteVisible = noteVisible;
    }

    public NoteItem(String noteDate, String noteTitle, String noteDescription, String noteFileName, boolean noteVisible) {
        this.noteDate = noteDate;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteFileName = noteFileName;
        this.noteVisible = noteVisible;
    }

    public int getNoteId() { return noteId; }

    public String getNoteDate() {
        return noteDate;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public String getNoteFileName() {
        return noteFileName;
    }

    public String getNoteUrl() {
        return noteUrl;
    }

    public boolean isNoteVisible() {
        return noteVisible;
    }
}
