package plus.lgx.ordermanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.lgx.ordermanager.entity.model.UserModel;
import plus.lgx.ordermanager.entity.pojo.Login;
import plus.lgx.ordermanager.entity.pojo.User;
import plus.lgx.ordermanager.entity.vo.LoginUserVO;
import plus.lgx.ordermanager.mapper.LoginMapper;
import plus.lgx.ordermanager.service.LoginService;
import plus.lgx.ordermanager.service.UserService;
import plus.lgx.ordermanager.utils.EncodeUtil;
import plus.lgx.ordermanager.utils.TokenUtil;

import java.time.LocalDateTime;

/**
 * <p>
 * 登录 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {

    @Resource
    private UserService userService;

    @Override
    @Transactional
    public TokenUtil.Token login(LoginUserVO user) {
        User sysUser = userService.lambdaQuery()
                .eq(User::getUserLoginName, user.getUserName()).one();
        boolean equaled = sysUser != null &&
                EncodeUtil.isEqualed(sysUser.getUserPassword(), user.getUserPassword(), sysUser.getUserPasswordSalt());
        if (equaled) {
            Login login = new Login();
            login.setLoginIp(user.getIp());
            login.setUserId(sysUser.getUserId());
            login.setLoginTime(LocalDateTime.now());
            if (!save(login)) {
                throw new RuntimeException();
            }

            sysUser.setUserLastLoginId(login.getLoginId());
            UserModel model = new UserModel(sysUser);
            if (!userService.lambdaUpdate()
                    .set(User::getUserLastLoginId, login.getLoginId())
                    .eq(User::getUserId, sysUser.getUserId()).update()) {
                throw new RuntimeException();
            }
            return TokenUtil.generate(model);
        }
        return null;
    }
}
