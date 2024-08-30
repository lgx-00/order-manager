package plus.lgx.ordermanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("`order`")
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
    @NotBlank(message = "订单标题不能为空")
    private String orderTitle;

    /**
     * 订单详情
     */
    @TableField("order_content")
    @NotBlank(message = "订单详情不能为空")
    private String orderContent;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("order_created_at")
    private LocalDateTime orderCreatedAt;

    /**
     * 订单创建时间
     */
    @TableField("order_created_by")
    private Long orderCreatedBy;

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
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("order_deadline")
    private LocalDate orderDeadline;

    /**
     * 订单预算 * 100
     */
    @TableField("order_budget")
    private Integer orderBudget;

    /**
     * 订单支付方式
     */
    @TableField("order_payment")
    private Long orderPayment;

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

    /**
     * 订单来源
     */
    @TableField("order_source")
    private Long orderSource;


}
