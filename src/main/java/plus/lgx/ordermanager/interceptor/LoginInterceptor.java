package plus.lgx.ordermanager.interceptor;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import plus.lgx.ordermanager.entity.model.UserModel;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.utils.IPUtil;
import plus.lgx.ordermanager.utils.TokenUtil;
import plus.lgx.ordermanager.utils.UserHolder;

import java.io.IOException;

import static cn.hutool.core.util.CharsetUtil.UTF_8;
import static plus.lgx.ordermanager.constant.SystemConstant.*;

/**
 * @author lgx
 * @since 2023-06-05-15:08
 */

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse resp,
            @NonNull Object handler
    ) throws Exception {

        // 拦截器取到请求先进行判断，如果是 OPTIONS 请求，则放行
        if(HttpMethod.OPTIONS.name().equalsIgnoreCase(req.getMethod())) {
            return true;
        }

        // 验证 Token 的有效性
        String xToken = req.getHeader(AUTHORIZATION_HEADER);
        UserModel user = TokenUtil.getUser(xToken);
        if (user == null) {
            log.info("【身份验证拦截器】令牌无效，来自 {} 的请求 {} 已被拦截。",
                    IPUtil.getIpAddr(req), req.getRequestURI());
            return fail(resp);
        }

        // 保存用户信息到 ThreadLocal
        UserHolder.saveUser(user);

        // 放行
        return true;
    }

    private boolean fail(HttpServletResponse response) throws IOException {
        // 拦截，返回 401 状态码
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8);
        response.getWriter().write(JSONUtil.toJsonStr(R.unauthorized()));

        return false;
    }
}
