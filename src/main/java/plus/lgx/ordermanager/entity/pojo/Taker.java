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
 * 接单员
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("taker")
public class Taker implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 接单员编号，主键
     */
    @TableId(value = "taker_id", type = IdType.AUTO)
    private Long takerId;

    /**
     * 用户编号，外键
     */
    @TableField("user_id")
    private Long userId;


}
