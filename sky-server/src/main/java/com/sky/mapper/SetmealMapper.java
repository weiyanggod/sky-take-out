package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.openxml4j.opc.OPCPackage;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    List<SetmealVO> page(String name, Integer status, Integer categoryId);

    /**
     * 新增套餐
     *
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    Long addSetmeal(Setmeal setmeal);

    /**
     * 根据id查询套餐
     *
     * @param id
     */
    SetmealVO getSetmealById(Integer id);

    /**
     * 修改套餐
     *
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    int updateSetmeal(Setmeal setmeal);

    /**
     * 起售停售状态修改
     */
    int updateSetmealStatus(Integer status, Long id);

    /**
     * 批量删除套餐     * @param idList
     */
    int deleteSetmeal(ArrayList<Object> idList);
}
