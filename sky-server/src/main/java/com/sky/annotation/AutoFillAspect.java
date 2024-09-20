package com.sky.annotation;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
/**
 * 自定义切面类,实现公共字段自动填充功能
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        log.info("开始自动填充....");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); // 获取方法上的注解
        OperationType operationType = autoFill.value(); // 获得数据库操作类型
        System.out.println(operationType);

        // 获取方法参数
        Object[] args = joinPoint.getArgs();

        if (args == null && args.length == 0) {
            return;
        }

        Object entity = args[0];

        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        Class<?> aClass = entity.getClass();

        if (operationType == OperationType.INSERT) {
            aClass.getMethod("setCreateTime", LocalDateTime.class).invoke(entity, now);
            aClass.getMethod("setUpdateTime", LocalDateTime.class).invoke(entity, now);
            aClass.getMethod("setCreateUser", Long.class).invoke(entity, currentId);
            aClass.getMethod("setUpdateUser", Long.class).invoke(entity, currentId);
        } else if (operationType == OperationType.UPDATE) {
            aClass.getMethod("setUpdateTime", LocalDateTime.class).invoke(entity, now);
            aClass.getMethod("setUpdateUser", Long.class).invoke(entity, currentId);

        }
        log.info("对象:{}", entity);
    }
}
