package plus.lgx.ordermanager.entity.vo;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import plus.lgx.ordermanager.entity.pojo.Order;

import java.util.List;

/**
 * 类型：OrderVO
 *
 * @author lgx
 * @since 2024/8/25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderVO extends Order {

    private List<String> tags;

    private List<Long> orderTag;

    public OrderVO(Order order) {
        BeanUtil.copyProperties(order, this);
    }

}
