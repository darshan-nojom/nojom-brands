package com.nojom.client.model;

import java.io.Serializable;

public class SortByFilterModel implements Serializable {
    public String filterName, filterID;
    public boolean isSelected;

    public SortByFilterModel(String filterName, String filterID) {
        this.filterName = filterName;
        this.filterID = filterID;
    }
}
