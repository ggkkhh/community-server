package com.roydon.app.controller;

import com.roydon.app.domain.vo.AppUser;
import com.roydon.common.annotation.Log;
import com.roydon.common.constant.UserConstants;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.enums.BusinessType;
import com.roydon.common.utils.EmailUtils;
import com.roydon.common.utils.PhoneUtils;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtils;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @Resource
    private ISysUserService userService;

    /**
     * 获取用户信息返回给APP端
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    public AjaxResult getInfo() {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        AppUser appUser = BeanCopyUtils.copyBean(userService.selectUserById(sysUser.getUserId()), AppUser.class);
        return AjaxResult.success(appUser);
    }

    /**
     * 用户修改信息
     */
    @ApiOperation("用户修改信息")
    @Log(title = "APP用户", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody SysUser user) {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        user.setUserId(sysUser.getUserId());
        // 检测账号是否存在
        if (StringUtils.isNotNull(user.getUserName()) && UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return AjaxResult.error("修改失败，账号已存在");
        }
        // 检测昵称是否存在
        if (StringUtils.isNotNull(user.getNickName()) && UserConstants.NOT_UNIQUE.equals(userService.checkNickNameUnique(user))) {
            return AjaxResult.error("修改失败，昵称已存在");
        }
        // 检测姓名合法性
        if (StringUtils.isNotNull(user.getRealName())) {
            if (user.getRealName().length() >= 5) {
                return AjaxResult.error("修改失败，姓名不合法");
            }
        }
        // 检测手机号合法性
        if (StringUtils.isNotNull(user.getPhonenumber())) {
            if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
                return AjaxResult.error("修改失败，手机号已存在");
            } else if (!PhoneUtils.isMobile(user.getPhonenumber())) {
                return AjaxResult.error("修改失败，手机号不合法");
            }
        }
        // 检测邮箱合法性
        if (StringUtils.isNotNull(user.getEmail())) {
            if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
                return AjaxResult.error("修改失败，邮箱已存在");
            } else if (!EmailUtils.isValidEmail(user.getEmail())) {
                return AjaxResult.error("修改失败，邮箱格式错误");
            }
        }
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateById(user));
    }

}
