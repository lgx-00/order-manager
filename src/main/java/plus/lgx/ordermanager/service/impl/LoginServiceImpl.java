package plus.lgx.ordermanager.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import plus.lgx.ordermanager.entity.pojo.Login;
import plus.lgx.ordermanager.entity.pojo.User;
import plus.lgx.ordermanager.mapper.LoginMapper;
import plus.lgx.ordermanager.service.LoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.lgx.ordermanager.service.UserService;

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
    public Login test() {
        try (Page<User> page = PageHelper.startPage(0, 10)) {
            System.out.println(page.doSelectPageInfo(() -> userService.lambdaQuery().list()));
        }
        return null;
    }
}
