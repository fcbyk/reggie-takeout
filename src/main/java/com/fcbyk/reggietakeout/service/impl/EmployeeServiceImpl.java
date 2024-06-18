package com.fcbyk.reggietakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fcbyk.reggietakeout.entity.Employee;
import com.fcbyk.reggietakeout.mapper.EmployeeMapper;
import com.fcbyk.reggietakeout.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService{
}
