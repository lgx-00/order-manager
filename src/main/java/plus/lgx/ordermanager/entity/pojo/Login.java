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
 * 登录
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("login")
public class Login implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 登录编号
     */
    @TableId(value = "login_id", type = IdType.AUTO)
    private Long loginId;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 登录地址
     */
    @TableField("login_ip")
    private Long loginIp;

    /**
     * 登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;


}
