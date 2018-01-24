package com.mlpj.www.morascorpions.NotesAndHwHandling;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 1/18/2018.
 * All Rights Reserved by MLPJ©
 */

public class HwListSerializable implements Serializable {
    private List<HwItem> hwList;

    public HwListSerializable(List<HwItem> hwList) {
        this.hwList = hwList;
    }

    public List<HwItem> getHwList() {
        return hwList;
    }
}
