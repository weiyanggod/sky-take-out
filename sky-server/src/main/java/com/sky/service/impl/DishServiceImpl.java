package com.sky.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Transactional
    @Override
    public void addDish(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.addDish(dish);

        Long id = dish.getId();
        System.out.println(id);
        if (dishDTO.getFlavors() != null && !dishDTO.getFlavors().isEmpty()) {
            dishDTO.getFlavors().forEach(flavorDTO -> {
                flavorDTO.setDishId(id);
            });
            dishFlavorMapper.addDishFlavor(dishDTO.getFlavors());
        }

    }

    /**
     * 分页查询菜品
     *
     * @param dishPageQueryDTO
     */
    @Override
    public PageResult getDishList(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        List<DishVO> dishList = dishMapper.getDishList(dishPageQueryDTO);
        PageInfo<DishVO> pageInfo = new PageInfo<>(dishList);
        long total = pageInfo.getTotal();
        pageInfo.getList();
        return new PageResult(total, pageInfo.getList());
    }
    /**
     * 删除菜品
     *
     * @param ids
     */
    @Transactional
    @Override
    public void deleteDish(List<Long> ids) {

        // 查询当前菜品列表是否有起售
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == 1) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 查询是否关联了套餐
        List<Long> setmealIds = setmealDishMapper.getByIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 删除菜品数据和对应的口味数据
        dishMapper.deleteDish(ids);
        dishFlavorMapper.deleteDishFlavor(ids);
    }

    /**
     * 查询菜品详情
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getDish(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> dishFlavorList = dishFlavorMapper.getDishFlavor(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }
    /**
     * 编辑菜品
     */
    @Override
    public int updateDish(DishDTO dishDTO) {
        dishMapper.updateDish(dishDTO);
        ArrayList<Long> dishFlavorIdList = new ArrayList<>();
        dishFlavorIdList.add(dishDTO.getId());

        // 先删除当前菜品对应口味
        dishFlavorMapper.deleteDishFlavor(dishFlavorIdList);

        // 判断当前口味是否是新口味,新口味则赋值id
        for (DishFlavor flavor : dishDTO.getFlavors()) {
            if (flavor.getDishId() == null) {
                flavor.setDishId(dishDTO.getId());
            }
        }
        // 重新添加口味
        dishFlavorMapper.addDishFlavor(dishDTO.getFlavors());
        return dishMapper.updateDish(dishDTO);
    }

    /**
     * 修改起售停售状态
     *
     * @param status
     * @param id
     */
    @Override
    public int setStatus(Integer status, Long id) {
        return dishMapper.setStatus(status, id);
    }
}
