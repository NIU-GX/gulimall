package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author NGX
 * @email 346328952@qq.com
 * @date 2020-05-15 21:34:58
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
