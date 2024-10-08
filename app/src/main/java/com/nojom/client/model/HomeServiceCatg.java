package com.nojom.client.model;

public class HomeServiceCatg {
    public String categoryColor = "";
    public String categoryNameApp = "";
    public String categoryAppMenuName = "";
    public String isNew = "0";
    public int id = 0;

    public HomeServiceCatg(String categoryColor, String categoryNameApp, String categoryAppMenuName, String isNew, int id) {
        this.categoryColor = categoryColor;
        this.categoryNameApp = categoryNameApp;
        this.categoryAppMenuName = categoryAppMenuName;
        this.isNew = isNew;
        this.id = id;
    }
}
