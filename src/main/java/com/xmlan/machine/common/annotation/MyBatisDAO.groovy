package com.xmlan.machine.common.annotation

import java.lang.annotation.*

/**
 * MyBatis DAO标记注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@interface MyBatisDAO {

    String value() default ""

}
