package plus.lgx.ordermanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.lgx.ordermanager.entity.pojo.Order;
import plus.lgx.ordermanager.entity.vo.OrderVO;
import plus.lgx.ordermanager.entity.vo.QueryOrderParam;
import plus.lgx.ordermanager.mapper.OrderMapper;
import plus.lgx.ordermanager.service.OrderService;

import java.util.Objects;

import static plus.lgx.ordermanager.constant.SystemConstant.DELETED_STATUS;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public boolean del(Integer id) {
        return lambdaUpdate().eq(Order::getOrderId, id).set(Order::getOrderStatus, DELETED_STATUS).update();
    }

    @Override
    @Transactional
    public PageInfo<OrderVO> queryPage(int pageNum, int pageSize, QueryOrderParam param) {
        try (Page<Order> page = PageHelper.startPage(pageNum, pageSize)) {
            return page.doSelectPageInfo(() -> {
                var query = lambdaQuery()
                        .eq(Objects.nonNull(param.getOrderId()), Order::getOrderId, param.getOrderId())
                        .eq(Objects.nonNull(param.getCustomerId()), Order::getCustomerId, param.getCustomerId())
                        .like(Objects.nonNull(param.getOrderTitle()), Order::getOrderTitle, param.getOrderTitle())
                        .like(Objects.nonNull(param.getOrderContent()), Order::getOrderContent, param.getOrderContent())

                        .le(Objects.nonNull(param.getOrderBudgetMax()), Order::getOrderBudget, param.getOrderBudgetMax())
                        .ge(Objects.nonNull(param.getOrderBudgetMin()), Order::getOrderBudget, param.getOrderBudgetMin())

                        .le(Objects.nonNull(param.getOrderDeadlineMax()), Order::getOrderDeadline, param.getOrderDeadlineMax())
                        .ge(Objects.nonNull(param.getOrderDeadlineMin()), Order::getOrderDeadline, param.getOrderDeadlineMin())

                        .le(Objects.nonNull(param.getOrderCompleteMax()), Order::getOrderComplete, param.getOrderCompleteMax())
                        .ge(Objects.nonNull(param.getOrderCompleteMin()), Order::getOrderComplete, param.getOrderCompleteMin())

                        .le(Objects.nonNull(param.getOrderSumMax()), Order::getOrderSum, param.getOrderSumMax())
                        .ge(Objects.nonNull(param.getOrderSumMin()), Order::getOrderSum, param.getOrderSumMin())
                        .orderByDesc(Order::getOrderId);
                if (Objects.nonNull(param.getOrderStatus())) {
                    query.eq(Order::getOrderStatus, param.getOrderStatus());
                } else {
                    query.ne(Order::getOrderStatus, DELETED_STATUS);
                }
                query.list();
            });
        }
    }
}
