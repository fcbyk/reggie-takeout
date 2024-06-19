package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.entity.ShoppingCart;
import com.fcbyk.reggietakeout.mapper.ShoppingCartMapper;
import com.fcbyk.reggietakeout.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
