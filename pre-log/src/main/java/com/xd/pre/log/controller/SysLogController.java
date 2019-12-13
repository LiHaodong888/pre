package com.xd.pre.log.controller;


import com.xd.pre.log.annotation.SysOperaLog;
import com.xd.pre.log.service.ISysLogService;
import com.xd.pre.common.utils.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-27
 */
@RestController
@RequestMapping("/log")
public class SysLogController {

    @Resource
    private ISysLogService logService;

    /**
     *
     * 分页查询日志列表
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:log:view')")
    public R selectLog(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") Integer type, @RequestParam String userName){
        return R.ok(logService.selectLogList(page, pageSize,type,userName));
    }


    @SysOperaLog(descrption = "删除日志")
    @DeleteMapping("/{logId}")
    @PreAuthorize("hasAuthority('sys:log:delete')")
    public R delete(@PathVariable("logId") Integer logId) {
        return R.ok(logService.removeById(logId));
    }
}

