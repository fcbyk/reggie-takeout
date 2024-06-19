package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.mapper.SetmealMapper;
import com.fcbyk.reggietakeout.entity.Setmeal;
import com.fcbyk.reggietakeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
}
