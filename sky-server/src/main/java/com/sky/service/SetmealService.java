package com.sky.service;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
public interface SetmealService {
    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    int addSetmeal(SetmealDTO setmealDTO);
    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据id查询套餐
     *
     * @param id
     */
    SetmealVO getSetmealById(Integer id);

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    int updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 起售停售状态修改
     *
     * @param status
     * @param id
     */
    int updateSetmealStatus(Integer status, Long id);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    int deleteSetmeal(String ids);
}
