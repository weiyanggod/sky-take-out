package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     */
    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dishDTO);

    /**
     * 分页查询菜品
     */
    List<DishVO> getDishList(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     *
     * @param ids
     */
    void deleteDish(List<Long> ids);

    /**
     * 查询当前菜品
     *
     * @param id
     */
    Dish getById(Long id);

    /**
     * 编辑菜品
     *
     * @param dishDTO
     */
    int updateDish(DishDTO dishDTO);

    /**
     * 修改起售停售状态
     *
     * @param status
     * @param id
     */
    int setStatus(Integer status, Long id);

    List<Dish> getDishByCategoryId(Long categoryId);
}
