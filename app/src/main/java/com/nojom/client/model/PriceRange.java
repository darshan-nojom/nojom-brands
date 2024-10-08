package com.nojom.client.model;

import java.io.Serializable;

public class PriceRange implements Serializable {

    public Integer minPrice, maxPrice,id;
    public String text;

    public boolean isSelected;

    public PriceRange(Integer minPrice, Integer maxPrice, String text,Integer id) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.text = text;
        this.id = id;
    }
}
