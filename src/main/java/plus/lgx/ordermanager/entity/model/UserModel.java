package plus.lgx.ordermanager.entity.model;

import plus.lgx.ordermanager.entity.pojo.User;
import plus.lgx.ordermanager.entity.vo.LoginUserVO;

/**
 * 类型：UserModel
 *
 * @author lgx
 * @since 2024/8/24
 */
public class UserModel extends User {

    public UserModel(LoginUserVO user) {
        this.setUserLoginName(user.getUserName());
        this.setUserPassword(user.getUserPassword());
    }

}
