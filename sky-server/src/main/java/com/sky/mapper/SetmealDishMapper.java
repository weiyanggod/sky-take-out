package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    List<Long> getByIds(List<Long> ids);

    /**
     * 新增菜品
     */
    int addSetmealDish(List<SetmealDish> setmealDish);

    /**
     * 删除套餐所属菜品
     */
    void delSetmealDish(Long id);

    /**
     * 根据id查询菜品
     */
    List<SetmealDish> getById(Integer id);
}
