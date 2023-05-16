package com.roydon.business.news.httpclient;

import com.alibaba.fastjson2.JSONObject;
import com.roydon.business.news.config.NewsConfig;
import com.roydon.business.news.domain.AppNews;
import com.roydon.business.news.enums.NewsType;
import com.roydon.business.news.model.*;
import com.roydon.business.news.service.AppNewsService;
import com.roydon.common.constant.Constants;
import com.roydon.common.utils.StringUtil;
import com.roydon.common.utils.http.HttpUtils;
import com.roydon.framework.manager.AsyncManager;
import com.roydon.framework.manager.factory.AsyncFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * GetNewsService
 *
 * @AUTHOR: roydon
 * @DATE: 2023/5/13
 **/
@Service
public class GetNewsService {

    private static final Logger log = LoggerFactory.getLogger(GetNewsService.class);

    @Resource
    private NewsConfig newsConfig;

    @Resource
    private AppNewsService appNewsService;

    public void getNewsList() {
        List<Integer> typeIdList = getTypeIdList();
        typeIdList.forEach(t -> {
            log.warn("开始获取新闻类型为[{}]的新闻！", t);
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String newsListUrl = newsConfig.getNewsListUrl(t, 1);
            String sendGet = HttpUtils.sendGet(newsListUrl);
            NewsRespResult newsRespResult = JSONObject.parseObject(sendGet, NewsRespResult.class);
            if (StringUtil.isEmpty(newsListUrl) || newsRespResult.getCode() != 1) {
                log.warn("新闻接口获取数据为空！");
                throw new RuntimeException();// 获取失败
            }
            List<News> data = newsRespResult.getData();
            if (StringUtil.isEmpty(data)) {
                log.warn("新闻接口获取数据为空！");
                return;
            }
            data.forEach(d -> {
                // 过滤封面为空和id为空
                if (StringUtil.isEmpty(d.getImgList()) || StringUtil.isEmpty(d.getNewsId())) {
                    return;
                }
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                NewsDetailsRespResult newsDetailsRespResult = getNewsDetailsRespResult(d.getNewsId());
                NewsDetails newsDetails = newsDetailsRespResult.getData();
                if (StringUtil.isEmpty(newsDetailsRespResult) || newsDetailsRespResult.getCode() != 1 || StringUtil.isEmpty(newsDetails)) {
                    log.warn("新闻详情接口获取数据为空！");
                    return;// 获取失败
                }
                AppNews an = new AppNews();
                an.setNewsId(d.getNewsId());
                an.setNewsTitle(d.getTitle());
                an.setCoverImg(d.getImgList().get(0));
                an.setSource(d.getSource());
                an.setNewsType(t);
                an.setThereNewsId(d.getNewsId());
                an.setDigest(d.getDigest());
                an.setPostTime(d.getPostTime());
                List<Images> images = newsDetails.getImages();
                String content = newsDetails.getContent();
                String replace =null;
                images.forEach(i -> {
                    String position = i.getPosition();
                    String img = "<img src=\"" + i.getImgSrc() + "\" alt=\"加载失败\">";
//                    replace = content.replace(position.toString(), img.toString());
                });
                an.setNewsContent(content);
                an.setContentImages(images.toString());
                an.setDelFlag(0);
                // 批量添加到数据库
                appNewsService.saveOrUpdate(an);
                log.info("插入或更新新闻数据成功！");
            });
        });


    }

    public NewsRespResult getNewsRespResult() {
        String newsListUrl = newsConfig.getNewsListUrl(NewsType.HEADLINE.getTypeId(), 1);
        String sendGet = HttpUtils.sendGet(newsListUrl);
        NewsRespResult newsRespResult = JSONObject.parseObject(sendGet, NewsRespResult.class);
        return newsRespResult;
    }

    public NewsDetailsRespResult getNewsDetailsRespResult(String newsId) {
        String newsDetailsUrl = newsConfig.getNewsDetailsUrl(newsId);
        String sendGet = null;
        NewsDetailsRespResult newsDetailsRespResult = null;
        try {
            sendGet = HttpUtils.sendGet(newsDetailsUrl);
            newsDetailsRespResult = JSONObject.parseObject(sendGet, NewsDetailsRespResult.class);
        } catch (Exception e) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(newsDetailsUrl, Constants.GET_NEWS_FAIL, e.getMessage()));
            throw new RuntimeException();
        }
        return newsDetailsRespResult;

    }

    public List<Integer> getTypeIdList() {
        List<Integer> values = new ArrayList<>();
        for (NewsType newsType : NewsType.values()) {
            values.add(newsType.getTypeId());
        }
        return values;
    }

}
