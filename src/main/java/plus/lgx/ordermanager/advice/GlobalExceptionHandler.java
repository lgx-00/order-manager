package plus.lgx.ordermanager.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.exc.InputCoercionException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import plus.lgx.ordermanager.entity.vo.R;

import java.util.List;
import java.util.Objects;

/**
 * 全局异常捕获器
 * @author lgx
 * 2023/4/15 21:51
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * <p>服务器异常</p>
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public R<?> e(Exception e) {
        log.error("【全局异常处理】发生未处理异常", e);
        return R.internalServerError();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public R<?> methodNotAllow() {
        return R.fail(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<?> error(HttpMessageNotReadableException e) {
        Throwable ex = e.getCause();
        Class<? extends Throwable> exClass = Objects.isNull(ex) ? null : ex.getClass();

        if (InvalidFormatException.class.equals(exClass)) {
            InvalidFormatException ife = (InvalidFormatException) ex;
            Class<?> targetType = ife.getTargetType();
            if (Integer.class.equals(targetType)) {
                JsonMappingException.Reference ref = ife.getPath().get(ife.getPath().size() - 1);
                Class<?> model = ref.getFrom().getClass();
                String fieldName = ref.getFieldName();

                String propName = fieldName != null ? fieldName : "unknown";
                log.warn("【全局异常处理】无法通过分析给定的字符串得到 32 位整型数值。给定的字符串为 \"{}\"，" +
                        "数据绑定模型为 {}，字段名称为 {}, 模型字段名称为 {}。", ife.getValue(),
                        model, fieldName, propName, e);
                return R.fail(String.format("“%s”的内容不合法，请输入正确的整数。", propName));
            }
            log.warn("【全局异常处理】无法分析字符串 \"{}\" 得到 \"{}\" 类型的值。", ife.getValue(), targetType.getName(), e);
        }

        if (JsonMappingException.class.equals(exClass)) {
            JsonMappingException jme = (JsonMappingException) ex;

            JsonMappingException.Reference ref = jme.getPath().get(jme.getPath().size() - 1);
            String msg = String.format("【全局异常处理】“%s”的内容不合法。", ref.getFieldName());
            log.warn(msg, e);
            return R.fail(msg);
        }

        if (Objects.isNull(exClass) || InputCoercionException.class.equals(exClass)
                || JsonParseException.class.equals(exClass)) {
            log.warn("【全局异常处理】请求参数无法识别。", e);
        } else {
            log.warn("【全局异常处理】未知异常。", e);
        }
        return R.fail("请求参数无法识别");
    }

    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<?> error(MissingServletRequestParameterException e) {
        log.warn("【全局异常处理】缺少必要参数。", e);
        return R.fail("缺少必要参数");
    }

    /**
     * spring 封装的参数验证异常， 在controller中没有写BindingResult(实际开发不常用)参数时，会进入
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HandlerMethodValidationException.class,
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public R<?> methodArgumentNotValidException(Exception ex) {
        BindingResult bindingResult = null;
        if (ex instanceof BindException) {
            bindingResult = ((BindException) ex).getBindingResult();
        }

        if (bindingResult == null) {
            log.warn("【全局异常处理】参数校验失败，错误信息：{}", ex.getMessage());
            return R.fail(ex.getMessage());
        }

        List<ObjectError> errors = bindingResult.getAllErrors();
        Object target = bindingResult.getTarget();
        String className = target != null ? target.getClass().getSimpleName() : null;
        if (errors.isEmpty()) {
            log.warn("【全局异常处理】参数校验失败，对象名称：{}，对象类型：{}",
                    bindingResult.getObjectName(), className);
            return R.fail("部分参数不合法");
        }
        ObjectError objectError = errors.get(0);
        String messages = objectError.getDefaultMessage();
        log.warn("【全局异常处理】参数校验失败，对象名称：{}，对象类型：{}，错误信息：{}",
                bindingResult.getObjectName(), className, messages);
        if (messages != null && messages.length() > 50) {
            return R.fail("部分参数不合法");
        }
        return R.fail(messages);
    }

}
