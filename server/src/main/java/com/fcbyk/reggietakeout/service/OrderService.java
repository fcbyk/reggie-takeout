package com.fcbyk.reggietakeout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fcbyk.reggietakeout.dto.OrdersDto;
import com.fcbyk.reggietakeout.entity.Orders;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public interface OrderService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);

    Page<OrdersDto> getUserPage(int page, int pageSize);

    public Page<OrdersDto> getPage(int page,
                                   int pageSize,
                                   String number,
                                   @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
                                   @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime);
}
