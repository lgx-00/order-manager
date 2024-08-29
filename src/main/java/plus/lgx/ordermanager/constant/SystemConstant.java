package plus.lgx.ordermanager.constant;

/**
 * 类型：SystemConstant
 *
 * @author lgx
 * @since 2024/8/24
 */
public class SystemConstant {

    public static final int RESPONSE_CODE_OK = 0;

    public static final int RESPONSE_CODE_FAIL = -1;

    public static final int RESPONSE_CODE_NO_PERMISSION = -403;

    public static final int RESPONSE_CODE_UNAUTHORIZED = -401;

    public static final int DELETED_STATUS = 100;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String X_REAL_IP_HEADER = "X-Real-IP";

    public static final String DELETE_FAILED = "删除失败";

    public static final String UPDATE_FAILED = "更新失败";

    public static final String SAVE_FAILED = "新增失败";

    public static final String RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR = "服务器异常";

    public static final String RESPONSE_MESSAGE_UNAUTHORIZED = "请先登录";

    public static final String RESPONSE_MESSAGE_NAME_OR_PASSWD_ERROR = "用户名或密码错误";

}
