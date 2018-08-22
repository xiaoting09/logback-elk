package com.xiao.commons.interceptor;

import com.alibaba.fastjson.JSONObject;

import com.xiao.commons.utils.BaseConstants;
import com.xiao.commons.utils.CusAccessObjectUtil;
import com.xiao.commons.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaoting
 * @Description: 日志拦截器
 * @Date: create in 15:49 2018/8/16
 * @Modified By:
 * 默认拦截记录info级别的日志,且默认不使用拦截器
 * 使用方式:在yml中配置:
 * app:log:enable:true
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = LogInterceptor.APP_LOG, name = "enable", havingValue = "true")
public class LogInterceptor implements HandlerInterceptor, Ordered {

    protected static final String APP_LOG = "app.log";


    /**
     * 获取默认日志级别
     */
    private static final Boolean LOG_LEVEL = log.isInfoEnabled();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (LOG_LEVEL) {
            String logs = parameters2String(request.getParameterMap(), getBaseInfoMap(handler, request));
            LogUtils.toInfoLog(logs);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }

    /**
     * 转换参数为字符串
     *
     * @param params  post参数
     * @param baseMap 基础参数map
     * @return
     */
    private static String parameters2String(Map<String, String[]> params, Map<String, String> baseMap) {
        if (params == null || params.size() <= 0) {
            return JSONObject.toJSONString(baseMap);
        }
        params.forEach((key, values) -> baseMap.put(key, values[0]));
        return JSONObject.toJSONString(baseMap);
    }

    /**
     * 获取默认 公用基础参数
     *
     * @param handler handler
     * @param request request
     * @return
     */
    private static Map<String, String> getBaseInfoMap(Object handler, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(30, 0.5f);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        for (MethodParameter methodParameter : handlerMethod.getMethodParameters()) {
//            if (BaseRequest.class.isAssignableFrom(methodParameter.getParameterType())) {
//              基础请求类书籍
//            }
        }
        map.put(BaseConstants.BASE_METHOD, request.getMethod());
        map.put(BaseConstants.BASE_URI, request.getRequestURI());
        map.put(BaseConstants.REQUEST_IP, CusAccessObjectUtil.getIpAddress(request));
        return map;

    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
