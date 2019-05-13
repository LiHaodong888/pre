package com.xd.pre.security.handle;

import cn.hutool.http.Status;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 权限不足处理类 返回403
 * @author lihaodong
 */
@Slf4j
@Component("AccessDeniedHandlerImpl")
public class AccessDeniedHandlerImpl implements AccessDeniedHandler , Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        log.info("请求: " + request.getRequestURI() + " 权限不足，无法访问系统资源.");
        log.error(e.getMessage());
        SecurityUtil.writeJavaScript(R.error(Status.HTTP_FORBIDDEN,"权限不足,禁止访问"),response);
    }
}
