package com.growthgenius.pricecompareredis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growthgenius.pricecompareredis.vo.Product;
import com.growthgenius.pricecompareredis.vo.ProductGroup;
import com.growthgenius.pricecompareredis.vo.SearchKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LowestPriceServiceImpl implements LowestPriceService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Set getZsetValue(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
    }

    @Override
    public int setProduct(Product product) {
        redisTemplate.opsForZSet().add(product.getProductGroupId(), product.getProductId(), product.getPrice());
        return Objects.requireNonNull(redisTemplate.opsForZSet().rank(product.getProductGroupId(), product.getProductId())).intValue();
    }

    @Override
    public int setProductGroup(ProductGroup productGroup) {
        List<Product> productList = productGroup.getProductList();
        for (Product product : productList) {
            product.setProductGroupId(productGroup.getProductGroupId());
            this.setProduct(product);
        }
        return Objects.requireNonNull(redisTemplate.opsForZSet().zCard(productGroup.getProductGroupId())).intValue();
    }

    public int setNewProductGroupToKeyword(String keyword, String productGroupId, double score) {
        redisTemplate.opsForZSet().add(keyword, productGroupId, score);
        return Objects.requireNonNull(redisTemplate.opsForZSet().rank(keyword, productGroupId)).intValue();
    }

    @Override
    public SearchKeyword getLowestPriceProductByKeyword(String keyword) {
        SearchKeyword searchKeyword = new SearchKeyword();
        // keyword 를 통해 product group 가져오기 10개
        List<ProductGroup> productGroupUsingKeyword = getProductGroupUsingKeyword(keyword);
        searchKeyword.setKeyword(keyword);
        searchKeyword.setProductGroupList(productGroupUsingKeyword);
        // 가져온 정보를 리턴 할 object 에 넣기
        return searchKeyword;
    }

    public List<ProductGroup> getProductGroupUsingKeyword(String keyword) {
        List<ProductGroup> returnProductGroupList = new ArrayList<>();
        // keyword 러 productGroupId 를 조회
        List<String> productGroupIdList = redisTemplate.opsForZSet().reverseRange(keyword, 0, 9).stream().map(Object::toString).toList();
        List<Product> tempProductList = new ArrayList<>();
        // 10 개 productGroupId 로 loop
        for (final String productGroupId : productGroupIdList) {
            Set<ZSetOperations.TypedTuple<Object>> productAndPriceList = redisTemplate.opsForZSet().rangeWithScores(productGroupId, 0, 9);
            assert productAndPriceList != null;
            ProductGroup productGroup = new ProductGroup();
            for (ZSetOperations.TypedTuple<Object> objectTypedTuple : productAndPriceList) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> productPriceMap = objectMapper.convertValue(objectTypedTuple, Map.class);
                Product tempProduct = new Product();
                tempProduct.setProductId(productPriceMap.get("value"));
                tempProduct.setPrice(Double.valueOf(productPriceMap.get("score")).intValue());
                tempProductList.add(tempProduct);
            }
            productGroup.setProductGroupId(productGroupId);
            productGroup.setProductList(tempProductList);
            returnProductGroupList.add(productGroup);
        }
        return returnProductGroupList;
    }

}
