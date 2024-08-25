package plus.lgx.ordermanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 技术栈标签
 * </p>
 *
 * @author lgx
 * @since 2024-08-24
 */
@Getter
@Setter
@TableName("tech_tag")
public class TechTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标签编号，主键
     */
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Long tagId;

    /**
     * 技术栈名称
     */
    @TableField("tag_name")
    private String tagName;

    /**
     * 用户编号，外键
     */
    @TableField("tag_created_user_id")
    private Long tagCreatedUserId;

    /**
     * 标签状态
     */
    @TableField("tag_status")
    private Integer tagStatus;


}
