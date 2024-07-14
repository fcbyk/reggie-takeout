package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.common.CustomException;
import com.fcbyk.reggietakeout.dto.SetmealDto;
import com.fcbyk.reggietakeout.entity.SetmealDish;
import com.fcbyk.reggietakeout.mapper.SetmealMapper;
import com.fcbyk.reggietakeout.entity.Setmeal;
import com.fcbyk.reggietakeout.service.SetmealService;
import com.fcbyk.reggietakeout.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SetmealDto getByIdWithDishes(Long id) {
        // 获取套餐基本信息　不含菜品信息
        Setmeal setmeal = this.getById(id);
        // 创建setmealDto对象，用于封装套餐基本信息和套餐所含菜品
        SetmealDto setmealDto = new SetmealDto();
        // 将setmeal的基本信息封装到setmealDto对象中
        BeanUtils.copyProperties(setmeal, setmealDto);
        // 获取套餐所含菜品
        LambdaQueryWrapper<SetmealDish> setmealDishQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishQueryWrapper.eq(SetmealDish::getSetmealId, id);  // where setmeal_id = ?
        List<SetmealDish> dishes = setmealDishService.list(setmealDishQueryWrapper);  // list()方法为MyBatis-Plus提供的查询方法，根据条件查询数据
        // 将套餐所含菜品封装到setmealDto对象中
        setmealDto.setSetmealDishes(dishes);

        // 返回setmealDto对象
        return setmealDto;
    }

    @Override
    @Transactional          // 对于多表操作，需要开启事务管理
    public void saveWithDishes(SetmealDto setmealDto) {
        // 保存套餐基本信息
        this.save(setmealDto);
        // 获取setmeal的List<SetmealDish>信息，！需要注意的是，该list中的setmealId为null，前端未传入，所以需要手动遍历，通过SetmealDto继承的Setmeal获取id
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().peek(item -> item.setSetmealId(setmealDto.getId())).collect(Collectors.toList()); // peek()方法为中间操作，不会改变原有的list，collect()方法为终止操作，会改变原有的list
        // 保存套餐和菜品的关联关系
        setmealDishService.saveBatch(setmealDishes);

        // 清理redis缓存
        Set<Object> keys = redisTemplate.keys("setmeal_*");    // 获取所有以dish_开头的key
        assert keys != null;
        redisTemplate.delete(keys);
    }

    @Override
    public void changeStatus(List<Long> ids, Integer status) {
        for (Long id : ids) {
            LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();   // 条件构造器
            updateWrapper.eq(Setmeal::getId, id);   // where id = ?
            updateWrapper.set(Setmeal::getStatus, status);   // set status = ?
            this.update(updateWrapper);  // update()方法为MyBatis-Plus提供的更新方法，根据条件更新数据
        }

        // 清理redis缓存
        Set<Object> keys = redisTemplate.keys("setmeal_*");    // 获取所有以dish_开头的key
        assert keys != null;
        redisTemplate.delete(keys);

    }

    @Override
    @Transactional
    public void removeWithDishes(List<Long> ids) {
        // 查询套餐是否在售卖 select count(1) from setmeal where id in (1, 2, 3) and status = 1
        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        int count = this.count(setmealQueryWrapper);   //　count()方法为MyBatis-Plus提供的查询方法，返回查询结果的数量
        // 如果不能删除，抛出异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖，不能删除");  // 自定义异常
        }
        // 如果能删除，先删除套餐表中的数据  setmeal的id为主键，所以可以直接删除
        this.removeByIds(ids);                  // removeByIds()方法为MyBatis-Plus提供的删除方法，根据id集合删除数据
        // 删除关系表中的数据  setmeal_dish的setmeal_id不是主键，所以需要先查询出setmeal_id对应的id集合，再根据id集合删除数据
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);   // where setmeal_id in (1, 2, 3)
        setmealDishService.remove(setmealDishLambdaQueryWrapper);   // remove()方法为MyBatis-Plus提供的删除方法，根据条件删除数据


        // 清理redis缓存
        Set<Object> keys = redisTemplate.keys("setmeal_*");    // 获取所有以dish_开头的key
        assert keys != null;
        redisTemplate.delete(keys);

    }

    @Transactional
    @Override
    public void updateWithDishes(SetmealDto setmealDto) {
        // 更新套餐基本信息
        this.updateById(setmealDto);
        // 获取套餐的id
        Long setmealId = setmealDto.getId();
        // 修改套餐和菜品的关联关系
        //　1.删除原有关联关系
        setmealDishService.remove(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, setmealId));   // remove()方法为MyBatis-Plus提供的删除方法，根据条件删除数据
        // 2.添加新的关联关系 即将setmeal的id字段赋值给setmealDish的setmealId字段
        // 获取新的关联关系
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes(); // 该setmealDishes中的setmealId字段为null
        // 使用stream流将setmealDishes中的setmealId字段赋值为setmealDtoId
        setmealDishes = setmealDishes.stream().peek(item -> {
            item.setSetmealId(setmealId);   // 将套餐id赋值给setmealDish的setmealId字段
        }).collect(Collectors.toList());    // 将stream流转换为list集合

        // 保存新的关联关系
        setmealDishService.saveBatch(setmealDishes);  // saveBatch()方法为MyBatis-Plus提供的批量保存方法，保存的是一个集合


        // 清理redis缓存
        Set<Object> keys = redisTemplate.keys("setmeal_*");    // 获取所有以dish_开头的key
        assert keys != null;
        redisTemplate.delete(keys);

    }

}
