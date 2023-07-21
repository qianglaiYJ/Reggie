package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
//    request 对象作用，登录成功之后，需要把员工对象的id，存入到session一份，获取登录用户随时获取出来

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        /*
        * 1.将页面提交的密码password进行md5加密处理
        * 2.根据页面提交的用户名username查询数库
        * 3.如果没有查询到则返回登录失败
        * 4.密码对比，如果不一致则返回登录失败结果
        * 5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        * 6.登录成功，将员工id存入Session并返回登录成功结果
        *
        * */
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        /*
        *
        *
        * */
        employeeLambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(employeeLambdaQueryWrapper);
//        3.如果没有查询到返回登录失败结果
        if (emp == null){
            return  R.error("登录失败");
        }
        if (!emp.getPassword().equals(password)){
            return  R.error("登录失败");

        }
//        5.查询员工状态，如果为已经禁用状态，则返回员工已禁用结果
        if (emp.getStatus() ==0){
            return  R.error("账号已禁用");


        }
//        6.登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());

        return  R.success(emp);

    }
    /*
    * 员工退出
    * */

    @PostMapping("/logout")
    public  R<String> logout(HttpServletRequest request){
//        清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return  R.success("退出成功");

    }
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
//    设置初始密码123456，需要进行md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户的id
        Long empId = (Long)request.getSession().getAttribute("employee");


//        表中字段是有标识 谁创建的员工，谁修改的用户
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        log.info("新增员工，员工信息：{}",employee.toString());

        employeeService.save(employee);


        return R.success("新增员工成功");

    }

    /**
     * 需要 手写/** 生成
     * @param page  当前页
     * @param pageSize 当前页面数
     * @param name name 为查询条件
     * @return
     */

    @GetMapping("/page")
    public  R<Page> page(int page,int pageSize,String name){
        log.info("page{},pageSize{},name{}",page,pageSize,name);

//        构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);


//        执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);

    }
//

    /**
     * 修改员工状态
     * @param employee
     * @return
     */
    @PutMapping
    public  R<String> update(HttpServletRequest httpServletRequest,@RequestBody Employee employee){

        log.info("员工id{},员工状态{}",employee.getId(),employee.getStatus());

//        当前数据的修改人
        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
//        设置更新时间
        employee.setUpdateTime(LocalDateTime.now());
//        设置具体的更新人
        employee.setUpdateUser(empId);
        employeeService.updateById(employee);


        return R.success("员工信息修改成功");

    }

    /**
     *
     *
     */
      @GetMapping("/{id}")
      public  R<Employee> getById(@PathVariable Long id){
          log.info("根据员工id查询员工信息...{}",id);
          Employee employee = employeeService.getById(id);
          if (employee!=null){
              return R.success(employee);
          }else {
              return R.error("没有查到对应的员工信息");
          }


      }



}
