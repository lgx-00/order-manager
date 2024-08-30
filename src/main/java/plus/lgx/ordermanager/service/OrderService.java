package plus.lgx.ordermanager.service;

import com.github.pagehelper.PageInfo;
import plus.lgx.ordermanager.entity.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import plus.lgx.ordermanager.entity.vo.OrderVO;
import plus.lgx.ordermanager.entity.vo.QueryOrderParam;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
public interface OrderService extends IService<Order> {

    boolean del(Integer id);

    void saveOrder(OrderVO order);

    void updateOrder(OrderVO order);

    PageInfo<OrderVO> queryPage(int pageNum, int pageSize, QueryOrderParam param);
}
