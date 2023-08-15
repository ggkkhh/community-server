package com.roydon.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.app.domain.dto.AppMessagePageDTO;
import com.roydon.app.domain.entity.AppMessage;
import com.roydon.app.enums.MessageStatusEnum;
import com.roydon.app.service.IAppMessageService;
import com.roydon.common.core.controller.BaseController;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.utils.SecurityUtils;
import com.roydon.common.utils.poi.ExcelUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * app消息通知Controller
 *
 * @author roydon
 * @date 2023-08-15
 */
@RestController
@RequestMapping("/app/message")
public class AppMessageController extends BaseController {

    @Resource
    private IAppMessageService appMessageService;

    /**
     * 查询用户未读消息条数
     */
    @PreAuthorize("@ss.hasAny()")
    @GetMapping("/count")
    public AjaxResult countForUser() {
        LambdaQueryWrapper<AppMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppMessage::getUserId, SecurityUtils.getUserId());
        queryWrapper.eq(AppMessage::getMessageStatus, MessageStatusEnum.NOT_READ.getCode());
        return AjaxResult.success(appMessageService.count(queryWrapper));
    }

    /**
     * 查询app消息通知列表
     */
    @PreAuthorize("@ss.hasAny()")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody AppMessagePageDTO appMessagePageDTO) {
        IPage<AppMessage> messageIPage = appMessageService.queryPage(appMessagePageDTO);
        return AjaxResult.genTableData(messageIPage.getRecords(), messageIPage.getTotal());
    }

    /**
     * 导出app消息通知列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, AppMessage appMessage) {
        List<AppMessage> list = appMessageService.selectAppMessageList(appMessage);
        ExcelUtil<AppMessage> util = new ExcelUtil<AppMessage>(AppMessage.class);
        util.exportExcel(response, list, "app消息通知数据");
    }

    /**
     * 获取app消息通知详细信息
     */
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId) {
        return success(appMessageService.selectAppMessageByMessageId(messageId));
    }

    /**
     * 新增app消息通知
     */
    @PostMapping
    public AjaxResult add(@RequestBody AppMessage appMessage) {
        return toAjax(appMessageService.insertAppMessage(appMessage));
    }

    /**
     * 修改app消息通知
     */
    @PutMapping
    public AjaxResult edit(@RequestBody AppMessage appMessage) {
        return toAjax(appMessageService.updateAppMessage(appMessage));
    }

    /**
     * 删除app消息通知
     */
    @DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds) {
        return toAjax(appMessageService.deleteAppMessageByMessageIds(messageIds));
    }
}
