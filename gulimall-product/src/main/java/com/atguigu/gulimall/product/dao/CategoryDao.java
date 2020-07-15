package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author NGX
 * @email 346328952@qq.com
 * @date 2020-05-11 17:18:45
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
