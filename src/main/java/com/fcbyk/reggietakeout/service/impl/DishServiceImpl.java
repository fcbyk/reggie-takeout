package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.entity.Dish;
import com.fcbyk.reggietakeout.mapper.DishMapper;
import com.fcbyk.reggietakeout.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
