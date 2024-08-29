package plus.lgx.ordermanager.controller;


import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import plus.lgx.ordermanager.entity.vo.OrderVO;
import plus.lgx.ordermanager.entity.vo.QueryOrderParam;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.service.OrderService;
import plus.lgx.ordermanager.utils.UserHolder;

import static plus.lgx.ordermanager.constant.SystemConstant.DELETE_FAILED;
import static plus.lgx.ordermanager.constant.SystemConstant.UPDATE_FAILED;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping
    public R<Void> postOrder(@RequestBody OrderVO order) {
        order.setOrderCreatedBy(UserHolder.getUser().getUserId());
        orderService.save(order);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delOrder(@PathVariable Integer id) {
        return orderService.del(id)
                ? R.ok()
                : R.fail(DELETE_FAILED);
    }

    @PutMapping
    public R<Void> putOrder(@RequestBody OrderVO order) {
        return orderService.updateById(order)
                ? R.ok()
                : R.fail(UPDATE_FAILED);
    }

    @GetMapping
    public R<PageInfo<OrderVO>>
    getOrderPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @ModelAttribute QueryOrderParam param
    ) {
        return R.ok(orderService.queryPage(pageNum, pageSize, param));
    }

}

