package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author NGX
 * @Date 2020/7/19 13:46
 */
@Data
public class AttrRespVo extends AttrVo{
    private String catelogName;
    private String groupName;

    private Long[] catelogPath;
}
