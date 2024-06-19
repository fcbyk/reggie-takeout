package com.fcbyk.reggietakeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fcbyk.reggietakeout.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}