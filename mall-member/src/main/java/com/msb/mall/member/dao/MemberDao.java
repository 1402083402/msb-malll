package com.msb.mall.member.dao;

import com.msb.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author wj
 * @email 1402083402@qq.com.com
 * @date 2022-06-23 21:19:45
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
