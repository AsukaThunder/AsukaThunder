package com.ford.asukathunder.controller.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: UpdateUserPasswordDTO
 * @author: Ford.Zhang
 * @version: 1.0
 * 2020/1/9 下午 5:47
 **/
@Getter
@Setter
public class UpdateUserPasswordDTO {

    private String password;

    private String repeatPassword;

    private String oldPassword;

}
