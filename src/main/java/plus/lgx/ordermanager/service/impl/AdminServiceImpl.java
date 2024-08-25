package plus.lgx.ordermanager.service.impl;

import plus.lgx.ordermanager.entity.pojo.Admin;
import plus.lgx.ordermanager.mapper.AdminMapper;
import plus.lgx.ordermanager.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理人员 服务实现类
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
