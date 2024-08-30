package plus.lgx.ordermanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import plus.lgx.ordermanager.entity.pojo.Order;
import plus.lgx.ordermanager.entity.vo.QueryOrderParam;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
public interface OrderMapper extends BaseMapper<Order> {

    @SuppressWarnings("UnusedReturnValue")
    List<Order> queryByCondition(@Param("param") QueryOrderParam param);

}
