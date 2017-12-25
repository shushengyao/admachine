package com.xmlan.machine.common.annotation

import java.lang.annotation.*

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@interface MyBatisDao {

    String value() default ""

}
