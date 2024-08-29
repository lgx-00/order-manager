package plus.lgx.ordermanager.service.impl;

import plus.lgx.ordermanager.entity.pojo.Customer;
import plus.lgx.ordermanager.mapper.CustomerMapper;
import plus.lgx.ordermanager.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
