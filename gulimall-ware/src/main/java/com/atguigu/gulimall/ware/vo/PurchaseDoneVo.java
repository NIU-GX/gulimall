package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author NGX
 * @date 2020/7/29 22:20
 */
@Data
public class PurchaseDoneVo {

    private Long id;
    private List<ItemVo> items;

}
