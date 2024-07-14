package com.fcbyk.reggietakeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fcbyk.reggietakeout.entity.Setmeal;
import com.fcbyk.reggietakeout.dto.SetmealDto;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    void saveWithDishes(SetmealDto setmealDto);

    void removeWithDishes(List<Long> ids);

    void changeStatus(List<Long> ids, Integer status);

    SetmealDto getByIdWithDishes(Long id);

    void updateWithDishes(SetmealDto setmealDto);
}
