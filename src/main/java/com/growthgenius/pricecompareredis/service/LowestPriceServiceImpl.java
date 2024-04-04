package com.growthgenius.pricecompareredis.service;

import com.growthgenius.pricecompareredis.vo.Product;
import com.growthgenius.pricecompareredis.vo.ProductGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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

}
