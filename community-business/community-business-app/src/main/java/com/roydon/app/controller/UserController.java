package com.roydon.app.controller;

import com.roydon.app.domain.vo.AppUser;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.bean.BeanCopyUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/26
 **/
@Api("用户接口")
@RestController()
@RequestMapping("/app/user")
public class UserController extends BaseController {

    /**
     * 获取用户信息返回给APP端
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    public AjaxResult getInfo() {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        AppUser appUser = BeanCopyUtils.copyBean(sysUser, AppUser.class);
        return AjaxResult.success(appUser);
    }

}
