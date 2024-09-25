package com.sky.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Override
    public int addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.addSetmeal(setmeal);
        Long setmealId = setmeal.getId();
        for (SetmealDish setmealDish : setmealDTO.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealId);
        }
        int setmealDishRow = setmealDishMapper.addSetmealDish(setmealDTO.getSetmealDishes());
        if (setmealDishRow > 0) {
            return 1;
        } else {
            return 0;
        }
    }
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        List<SetmealVO> list = setmealMapper.page(setmealPageQueryDTO.getName(), setmealPageQueryDTO.getStatus(), setmealPageQueryDTO.getCategoryId());

        PageInfo<SetmealVO> pageInfo = PageInfo.of(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }
    /**
     * 根据id查询套餐
     *
     * @param id
     */
    @Override
    public SetmealVO getSetmealById(Integer id) {
        List<SetmealDish> setmealDishList = setmealDishMapper.getById(id);
        SetmealVO setmealVO = setmealMapper.getSetmealById(id);
        setmealVO.setSetmealDishes(setmealDishList);
        System.out.println(setmealVO);
        return setmealVO;
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    @Override
    public int updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        // 修改
        int row = setmealMapper.updateSetmeal(setmeal);
        // 删除菜品
        setmealDishMapper.delSetmealDish(setmealDTO.getId());
        // 重新添加菜品
        for (SetmealDish setmealDish : setmealDTO.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealDTO.getId());
        }
        setmealDishMapper.addSetmealDish(setmealDTO.getSetmealDishes());
        return row;
    }

    /**
     * 起售停售状态修改
     *
     * @param status
     * @param id
     */
    @Override
    public int updateSetmealStatus(Integer status, Long id) {
        return setmealMapper.updateSetmealStatus(status, id);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Override
    public int deleteSetmeal(String ids) {
        ArrayList<Object> idList = new ArrayList<>();
        for (String string : ids.split(",")) {
            idList.add(Long.parseLong(string));
        }
        return setmealMapper.deleteSetmeal(idList);
    }
}
