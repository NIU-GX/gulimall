package com.atguigu.gulimall.product.feign;

import com.atguigu.common.to.SkuReductionTo;
import com.atguigu.common.to.SpuBoundTo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author NGX
 * @Date 2020/7/26 13:25
 *
 * CouponFeignService.saveSpuBounds(spuBoundTo)
 *      1. SpringCloud会使用@RequestBody注解将 spuBoundTo 转化为json数据
 *      2. 找到gulimall-coupon服务，给coupon/spubounds/save 发送请求。
 *          将json数据放在请求提里
 *      3. 对方服务收到请求，请求体里json数据，将json再转化为对象，只要json数据兼容，双方服务无需使用同一个to
 */

@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
