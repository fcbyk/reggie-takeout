package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.common.BaseContext;
import com.fcbyk.reggietakeout.common.CustomException;
import com.fcbyk.reggietakeout.dto.OrdersDto;
import com.fcbyk.reggietakeout.entity.*;
import com.fcbyk.reggietakeout.mapper.OrderMapper;
import com.fcbyk.reggietakeout.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(wrapper);
    }

    public Page<OrdersDto> getUserPage(int page, int pageSize) {
        // 构造分页对象
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        Page<OrdersDto> dtoPage = new Page<>(); // 用于存放转换后的数据
        // 构造查询条件
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
        queryWrapper.orderByDesc(Orders::getOrderTime);
        // 查询基本订单数据
        this.page(ordersPage, queryWrapper);
        // 拷贝对象，将ordersPage中的数据拷贝到dtoPage中，除去records属性,records属性需要单独处理
        BeanUtils.copyProperties(ordersPage, dtoPage, "records");
        // records是Page对象的一个属性，用于存放分页查询的结果，因为orderPage的records很多数据比dtoPage的少，需要单独处理
        List<Orders> ordersPageRecords = ordersPage.getRecords();
        // 将records中的每个Orders对象转换为OrdersDto对象
        List<OrdersDto> dtoPageRecords = ordersPageRecords.stream().map(item -> {
            // 1.先创建OrdersDto对象
            OrdersDto ordersDto = new OrdersDto();
            // 2.将Orders对象中的基本数据拷贝到OrdersDto对象中
            BeanUtils.copyProperties(item, ordersDto);
            // 3.查询订单商品数据
            LambdaQueryWrapper<OrderDetail> orderDetailQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailQueryWrapper.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(orderDetailQueryWrapper);
            // 4.将订单商品数据设置到OrdersDto对象中
            ordersDto.setOrderDetails(orderDetails);
            // 5.将其它数据设置到OrdersDto对象中
            LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(User::getId, item.getUserId());
            User user = userService.getOne(userQueryWrapper);
            if (user != null) {
                ordersDto.setUserName(user.getName());
                ordersDto.setPhone(user.getPhone());
            }
            ordersDto.setAmount(item.getAmount());
            ordersDto.setConsignee(item.getConsignee());
            ordersDto.setAddress(item.getAddress());
            ordersDto.setUserName(item.getConsignee());

            return ordersDto;

        }).collect(Collectors.toList());

        // 将转换后的数据设置到dtoPage中
        dtoPage.setRecords(dtoPageRecords);

        return dtoPage;
    }

    public Page<OrdersDto> getPage(int page,
                                   int pageSize,
                                   String number,
                                   @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date beginTime,
                                   @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") Date endTime) {
        // 构造分页对象
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        Page<OrdersDto> dtoPage = new Page<>(); // 用于存放转换后的数据
        // 构造查询条件
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);
        queryWrapper.like(StringUtils.isNotEmpty(number), Orders::getNumber, number);
        queryWrapper.between((beginTime != null && endTime != null), Orders::getOrderTime, beginTime, endTime);
        // 查询基本订单数据
        this.page(ordersPage, queryWrapper);
        // 拷贝对象，将ordersPage中的数据拷贝到dtoPage中，除去records属性,records属性需要单独处理
        BeanUtils.copyProperties(ordersPage, dtoPage, "records");
        // records是Page对象的一个属性，用于存放分页查询的结果，因为orderPage的records很多数据比dtoPage的少，需要单独处理
        List<Orders> ordersPageRecords = ordersPage.getRecords();
        // 将records中的每个Orders对象转换为OrdersDto对象
        List<OrdersDto> dtoPageRecords = ordersPageRecords.stream().map(item -> {
            // 1.先创建OrdersDto对象
            OrdersDto ordersDto = new OrdersDto();
            // 2.将Orders对象中的基本数据拷贝到OrdersDto对象中
            BeanUtils.copyProperties(item, ordersDto);
            // 3.查询订单商品数据
            LambdaQueryWrapper<OrderDetail> orderDetailQueryWrapper = new LambdaQueryWrapper<>();
            orderDetailQueryWrapper.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(orderDetailQueryWrapper);
            // 4.将订单商品数据设置到OrdersDto对象中
            ordersDto.setOrderDetails(orderDetails);
            // 5.将其它数据设置到OrdersDto对象中
            LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
            userQueryWrapper.eq(User::getId, item.getUserId());
            User user = userService.getOne(userQueryWrapper);
            if (user != null) {
                ordersDto.setUserName(user.getName());
                ordersDto.setPhone(user.getPhone());
            }
            ordersDto.setAmount(item.getAmount());
            ordersDto.setConsignee(item.getConsignee());
            ordersDto.setAddress(item.getAddress());
            ordersDto.setUserName(item.getConsignee());

            return ordersDto;
        }).collect(Collectors.toList());

        // 将转换后的数据设置到dtoPage中
        dtoPage.setRecords(dtoPageRecords);

        return dtoPage;
    }
}