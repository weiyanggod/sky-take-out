package com.sky.service;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;
public interface DishService {

    /**
     * 新增菜品
     */
    void addDish(DishDTO dishDTO);

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     */
    PageResult getDishList(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     *
     * @param ids
     */
    void deleteDish(List<Long> ids);

    /**
     * 菜品详情
     *
     * @param id
     */
    DishVO getDish(Long id);

    /**
     * 编辑菜品
     */
    int updateDish(DishDTO dishDTO);

    /**
     * 修改起售停售状态
     *
     * @param status
     * @param id
     */
    int setStatus(Integer status, Long id);
}
