package plus.lgx.ordermanager.service;

import plus.lgx.ordermanager.entity.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.lgx.ordermanager.entity.model.UserModel;

import java.util.HashSet;
import java.util.Map;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
public interface UserService extends IService<User> {

    Map<Integer, UserModel> getPerms(HashSet<Integer> ids);
}
