package plus.lgx.ordermanager.entity.model;

import cn.hutool.core.bean.BeanUtil;
import plus.lgx.ordermanager.entity.pojo.User;

import java.util.Collections;
import java.util.Map;

/**
 * 类型：UserModel
 *
 * @author lgx
 * @since 2024/8/24
 */
public class UserModel extends User {

    public UserModel(User user) {
        BeanUtil.copyProperties(user, this);
    }

    public Map<String, PermissionModel> getPermission() {
        // TODO
        return Collections.emptyMap();
    }

}
