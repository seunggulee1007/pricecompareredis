package com.growthgenius.pricecompareredis.vo;

import lombok.Data;

import java.util.List;

@Data
public class Keyword {

    private String keyword; // 유아용품 - 하기스귀저기(FPG0001), A사 딸랑이 (FPG0002)

    private List<ProductGroup> productGroupList;
}
