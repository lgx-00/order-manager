package plus.lgx.ordermanager.service.impl;

import plus.lgx.ordermanager.entity.model.UserModel;
import plus.lgx.ordermanager.entity.pojo.User;
import plus.lgx.ordermanager.mapper.UserMapper;
import plus.lgx.ordermanager.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Map<Long, UserModel> getPerms(HashSet<Long> ids) {
        // TODO
        if (ids == null || ids.isEmpty()) return Collections.emptyMap();
        return lambdaQuery().in(User::getUserId, ids).list().stream().collect(Collectors.toMap(User::getUserId, UserModel::new));
    }

}
