package com.ford.asukathunder.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: PublicInterface
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 3:14
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicInterface {

}
