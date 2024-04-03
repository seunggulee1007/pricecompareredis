package com.growthgenius.pricecompareredis.controller;

import com.growthgenius.pricecompareredis.service.LowestPriceService;
import com.growthgenius.pricecompareredis.vo.Product;
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

}
