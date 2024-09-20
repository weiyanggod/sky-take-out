package com.sky.mapper;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface DishFlavorMapper {
    /**
     * 新增口味
     *
     * @param dishFlavors
     */
    void addDishFlavor(List<DishFlavor> dishFlavors);

    /**
     * 根据菜品id查询对应的口味
     *
     * @param
     */
    List<DishFlavor> getDishFlavor(Long id);

    /**
     * 根据菜品删除口味
     *
     * @param ids
     */
    void deleteDishFlavor(List<Long> ids);
}
