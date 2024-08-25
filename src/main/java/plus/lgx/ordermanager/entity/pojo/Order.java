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
 * 订单
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("order")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 客户编号
     */
    @TableField("customer_id")
    private Long customerId;

    /**
     * 订单标题
     */
    @TableField("order_title")
    private String orderTitle;

    /**
     * 订单详情
     */
    @TableField("order_content")
    private String orderContent;

    /**
     * 订单创建时间
     */
    @TableField("order_created_at")
    private LocalDateTime orderCreatedAt;

    /**
     * 订单附件地址
     */
    @TableField("order_appendix_path")
    private String orderAppendixPath;

    /**
     * 订单附件名称
     */
    @TableField("order_appendix_name")
    private String orderAppendixName;

    /**
     * 订单截止日期
     */
    @TableField("order_deadline")
    private LocalDateTime orderDeadline;

    /**
     * 订单预算 * 100
     */
    @TableField("order_budget")
    private Integer orderBudget;

    /**
     * 订单支付方式
     */
    @TableField("order_payment")
    private String orderPayment;

    /**
     * 订单交易额 * 100
     */
    @TableField("order_sum")
    private Integer orderSum;

    /**
     * 订单完成时间
     */
    @TableField("order_complete")
    private LocalDateTime orderComplete;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;


}
