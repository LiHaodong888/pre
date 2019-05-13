package com.xd.pre.codegen.controller;


import com.xd.pre.codegen.domain.SysDb;
import com.xd.pre.codegen.dto.DbDto;
import com.xd.pre.codegen.service.ISysDbService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 数据库管理 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-26
 */
@RestController
@RequestMapping("/db")
public class SysDbController {

    @Autowired
    private ISysDbService dbService;

    /**
     * 保存数据库信息
     * @param dbDto
     * @return
     */
    @PostMapping
    public R save(@RequestBody DbDto dbDto){
        return R.ok(dbService.saveDb(dbDto));
    }

    /**
     * 保存数据库信息
     * @return
     */
    @GetMapping
    public R select(){
        return R.ok(dbService.selectDbList());
    }

    /**
     * 删除数据库信息
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Integer id){
        return R.ok(dbService.removeById(id));
    }

    /**
     * 更新数据库信息
     * @return
     */
    @PutMapping
    public R update(@RequestBody SysDb sysDb){
        return R.ok(dbService.updateById(sysDb));
    }


}

