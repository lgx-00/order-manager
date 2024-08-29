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
 * 第三方平台
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("third_platform")
public class ThirdPlatform implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 平台编号
     */
    @TableId(value = "platform_id", type = IdType.AUTO)
    private Long platformId;

    /**
     * 平台名称
     */
    @TableField("platform_name")
    private String platformName;

    /**
     * 状态
     */
    @TableField("platform_status")
    private String platformStatus;

}
