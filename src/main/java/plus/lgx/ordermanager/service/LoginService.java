package plus.lgx.ordermanager.service;

import plus.lgx.ordermanager.entity.pojo.Login;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录 服务类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
public interface LoginService extends IService<Login> {

    Login test();


}
