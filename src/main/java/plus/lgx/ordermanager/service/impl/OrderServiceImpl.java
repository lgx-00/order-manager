package plus.lgx.ordermanager.service.impl;

import plus.lgx.ordermanager.entity.pojo.Order;
import plus.lgx.ordermanager.mapper.OrderMapper;
import plus.lgx.ordermanager.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
