package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author NGX
 * @email 346328952@qq.com
 * @date 2020-05-15 20:39:02
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
