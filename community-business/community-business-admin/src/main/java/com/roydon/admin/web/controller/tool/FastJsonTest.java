package com.roydon.admin.web.controller.tool;

import com.alibaba.fastjson2.JSON;

import java.util.List;

/**
 * FastJsonTest
 *
 * @AUTHOR: roydon
 * @DATE: 2023/4/25
 **/
public class FastJsonTest {
    public static void main(String[] args) {
        String image = "[{\\\"name\\\":\\\"1.jpg\\\",\\\"url\\\":\\\"https://ggzy.guizhou.gov.cn/zhdt/tpxw/202205/W020220531570286566708.jpg\\\"}]";
        System.out.println("image = " + image);
        String jsonStr = image.replace("\\\"", "\"");
        System.out.println("jsonStr = " + jsonStr);

        // JSON字符串转Java对象
//        JSONObject json = JSON.parseObject(image);
//        Image i = JSON.toJavaObject(json, Image.class);

//        System.out.println(i.toString());

        List<Image> personList = JSON.parseArray(jsonStr, Image.class);

        System.out.println(personList.get(0).toString());

    }
}
