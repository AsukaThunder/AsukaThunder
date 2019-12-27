package com.ford.asukathunder.controller.login;

import com.alibaba.fastjson.JSON;
import com.ford.asukathunder.common.annotation.PublicInterface;
import com.ford.asukathunder.common.encrypt.anno.Encrypt;
import com.ford.asukathunder.common.entity.role.UserRoleRef;
import com.ford.asukathunder.common.entity.user.User;
import com.ford.asukathunder.common.exception.UnauthorizedException;
import com.ford.asukathunder.controller.login.dto.DetailLoginDTO;
import com.ford.asukathunder.controller.login.dto.LoginDTO;
import com.ford.asukathunder.service.UserService;
import com.ford.asukathunder.util.CheckoutUtil;
import com.ford.asukathunder.util.ErrorCode;
import com.ford.asukathunder.util.JwtToken;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: LoginController
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/27 下午 1:46
 **/
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @PostMapping(value = "/login", produces = "application/json")
    @ApiOperation(value = "登录")
    @PublicInterface
    @Encrypt
    public DetailLoginDTO login(@RequestBody LoginDTO dto) {
        logger.info("login info is {}", JSON.toJSONString(dto));
        User user = userService.queryByAccountAndPassword(dto.getAccount(), CheckoutUtil.md5(dto.getPassword()));
        if (null == user) {
            throw new UnauthorizedException(ErrorCode.UserOrPasswordError);
        }

        // 判断当前用户是否有已弃用的角色
        List<UserRoleRef> refs = user.getUserRoleRef().stream().filter(ref -> ref.getRole().getEnable()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(refs)) {
            throw new UnauthorizedException(ErrorCode.NoRoleEnable);
        }

        String token = JwtToken.createToken(user.getUserId(), dto.isFromMobile());
        DetailLoginDTO result = new DetailLoginDTO().convertFrom(user);
        result.setToken(token);
        return result;
    }
}
