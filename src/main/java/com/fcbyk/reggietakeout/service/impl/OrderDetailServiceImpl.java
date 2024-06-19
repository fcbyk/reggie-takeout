package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.entity.OrderDetail;
import com.fcbyk.reggietakeout.mapper.OrderDetailMapper;
import com.fcbyk.reggietakeout.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}