package com.fcbyk.reggietakeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fcbyk.reggietakeout.entity.Category;


public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
