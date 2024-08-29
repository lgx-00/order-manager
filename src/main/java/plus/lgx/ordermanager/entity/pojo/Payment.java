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
 * 支付
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("payment")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 支付编号
     */
    @TableId(value = "payment_id", type = IdType.AUTO)
    private Long paymentId;

    /**
     * 支付方式
     */
    @TableField("payment_method")
    private String paymentMethod;

    /**
     * 支付时间
     */
    @TableField("payment_created_at")
    private LocalDateTime paymentCreatedAt;

    /**
     * 支付金额 * 100
     */
    @TableField("payment_sum")
    private Integer paymentSum;


}
