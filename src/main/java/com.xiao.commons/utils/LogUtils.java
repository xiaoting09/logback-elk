package com.xiao.commons.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: xiaoting
 * @Description: 日志打印工具类
 * @Date: create in 14:00 2018/8/16
 * @Modified By:
 */
@Slf4j
public class LogUtils {
    /**
     * 打印日志 根据系统级别打印
     *
     * @param json json
     */
    public static void toLog(Object obj) {
        String json = JSONObject.toJSONString(obj);
        if (log.isInfoEnabled()) {
            toInfoLog(json);
        } else if (log.isErrorEnabled()) {
            toErrorLog(json);
        } else if (log.isDebugEnabled()) {
            toDebugLog(json);
        } else if (log.isWarnEnabled()) {
            toWarnLog(json);
        }
    }

    /**
     * 打印错误日志
     *
     * @param json json数据
     */
    public static void toErrorLog(String json) {
        log.error(json);
    }

    /**
     * warn
     *
     * @param json json数据
     */
    public static void toWarnLog(String json) {
        log.warn(json);
    }

    /**
     * info
     *
     * @param json json数据
     */
    public static void toInfoLog(String json) {
        log.info(json);
    }

    /**
     * debug
     *
     * @param json json数据
     */
    public static void toDebugLog(String json) {
        log.debug(json);
    }


}
