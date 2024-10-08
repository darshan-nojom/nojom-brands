package com.nojom.client.model;

import java.io.Serializable;

public class PriceRangeSel implements Serializable {

    public Integer price_range_start, price_range_end;

    public PriceRangeSel(Integer minPrice, Integer maxPrice) {
        this.price_range_start = minPrice;
        this.price_range_end = maxPrice;
    }
}
