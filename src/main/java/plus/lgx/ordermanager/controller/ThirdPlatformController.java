package plus.lgx.ordermanager.controller;


import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import plus.lgx.ordermanager.entity.pojo.ThirdPlatform;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.service.ThirdPlatformService;

import java.util.List;

import static plus.lgx.ordermanager.constant.SystemConstant.*;

/**
 * <p>
 * 第三方平台 前端控制器
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@RestController
@RequestMapping("/third-platform")
public class ThirdPlatformController {

    @Resource
    private ThirdPlatformService thirdPlatformService;

    @GetMapping
    public R<List<ThirdPlatform>> getAllThirdPlatform() {
        return R.ok(thirdPlatformService.lambdaQuery()
                .ne(ThirdPlatform::getPlatformStatus, DELETED_STATUS)
                .orderByDesc(ThirdPlatform::getPlatformId).list());
    }

    @PostMapping
    public R<Void> postPlatform(@RequestBody @Valid ThirdPlatform thirdPlatform) {
        return thirdPlatformService.save(thirdPlatform) ? R.ok() : R.fail(SAVE_FAILED);
    }

    @PutMapping
    public R<Void> putPlatform(@RequestBody @Valid ThirdPlatform platform) {
        return thirdPlatformService.updateById(platform) ? R.ok() : R.fail(UPDATE_FAILED);
    }

    @DeleteMapping("{id}")
    public R<Void> delPlatform(@PathVariable Long id) {
        return thirdPlatformService.lambdaUpdate()
                .set(ThirdPlatform::getPlatformStatus, DELETED_STATUS)
                .eq(ThirdPlatform::getPlatformId, id).update()
                ? R.ok() : R.fail(UPDATE_FAILED);
    }

}

