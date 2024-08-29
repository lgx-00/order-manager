package plus.lgx.ordermanager.service;

import plus.lgx.ordermanager.entity.pojo.Login;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.lgx.ordermanager.entity.vo.LoginUserVO;
import plus.lgx.ordermanager.utils.TokenUtil;

/**
 * <p>
 * 登录 服务类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
public interface LoginService extends IService<Login> {

    TokenUtil.Token login(LoginUserVO user);


}
