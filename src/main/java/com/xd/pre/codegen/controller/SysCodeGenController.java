package com.xd.pre.codegen.controller;

import com.xd.pre.codegen.domain.SysDb;
import com.xd.pre.codegen.dto.DbDto;
import com.xd.pre.codegen.service.SysCodeGenService;
import com.xd.pre.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname SysCodeGenController
 * @Description 代码生成器控制层
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-25 17:11
 * @Version 1.0
 */
@RestController
@RequestMapping("/code")
public class SysCodeGenController {

    @Autowired
    private SysCodeGenService codeGenService;


    @PostMapping("/testConnection")
    public R testConnection(@RequestBody DbDto dbDto) {
        boolean success = codeGenService.testConnection(dbDto);
        if(success) {
            return R.ok(codeGenService.testConnection(dbDto));
        }
        return R.error("连接失败,请检查数据库及连接");
    }


    @GetMapping("/table")
    public R selectTable(@RequestParam("table") String table){
        return R.ok(codeGenService.selectTable(table));
    }


    @GetMapping("/column")
    public R selectColumn(@RequestParam("tableName") String tableName){
        return R.ok(codeGenService.selectColumn(tableName));
    }
}
