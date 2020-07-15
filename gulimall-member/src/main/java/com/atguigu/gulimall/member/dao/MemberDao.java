package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author NGX
 * @email 346328952@qq.com
 * @date 2020-05-15 20:52:38
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
