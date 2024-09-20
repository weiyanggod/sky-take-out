package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 设置状态
     *
     * @param id
     */
    int setStatus(Integer status, Long id);

    /**
     * 获取员工信息
     *
     * @param id
     */
    Employee getDetails(Long id);

    /**
     * 编辑员工
     *
     * @param employeeDTO
     */
    int edit(EmployeeDTO employeeDTO);
}
