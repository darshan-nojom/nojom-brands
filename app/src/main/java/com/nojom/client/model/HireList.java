package com.nojom.client.model;

import java.io.Serializable;

public class HireList implements Serializable {
    public int categoryID;
    public String categoryName;
    public String categoryColor;
    public String formattedWord;

    public HireList(int categoryID, String categoryName, String categoryColor, String formattedWord) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.formattedWord = formattedWord;
    }
}


