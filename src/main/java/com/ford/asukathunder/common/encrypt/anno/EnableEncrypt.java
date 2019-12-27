package com.ford.asukathunder.common.encrypt.anno;

import com.ford.asukathunder.common.encrypt.auto.EncryptAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;
/**
 * @ClassName: EnableEncrypt
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:49
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({EncryptAutoConfiguration.class})
public @interface EnableEncrypt {
}
