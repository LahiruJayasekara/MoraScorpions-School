package com.mlpj.www.morascorpions.NotesAndHwHandling;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 1/23/2018.
 * All Rights Reserved by MLPJ©
 */

public class NoteListSerializable implements Serializable{
    private List<NoteItem> noteList;

    public NoteListSerializable(List<NoteItem> noteList) {
        this.noteList = noteList;
    }

    public List<NoteItem> getNoteList() {
        return noteList;
    }
}
