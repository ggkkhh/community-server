package com.roydon.web.controller;

import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.system.service.ISysUserService;
import com.roydon.web.domain.dto.UserRegisterBody;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * UserController
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/26
 **/
@Api("用户接口")
@RestController()
@RequestMapping("/user")
public class UserController {

    @Resource
    private ISysUserService userService;

    /**
     * 用户使用手机号注册
     *
     * @param userRegisterBody
     * @return
     */
    @PostMapping("/register")
    public AjaxResult add(@RequestBody UserRegisterBody userRegisterBody) {
        if (StringUtils.isNotEmpty(userRegisterBody.getTelephone()) && StringUtils.isNotNull(userService.checkTelephoneExists(userRegisterBody.getTelephone()))) {
            return AjaxResult.error("新增用户失败，手机号码'" + userRegisterBody.getTelephone() + "'已存在");
        } else if(userRegisterBody.getCode()){

        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }


}
