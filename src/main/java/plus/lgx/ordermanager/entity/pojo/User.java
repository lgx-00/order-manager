package plus.lgx.ordermanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户编号，主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 登录用户名
     */
    @TableField("user_login_name")
    private String userLoginName;

    /**
     * 用户昵称
     */
    @TableField("user_nickname")
    private String userNickname;

    /**
     * 用户密码
     */
    @TableField("user_password")
    private String userPassword;

    /**
     * 用户密码盐
     */
    @TableField("user_password_salt")
    private String userPasswordSalt;

    /**
     * 邮箱地址
     */
    @TableField("user_email")
    private String userEmail;

    /**
     * 用户手机号
     */
    @TableField("user_phone")
    private String userPhone;


    /**
     * 用户是否为管理员
     */
    @TableField("user_is_admin")
    private Boolean userIsAdmin;

    /**
     * 创建时间
     */
    @TableField("user_created_at")
    private LocalDateTime userCreatedAt;

    /**
     * 上次登录状态，外键
     */
    @TableField("user_last_login_id")
    private Long userLastLoginId;

    /**
     * 用户状态，是否删除
     */
    @TableField("user_status")
    private Integer userStatus;


}
