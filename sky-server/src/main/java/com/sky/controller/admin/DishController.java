package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品管理相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> addDish(@RequestBody DishDTO dishDTO) {
        log.info("菜品数据:{}", dishDTO);

        dishService.addDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> getDishList(DishPageQueryDTO dishPageQueryDTO) {
        log.info("查询参数:{}", dishPageQueryDTO);
        PageResult dishList = dishService.getDishList(dishPageQueryDTO);
        return Result.success(dishList);
    }

    /**
     * 删除菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result<String> deleteDish(@RequestParam List<Long> ids) {
        dishService.deleteDish(ids);
        return Result.success("删除成功");

    }

    /**
     * 查询菜品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("查询菜品详情")
    public Result<DishVO> getDish(@PathVariable Long id) {
        System.out.println("菜品id" + id);

        DishVO dish = dishService.getDish(id);

        return Result.success(dish);
    }

    @GetMapping("/list")
    public Result<List<Dish>> getDishByCategoryId(Long categoryId) {

        List<Dish> dishList = dishService.getDishByCategoryId(categoryId);
        return Result.success(dishList);
    }

    @PutMapping
    @ApiOperation("编辑菜品")
    public Result<String> updateDish(@RequestBody DishDTO dishDTO) {
        int row = dishService.updateDish(dishDTO);
        if (row != 0) {
            return Result.success("编辑成功");
        } else {
            return Result.error("编辑失败");
        }
    }

    @PostMapping("/status/{status}")
    @ApiOperation("起售停售状态修改")
    public Result<String> setDishStatus(@PathVariable Integer status, @RequestParam Long id) {

        int row = dishService.setStatus(status, id);

        if (row != 0) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }
}
