package com.xd.pre.limit;

import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Classname DataScopeInterceptor
 * @Description 注解拦截
 * @Author Created by LiHaodong (alias:小东啊) lihaodongmail@163.com
 * @Date 2019-06-08 10:29
 * @Version 1.0
 */
@Slf4j
@Aspect
@Configuration
public class LimitAspect {


    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private DefaultRedisScript<Number> redisluaScript;

    //执行redis的具体方法，限制method,保证没有其他的东西进来
    @Around("execution(* com.xd.pre.controller ..*(..) )")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();

        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit != null) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String ipAddress = ServletUtil.getClientIP(request);

            String string = ipAddress + "-" + targetClass.getName() + "- " + method.getName() + "-" + rateLimit.key();
            List<String> keys = Collections.singletonList(string);
            Number number = redisTemplate.execute(redisluaScript, keys, rateLimit.count(), rateLimit.time());

            if (number != null && number.intValue() != 0 && number.intValue() <= rateLimit.count()) {
                log.info("限流时间段内访问第：{} 次", number.toString());
                return joinPoint.proceed();
            }

        } else {
            return joinPoint.proceed();
        }
        log.error("已经到设置限流次数");
        throw new RuntimeException("已经到设置限流次数");
    }

}

