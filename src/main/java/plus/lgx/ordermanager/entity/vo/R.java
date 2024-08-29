package plus.lgx.ordermanager.entity.vo;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import plus.lgx.ordermanager.utils.ToStringUtil;

import java.io.Serial;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;

import static plus.lgx.ordermanager.constant.SystemConstant.*;

/**
 * 类型：R
 *
 * @author lgx
 * @since 2024/8/24
 */
@SuppressWarnings("unused")
@Getter
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer code;
    private final String message;
    private final T data;

    private R(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private static final R<Void> OK = new R<>(RESPONSE_CODE_OK, null, null);
    private static final R<Void> INTERNAL_SERVER_ERROR =
            new R<>(RESPONSE_CODE_FAIL, RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR, null);
    private static final R<Void> UNAUTHORIZED =
            new R<>(RESPONSE_CODE_UNAUTHORIZED, RESPONSE_MESSAGE_UNAUTHORIZED, null);

    @SuppressWarnings("unchecked")
    public static <T> R<T> ok() {
        return (R<T>) OK;
    }

    @SuppressWarnings("unchecked")
    public static <T> R<T> internalServerError() {
        return (R<T>) INTERNAL_SERVER_ERROR;
    }

    @SuppressWarnings("unchecked")
    public static <T> R<T> unauthorized() {
        return (R<T>) UNAUTHORIZED;
    }

    public static <T> R<T> ok(T data) {
        return new R<>(RESPONSE_CODE_OK, null, data);
    }

    public static <T> R<T> ok(String msg) {
        return new R<>(RESPONSE_CODE_OK, msg, null);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(RESPONSE_CODE_OK, msg, data);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(RESPONSE_CODE_FAIL, msg, null);
    }

    public static <T> R<T> fail(int errorCode, String msg) {
        return new R<>(errorCode, msg, null);
    }

    public static <T> R<T> fail(int errorCode, String msg, T data) {
        return new R<>(errorCode, msg, data);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String toString() {
        String fmt = "R(code=" + code + ", msg=" + message + ", data=";
        if (data instanceof List) {
            Object[] array = ((List<?>) data).toArray();
            return fmt + ToStringUtil.toString(array) + ')';
        }
        if (data instanceof PageInfo) {
            return fmt +
                    "PageInfo{pageNum=" + ((PageInfo<?>) data).getPageNum() +
                    ", pageSize=" + ((PageInfo<?>) data).getPageSize() +
                    ", total=" + ((PageInfo<?>) data).getTotal() +
                    ", list=" + new MockList(((PageInfo<?>) data).getList()) + "})";
        }
        return fmt + ToStringUtil.toString(data) + ')';
    }

    @SuppressWarnings("unchecked")
    private static class MockList<T> extends AbstractList<T> {

        private final Object[] elements;

        public MockList(List<T> list) {
            elements = list.toArray();
        }

        @Override
        public T get(int index) {
            return (T) elements[index];
        }

        @Override
        public int size() {
            return elements.length;
        }

        @Override
        public String toString() {
            return ToStringUtil.toString(elements);
        }
    }
}
