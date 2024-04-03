package com.growthgenius.pricecompareredis.service;

import com.growthgenius.pricecompareredis.vo.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

}
