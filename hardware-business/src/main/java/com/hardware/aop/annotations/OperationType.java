package com.hardware.aop.annotations;


import com.hardware.aop.enums.OperationCategory;
import com.hardware.aop.enums.OperationContent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br>
 *
 * @author sunzhongshuai
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationType {
    OperationContent value();
    OperationCategory type();
}
