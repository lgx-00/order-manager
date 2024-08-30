package plus.lgx.ordermanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plus.lgx.ordermanager.entity.pojo.Order;
import plus.lgx.ordermanager.entity.pojo.OrderTag;
import plus.lgx.ordermanager.entity.pojo.TechTag;
import plus.lgx.ordermanager.entity.vo.OrderVO;
import plus.lgx.ordermanager.entity.vo.QueryOrderParam;
import plus.lgx.ordermanager.mapper.OrderMapper;
import plus.lgx.ordermanager.service.OrderService;
import plus.lgx.ordermanager.service.OrderTagService;
import plus.lgx.ordermanager.service.TechTagService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private OrderTagService orderTagService;

    @Resource
    private TechTagService techTagService;

    @Override
    public boolean del(Integer id) {
        return lambdaUpdate().eq(Order::getOrderId, id).set(Order::getOrderStatus, DELETED_STATUS).update();
    }

    @Override
    @Transactional
    public void saveOrder(OrderVO order) {
        if (!save(order)) {
            throw new RuntimeException();
        }
        saveOrderTags(order);
    }

    @Override
    public void updateOrder(OrderVO order) {
        if (!updateById(order)) {
            throw new RuntimeException();
        }
        if (!orderTagService.lambdaUpdate().eq(OrderTag::getOrderId, order.getOrderId()).remove()) {
            throw new RuntimeException();
        }
        saveOrderTags(order);
    }

    private void saveOrderTags(OrderVO order) {
        Long orderId = order.getOrderId();
        List<OrderTag> orderTagList = CollectionUtil.emptyIfNull(order.getOrderTag())
                .stream().map(tagId -> new OrderTag(orderId, tagId)).toList();
        if (!orderTagService.saveBatch(orderTagList)) {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
    public PageInfo<OrderVO> queryPage(int pageNum, int pageSize, QueryOrderParam param) {

        try (Page<Order> page = PageHelper.startPage(pageNum, pageSize)) {
            PageInfo<Order> pageInfo = page.doSelectPageInfo(() -> baseMapper.queryByCondition(param));

            List<Long> orderIds = pageInfo.getList().stream().map(Order::getOrderId).toList();
            List<OrderTag> allMiddleTags = orderIds.isEmpty()
                    ? Collections.emptyList()
                    : orderTagService.lambdaQuery().in(OrderTag::getOrderId, orderIds).list();

            List<Long> allTagIds = allMiddleTags.stream().map(OrderTag::getTagId).distinct().toList();
            Map<Long, TechTag> allTechTags = allTagIds.isEmpty()
                    ? Collections.emptyMap()
                    : techTagService.lambdaQuery().in(TechTag::getTagId, allTagIds).list().stream()
                    .collect(Collectors.toMap(TechTag::getTagId, x -> x));

            Map<Long, List<String>> nameGroup = allMiddleTags.stream()
                    .collect(Collectors.groupingBy(OrderTag::getOrderId,
                            Collectors.mapping(tag -> allTechTags.get(tag.getTagId()).getTagName(),
                                    Collectors.toList())));
            Map<Long, List<Long>> idGroup = allMiddleTags.stream()
                    .collect(Collectors.groupingBy(OrderTag::getOrderId,
                            Collectors.mapping(OrderTag::getTagId,
                                    Collectors.toList())));

            List<OrderVO> list = pageInfo.getList().stream().map(order -> {
                OrderVO orderVO = new OrderVO(order);
                orderVO.setTags(nameGroup.get(order.getOrderId()));
                orderVO.setOrderTag(idGroup.get(order.getOrderId()));
                return orderVO;
            }).toList();

            PageInfo<OrderVO> ret = new PageInfo<>();
            BeanUtil.copyProperties(pageInfo, ret);
            ret.setList(list);
            return ret;
        }
    }
}
