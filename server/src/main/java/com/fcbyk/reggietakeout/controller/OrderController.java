package com.fcbyk.reggietakeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fcbyk.reggietakeout.common.R;
import com.fcbyk.reggietakeout.dto.OrdersDto;
import com.fcbyk.reggietakeout.entity.Orders;
import com.fcbyk.reggietakeout.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public R<Page<OrdersDto>> page(
            int page,
            int pageSize,
            String number,
            @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
            @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime) {
        Page<OrdersDto> pageInfo = orderService.getPage(page, pageSize, number, beginTime, endTime);

        return R.success(pageInfo);
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page<OrdersDto>> userPage(int page, int pageSize) {
        // 获取用户订单分页
        Page<OrdersDto> userPage = orderService.getUserPage(page, pageSize);

        return R.success(userPage);
    }
}
