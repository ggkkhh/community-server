package com.roydon.common.utils.bean;

import com.alibaba.fastjson2.JSON;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @USER: roydon
 * @DATE: 2023/4/27 9:47
 * @Description 对象拷贝工具
 **/
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream().map(o -> copyBean(o, clazz)).collect(Collectors.toList());
    }

    /**
     * 属性类型不同的转换
     *
     * @param source
     * @param clazz
     * @param <V>
     * @return
     */
    public static <V> V copyBeanIgnore(Object source, Class<V> clazz) {
        V v = JSON.parseObject(JSON.toJSONString(source), clazz);
        return v;
    }

}
