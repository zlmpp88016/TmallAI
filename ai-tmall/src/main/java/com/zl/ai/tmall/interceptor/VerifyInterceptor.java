package com.zl.ai.tmall.interceptor;

import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author:lei.zhu
 * @date:2020/11/13 9:40
 */
@Component
@Slf4j
public class VerifyInterceptor implements HandlerInterceptor {
    private static final String PREFIX_TMALL_API_PATH="/tmall/api/";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURL().toString();
        if(url.contains(PREFIX_TMALL_API_PATH)){
            new RequestWrapper(request);
        }
        return true;
    }
}
