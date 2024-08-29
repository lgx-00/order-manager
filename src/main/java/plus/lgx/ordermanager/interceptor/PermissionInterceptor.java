package plus.lgx.ordermanager.interceptor;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import plus.lgx.ordermanager.entity.model.PermissionModel;
import plus.lgx.ordermanager.entity.model.UserModel;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.enums.RequestMethodEnum;
import plus.lgx.ordermanager.utils.IPUtil;
import plus.lgx.ordermanager.utils.TokenUtil;
import plus.lgx.ordermanager.utils.UserHolder;

import java.util.Map;
import java.util.Set;

import static cn.hutool.core.util.CharsetUtil.UTF_8;
import static plus.lgx.ordermanager.constant.SystemConstant.AUTHORIZATION_HEADER;
import static plus.lgx.ordermanager.constant.SystemConstant.RESPONSE_CODE_NO_PERMISSION;


@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse resp,
            @NonNull Object handler
    ) throws Exception {
        //拦截器取到请求先进行判断，如果是OPTIONS请求，则放行
        if(HttpMethod.OPTIONS.name().equalsIgnoreCase(req.getMethod())) {
            return true;
        }

        log.info("【权限拦截器】远程主机地址：{}，请求路径：{}，请求方法：{}",
                IPUtil.getIpAddr(req), req.getServletPath(), req.getMethod());

        UserModel user = TokenUtil.getUser(req.getHeader(AUTHORIZATION_HEADER));

        // 管理员的后门
        if (user.getUserId().equals(1L)) {
            log.info("【权限拦截器】离开权限拦截器，管理员直接放行。");
            return true;
        }

        // 请求路径
        String url = req.getServletPath();
        RequestMethodEnum method = RequestMethodEnum.valueOf(req.getMethod());

        if (url.equals("/sys/user") && method.equals(RequestMethodEnum.GET)
                || url.startsWith("/basedata") && method.equals(RequestMethodEnum.GET)) {
            log.info("【权限拦截器】离开权限拦截器，请求顺利通过。");
            return true;
        }

        // 系统控制不在此设置拦截，拦截的位置统一设置在其内部的切面
        if (url.startsWith("/system-control")) {
            return true;
        }

        Map<String, PermissionModel> permission = user.getPermission();
        Set<String> keys = permission.keySet();
        boolean perm = false;

        for (String key : keys) {
            if (url.startsWith(key)) {
                // TODO 权限拦截
                perm = true;//(permission.get(key).getRpDetail() >> method.digit & 1) > 0;
                break;
            }
        }

        if (!perm) {
            resp.setCharacterEncoding(UTF_8);
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.getWriter().write(JSONUtil.toJsonStr(R.fail(RESPONSE_CODE_NO_PERMISSION, method.noPermissionMessage)));
            log.info("【权限拦截器】离开权限拦截器，请求被拦截。");
        } else {
            log.info("【权限拦截器】离开权限拦截器，请求顺利通过。");
        }
        return perm;
    }

    @Override
    public void afterCompletion(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse resp,
            @NonNull Object handler,
            Exception ex
    ) {
        UserHolder.removeUser();
    }
}
