package plus.lgx.ordermanager.controller;


import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import plus.lgx.ordermanager.entity.model.UserModel;
import plus.lgx.ordermanager.entity.vo.LoginUserVO;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.service.LoginService;
import plus.lgx.ordermanager.utils.TokenUtil;

/**
 * <p>
 * 登录 前端控制器
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public R<TokenUtil.Token> login(LoginUserVO user) {
        loginService.test();
        UserModel model = new UserModel(user);
        return R.ok(TokenUtil.generate(model));
    }

    @GetMapping("user-info")
    public R<UserModel> getUserInfo(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        return R.ok(TokenUtil.getUser(token));
    }



}

