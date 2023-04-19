package cn.zcoder.reggie.service.impl;

import cn.zcoder.reggie.entity.Category;
import cn.zcoder.reggie.mapper.CategoryMapper;
import cn.zcoder.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicelmpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
