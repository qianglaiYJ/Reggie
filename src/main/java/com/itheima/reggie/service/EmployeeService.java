package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Employee;

/*
* IService是Mybatis Plus提供的服务接口。
* 它提供了一些高级的CRUD操作方法，如批量插入、批量删除和分页查询等。
* IService接口是一个抽象的接口，可以根据实际需要进行实现。
* 通常与BaseMapper接口结合使用，提供更加灵活和高效的数据操作。


 *
* */

public interface EmployeeService extends IService<Employee> {
}
