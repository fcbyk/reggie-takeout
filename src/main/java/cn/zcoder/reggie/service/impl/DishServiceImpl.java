package cn.zcoder.reggie.service.impl;

import cn.zcoder.reggie.entity.Dish;
import cn.zcoder.reggie.mapper.DishMapper;
import cn.zcoder.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}