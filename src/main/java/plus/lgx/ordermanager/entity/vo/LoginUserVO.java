package plus.lgx.ordermanager.entity.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 类型：LoginUserVO
 *
 * @author lgx
 * @since 2024/8/24
 */
@Data
@Valid
public class LoginUserVO {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String userPassword;

    private Long ip;

}
