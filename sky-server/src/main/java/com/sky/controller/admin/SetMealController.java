package com.sky.controller.admin;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sky.result.Result;
// 套餐模块
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "分类相关接口")
@Slf4j
public class SetMealController {

    @Autowired
    SetmealService setMealService;

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {

        System.out.println(setmealPageQueryDTO);

        PageResult pageResult = setMealService.page(setmealPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result<String> addSetmeal(@RequestBody SetmealDTO setmealDTO) {

        int row = setMealService.addSetmeal(setmealDTO);
        if (row == 1) {
            return Result.success("新增成功");
        } else {
            return Result.error("新增失败");
        }
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getSetmealById(@PathVariable Integer id, String name) {
        SetmealVO setmealVO = setMealService.getSetmealById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        int row = setMealService.updateSetmeal(setmealDTO);
        if (row != 0) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }
    }

    /**
     * 起售停售状态修改
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("起售停售状态修改")
    public Result<String> updateSetmealStatus(@PathVariable Integer status, Long id) {
        System.out.println(status);
        System.out.println(id);
        int i = setMealService.updateSetmealStatus(status, id);
        System.out.println(i);
        if (i == 1) {
            return Result.success("修改成功");
        } else {
            return Result.error("修改失败");
        }

    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result<String> deleteSetmeal(String ids) {
        System.out.println(ids);

        int rows = setMealService.deleteSetmeal(ids);

        if (rows >= 1) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }

    }

}