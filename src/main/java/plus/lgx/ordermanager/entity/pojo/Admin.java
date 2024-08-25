package plus.lgx.ordermanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 管理人员
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("admin")
public class Admin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 管理人员编号，主键
     */
    @TableId(value = "admin_id", type = IdType.AUTO)
    private Long adminId;

    /**
     * 用户编号，外键
     */
    @TableField("user_id")
    private Long userId;


}
