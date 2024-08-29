package plus.lgx.ordermanager.controller;


import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import plus.lgx.ordermanager.entity.pojo.TechTag;
import plus.lgx.ordermanager.entity.vo.R;
import plus.lgx.ordermanager.entity.vo.TechTagVO;
import plus.lgx.ordermanager.service.TechTagService;
import plus.lgx.ordermanager.utils.UserHolder;

import java.util.List;

import static plus.lgx.ordermanager.constant.SystemConstant.*;

/**
 * <p>
 * 技术栈标签 前端控制器
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@RestController
@RequestMapping("/tech-tag")
public class TechTagController {

    @Resource
    private TechTagService techTagService;

    @GetMapping
    public R<List<TechTag>> getAllTag() {
        List<TechTag> list = techTagService.lambdaQuery()
                .ne(TechTag::getTagStatus, DELETED_STATUS)
                .orderByDesc(TechTag::getTagId).list();
        return R.ok(list);
    }

    @PostMapping
    public R<Void> postTag(@RequestBody @Valid TechTagVO techTag) {
        techTag.setTagCreatedBy(UserHolder.getUser().getUserId());
        return techTagService.save(techTag) ? R.ok() : R.fail(SAVE_FAILED);
    }

    @PutMapping
    public R<Void> putTag(@RequestBody @Valid TechTagVO techTag) {
        return techTagService.updateById(techTag) ? R.ok() : R.fail(UPDATE_FAILED);
    }

    @DeleteMapping("/{id}")
    public R<Void> delTag(@PathVariable Integer id) {
        return techTagService.lambdaUpdate()
                .set(TechTag::getTagStatus, DELETED_STATUS)
                .eq(TechTag::getTagId, id).update()
                ? R.ok() : R.fail(DELETE_FAILED);
    }

}
