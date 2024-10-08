package plus.lgx.ordermanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 客户
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("customer")
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户编号，主键
     */
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Long customerId;

    /**
     * 用户编号，外键
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 客户来源
     */
    @TableField("customer_source")
    private Long customerSource;


}
