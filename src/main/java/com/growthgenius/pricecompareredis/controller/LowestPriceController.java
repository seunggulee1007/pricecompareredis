package com.growthgenius.pricecompareredis.controller;

import com.growthgenius.pricecompareredis.service.LowestPriceService;
import com.growthgenius.pricecompareredis.vo.Product;
import com.growthgenius.pricecompareredis.vo.ProductGroup;
import com.growthgenius.pricecompareredis.vo.SearchKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LowestPriceController {

    private final LowestPriceService lowestPriceService;

    @GetMapping("/product")
    public Set getZsetValue(String key) {
        return lowestPriceService.getZsetValue(key);
    }

    @PutMapping("/product")
    public int setProduct(@RequestBody Product product) {
        return lowestPriceService.setProduct(product);
    }

    @PutMapping("/product/group")
    public int setProductGroup(@RequestBody ProductGroup productGroup) {
        return lowestPriceService.setProductGroup(productGroup);
    }

    @PutMapping("/product/group/keyword")
    public int setNewProductGroupToKeyword(String keyword, String productGroupId, double score) {
        return lowestPriceService.setNewProductGroupToKeyword(keyword, productGroupId, score);
    }

    @GetMapping("/product/price/lowest")
    public SearchKeyword getLowestPriceProductByKeyword(String keyword) {
        return lowestPriceService.getLowestPriceProductByKeyword(keyword);
    }

}
