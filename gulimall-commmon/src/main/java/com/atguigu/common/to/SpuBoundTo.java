package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author NGX
 * @Date 2020/7/26 13:29
 */
@Data
public class SpuBoundTo {
    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;
}
