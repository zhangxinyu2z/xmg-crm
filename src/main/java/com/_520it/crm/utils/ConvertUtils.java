package com._520it.crm.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @created on 2020/5/25
 */
@Slf4j
public class ConvertUtils {

    public static <T> T convert(Object source, Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            log.error("ConvertUtils convert Exception:{}", e.getMessage(), e);
        }
        return null;
    }

    public static <T> List<T> convertList(List<?> sourceList, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        try {
            List<T> list = new ArrayList<>();
            for (Object source : sourceList) {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(source, t);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            log.error("ConvertUtils convert Exception:{}", e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>)obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
