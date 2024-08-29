package plus.lgx.ordermanager.service.impl;

import plus.lgx.ordermanager.entity.pojo.Payment;
import plus.lgx.ordermanager.mapper.PaymentMapper;
import plus.lgx.ordermanager.service.PaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {

}
