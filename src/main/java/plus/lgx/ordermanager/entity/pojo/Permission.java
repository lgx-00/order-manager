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
 * 权限
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("permission")
public class Permission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 权限编号
     */
    @TableId(value = "perm_id", type = IdType.AUTO)
    private Long permId;

    /**
     * 权限名称
     */
    @TableField("perm_name")
    private String permName;

    /**
     * 权限类型
     */
    @TableField("perm_type")
    private String permType;

    /**
     * 权限路径
     */
    @TableField("perm_url")
    private String permUrl;

    /**
     * 权限方法
     */
    @TableField("perm_method")
    private String permMethod;

    /**
     * 权限状态
     */
    @TableField("perm_status")
    private Integer permStatus;


}
