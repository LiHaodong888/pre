package com.xd.pre.limit;


import java.lang.annotation.*;

/**
 * @Classname DataScopeInterceptor
 * @Description 限流注解
 * @Author Created by LiHaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-20 10:29
 * @Version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 限流唯一标示
     *
     * @return
     */
    String key() default "";

    /**
     * 限流时间
     *
     * @return
     */
    int time();

    /**
     * 限流次数
     *
     * @return
     */
    int count();
}
