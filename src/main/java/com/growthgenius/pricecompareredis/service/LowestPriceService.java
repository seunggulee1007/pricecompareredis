package com.growthgenius.pricecompareredis.service;

import com.growthgenius.pricecompareredis.vo.Product;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setProduct(Product product);

}
