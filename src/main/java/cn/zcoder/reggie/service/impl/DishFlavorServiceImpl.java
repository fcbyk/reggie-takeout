package cn.zcoder.reggie.service.impl;

import cn.zcoder.reggie.entity.DishFlavor;
import cn.zcoder.reggie.mapper.DishFlavorMapper;
import cn.zcoder.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
