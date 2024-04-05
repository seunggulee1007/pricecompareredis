package com.growthgenius.pricecompareredis.service;

import com.growthgenius.pricecompareredis.vo.Product;
import com.growthgenius.pricecompareredis.vo.ProductGroup;
import com.growthgenius.pricecompareredis.vo.SearchKeyword;

import java.util.Set;

public interface LowestPriceService {

    Set getZsetValue(String key);

    int setProduct(Product product);

    int setProductGroup(ProductGroup productGroup);

    int setNewProductGroupToKeyword(String keyword, String productGroupId, double score);

    SearchKeyword getLowestPriceProductByKeyword(String keyword);

}
