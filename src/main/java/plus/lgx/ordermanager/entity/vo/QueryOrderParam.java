package plus.lgx.ordermanager.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import plus.lgx.ordermanager.entity.pojo.Order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 类型：QueryOrderParam
 *
 * @author lgx
 * @since 2024/8/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryOrderParam extends Order {

    /**
     * 订单截止日期范围最小值
     */
    private LocalDateTime orderDeadlineMin;

    /**
     * 订单截止日期范围最大值
     */
    private LocalDateTime orderDeadlineMax;

    /**
     * 订单预算范围最小值 * 100
     */
    private Integer orderBudgetMin;

    /**
     * 订单预算范围最大值 * 100
     */
    private Integer orderBudgetMax;

    /**
     * 订单交易额最小值 * 100
     */
    private Integer orderSumMin;

    /**
     * 订单交易额最大值 * 100
     */
    private Integer orderSumMax;

    /**
     * 订单完成时间最小值
     */
    private LocalDateTime orderCompleteMin;

    /**
     * 订单完成时间最大值
     */
    private LocalDateTime orderCompleteMax;

    /**
     * 订单来源列表
     */
    private List<Long> orderSourceList;

    /**
     * 订单技术标签
     */
    private List<Long> orderTag;

}
