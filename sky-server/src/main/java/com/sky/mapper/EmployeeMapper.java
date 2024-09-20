package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.entity.User;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface EmployeeMapper {

    /**
     * 插入员工
     *
     * @param employee
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> getList(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新
     *
     * @param employee
     * @return
     */
    @AutoFill(value = OperationType.UPDATE)
    int update(Employee employee);

    /**
     * 获取员工信息
     *
     * @param id
     */
    Employee getDetails(Long id);
}
