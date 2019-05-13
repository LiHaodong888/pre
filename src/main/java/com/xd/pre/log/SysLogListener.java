package com.xd.pre.log;

import com.xd.pre.domain.SysLog;
import com.xd.pre.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Classname SysLogListener
 * @Description 注解形式的监听 异步监听日志事件
 * @Author 李号东 lihaodongmail@163.com
 * @Date 2019-04-28 11:34
 * @Version 1.0
 */
@Slf4j
@Component
public class SysLogListener {

    @Autowired
    private ISysLogService sysLogService;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        // 保存日志
        sysLogService.save(sysLog);
    }
}
