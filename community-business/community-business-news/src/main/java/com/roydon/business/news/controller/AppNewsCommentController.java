package com.roydon.business.news.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.roydon.business.news.domain.dto.AppNewsCommentDTO;
import com.roydon.business.news.domain.entity.AppNewsComment;
import com.roydon.business.news.domain.vo.AppNewsCommentVO;
import com.roydon.business.news.service.IAppNewsCommentService;
import com.roydon.common.core.domain.AjaxResult;
import com.roydon.common.core.domain.entity.SysUser;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.bean.BeanCopyUtils;
import com.roydon.system.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (AppNewsComment)表控制层
 *
 * @author roydon
 * @since 2023-10-08 21:07:04
 */
@RestController
@RequestMapping("/app/news/comment")
public class AppNewsCommentController {

    @Resource
    private IAppNewsCommentService appNewsCommentService;

    @Resource
    private ISysUserService userService;

    /**
     * 分页查询新闻评论集合树
     */
    @PostMapping("/list")
    public AjaxResult queryTreeByNewsId(@RequestBody AppNewsCommentDTO pageDTO) {
        String newsId = pageDTO.getNewsId();
        if (StringUtil.isEmpty(newsId)) {
            AjaxResult.success();
        }
        IPage<AppNewsComment> iPage = this.appNewsCommentService.getTreeByNewsId(pageDTO);
        List<AppNewsComment> records = iPage.getRecords();
        List<AppNewsCommentVO> voList = new ArrayList<>();
        records.forEach(r -> {
            // 获取用户详情
            AppNewsCommentVO appNewsCommentVO = BeanCopyUtils.copyBean(r, AppNewsCommentVO.class);
            Long userId = r.getUserId();
            SysUser user = userService.getById(userId);
            appNewsCommentVO.setNickName(user.getNickName());
            appNewsCommentVO.setAvatar(user.getAvatar());
            voList.add(appNewsCommentVO);
        });
        return AjaxResult.genTableData(voList, iPage.getTotal());
    }

    /**
     * 新增数据
     *
     * @param appNewsComment 实体
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult add(@RequestBody AppNewsComment appNewsComment) {
        return AjaxResult.success(this.appNewsCommentService.insert(appNewsComment));
    }

    @PostMapping("apply")
    public AjaxResult apply(@RequestBody AppNewsComment appNewsComment) {
        return AjaxResult.success(this.appNewsCommentService.apply(appNewsComment));
    }

    /**
     * 删除数据
     */
    @DeleteMapping("{commentId}")
    public AjaxResult removeById(@PathVariable Long commentId) {
        return AjaxResult.success(this.appNewsCommentService.deleteById(commentId));
    }

}

