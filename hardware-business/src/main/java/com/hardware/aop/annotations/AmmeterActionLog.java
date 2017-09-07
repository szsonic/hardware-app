package com.hardware.aop.annotations;


import com.hardware.aop.enums.AmmeterOperateParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br>
 * 电表操作日志（通电、断电）
 * @author sunzhongshuai
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AmmeterActionLog {
    AmmeterOperateParams param();
}
