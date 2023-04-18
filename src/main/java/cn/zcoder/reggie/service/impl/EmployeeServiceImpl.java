package cn.zcoder.reggie.service.impl;

import cn.zcoder.reggie.entity.Employee;
import cn.zcoder.reggie.mapper.EmployeeMapper;
import cn.zcoder.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}