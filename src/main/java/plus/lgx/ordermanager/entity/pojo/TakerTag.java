package plus.lgx.ordermanager.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 接单员技术标签
 * </p>
 *
 * @author lgx
 * @since 2024-08-25
 */
@Getter
@Setter
@TableName("taker_tag")
public class TakerTag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 接单员编号
     */
    private Long takerId;

    /**
     * 技术标签编号
     */
    private Long tagId;


}
