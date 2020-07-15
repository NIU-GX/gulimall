package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author NGX
 * @email 346328952@qq.com
 * @date 2020-05-15 21:34:58
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
