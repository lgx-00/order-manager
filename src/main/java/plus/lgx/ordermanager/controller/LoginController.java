package plus.lgx.ordermanager.controller;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import plus.lgx.ordermanager.entity.model.UserModel;
import plus.lgx.ordermanager.entity.vo.LoginUserVO;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.service.LoginService;
import plus.lgx.ordermanager.utils.IPUtil;
import plus.lgx.ordermanager.utils.TokenUtil;
import plus.lgx.ordermanager.utils.UserHolder;

import static plus.lgx.ordermanager.constant.SystemConstant.AUTHORIZATION_HEADER;
import static plus.lgx.ordermanager.constant.SystemConstant.RESPONSE_MESSAGE_NAME_OR_PASSWD_ERROR;

/**
 * <p>
 * 登录 前端控制器
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public R<TokenUtil.Token> login(HttpServletRequest req, @RequestBody @Valid LoginUserVO user) {
        user.setIp(IPUtil.toLongIp(IPUtil.getIpAddr(req)));
        try {
            TokenUtil.Token token = loginService.login(user);
            return token != null ? R.ok(token) : R.fail(RESPONSE_MESSAGE_NAME_OR_PASSWD_ERROR);
        } catch (Exception e) {
            log.warn("登录失败", e);
            return R.internalServerError();
        }
    }

    // TODO 检验 IP 是否与登录时一致
    @GetMapping("user-info")
    public R<UserModel> getUserInfo(@RequestHeader(AUTHORIZATION_HEADER) String token) {
        return R.ok(UserHolder.getUser());
    }

    // TODO 退出登录
}

