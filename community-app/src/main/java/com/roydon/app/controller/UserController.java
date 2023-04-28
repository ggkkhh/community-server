package com.roydon.app.controller;

import com.roydon.common.constant.CacheConstants;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.exception.alisms.SmsException;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.StringUtils;
import com.roydon.system.service.ISysUserService;
import com.roydon.app.domain.dto.UserRegisterBody;
import io.swagger.annotations.Api;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.roydon.common.utils.SecurityUtils.getUsername;

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




}
