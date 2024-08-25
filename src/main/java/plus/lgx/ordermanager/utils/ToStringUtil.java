package plus.lgx.ordermanager.utils;

import cn.hutool.extra.spring.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Class name: ToStringUtil
 * Create time: 2024/1/8 10:41
 *
 * @author lgx
 * @version 1.0
 */

public class ToStringUtil {

    private static final int maxLength =
            Integer.parseInt(Optional.ofNullable(SpringUtil.getProperty("fgw.log.max-length")).orElse("200"));

    private static final Map<Class<?>, Meta> map = new HashMap<>();

    public static String toString(Object o) {
        if (o == null)
            return "null";

        if (o.getClass().isArray()) {
            Class<?> componentType = o.getClass().getComponentType();
            return componentType.isPrimitive()
                    ? toPrimitiveArrayString(o)
                    : toString((Object[]) o);
        }
        if (o instanceof Collection)
            return toString((Collection<?>) o);

        String s = o.toString();
        return s.length() > maxLength
                ? s.substring(0, maxLength - 4) + "..."
                : s;
    }

    public static String toStringNoProperties(Object o) {
        if (o == null)
            return "null";

        if (o.getClass().isArray()) {
            Class<?> componentType = o.getClass().getComponentType();
            return componentType.isPrimitive()
                    ? toPrimitiveArrayStringNoProperties(o)
                    : toStringNoProperties((Object[]) o);
        }
        if (o instanceof Collection)
            return toStringNoProperties((Collection<?>) o);

        String s = o.toString();
        return s.length() > maxLength
                ? s.substring(0, maxLength - 4) + "..."
                : s;
    }

    public static String toString(Collection<?> collection) {
        return toString(collection.toArray());
    }

    public static String toStringNoProperties(Collection<?> collection) {
        return toStringNoProperties(collection.toArray());
    }

    /**
     * <h3>生成一个数组的描述字符串</h3>
     * 该方法用于生成一个数组的描述字符串。根据数组的不同情况，会生成不同形式的字符串。以下分情况说明：
     * <ol>
     *     <li>当数组为 null 时，该方法直接返回字符串 <i>"null"</i>；</li>
     *     <li>当数组的长度为 0 时，该方法直接返回字符串 <i>"[]"</i>；</li>
     *     <li>若数组中所有元素均为 null 则会生成形如 <i>{size:数组长度}[&lt;null...>]</i> 的字符串作为该数组的描述字符串并返回。</li>
     *     <li>若数组不在以上情况之中，则该方法会寻找数组的第一个非 null 元素，使用其类型作为数组的元素的类型，然后遍历数组判断是否所
     *     有元素的类型都与其相同。若是，则将数组中元素的类型提取出来，生成形如 <i>元素类型名称(各项属性){size:数组长度}[(各项属性的值),
     *     (各项属性的值),...]</i> 的字符串作为该数组的描述字符串并返回；否则按第 5 种情况处理。</li>
     *     <li>若数组并非所有元素类型一致，则按标准的 toString 方式生成，即生成形如 <i>[元素.toString(),元素.toString(),...]</i> 的
     *     字符串并返回。</li>
     * </ol>
     * 不论何种情况，生成的字符串会有长度限制，该限制由 maxLength 属性决定，默认为 200。当字符串的长度超过了限制，则会截取前
     * maxLength - 4 个字符并拼接上 “..."再返回。另外，当无法获取元素类型的 get 方法时也会按照第 5 种情况处理。
     * @param a 待生成描述字符串的数组
     * @return 生成的描述传入的数组参数的字符串
     */
    public static String toString(Object[] a) {
        return toString0(a, true);
    }

    private static String toStringNoProperties(Object[] a) {
        return toString0(a, false);
    }

    private static String toString0(Object[] a, boolean withProperties) {
        if (a == null) return "null";

        if (a.length == 0) return "[]";

        Class<?> clazz = Arrays.stream(a).filter(Objects::nonNull).findFirst().map(Object::getClass).orElse(null);
        if (Objects.isNull(clazz)) return "{size:" + a.length + "}[<null...>]";
        for (Object o : a) {
            if (Objects.nonNull(o) && !o.getClass().equals(clazz)) return generateString(a);
        }

        if (Number.class.isAssignableFrom(clazz)) {
            return toPrimitiveArrayString(a, clazz.getSimpleName());
        }

        Meta meta = map.get(clazz);
        if (Objects.isNull(meta))
            map.put(clazz, meta = getMeta(clazz));
        return Meta.UNREACHABLE.equals(meta)
                ? generateString(a)
                : withProperties
                ? generateString(a, meta)
                : generateStringNoProperties(a, meta);
    }

    private static Meta getMeta(Class<?> clazz) {
        if (Collection.class.isAssignableFrom(clazz)) {
            return Meta.UNREACHABLE;
        }
        StringJoiner sj = new StringJoiner(",", clazz.getSimpleName() + "(", ")");

        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            return Meta.UNREACHABLE;
        }

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors.length == 0) {
            return new Meta(sj.toString(), new Method[0]);
        }

        Method[] readMethods = new Method[propertyDescriptors.length];
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            if (descriptor.getName().equals("class")) continue;
            readMethods[i] = descriptor.getReadMethod();

            String name = descriptor.getName();
            Class<?> propertyType = descriptor.getPropertyType();
            if (propertyType.isArray()) {
                Class<?> componentType = propertyType.getComponentType();
                Meta meta;
                map.putIfAbsent(componentType, meta = map.getOrDefault(componentType, getMeta(componentType)));
                sj.add(name + "=" + meta.name);
                continue;
            }
            sj.add(name);
        }

        if (Objects.isNull(readMethods[readMethods.length - 1])) {
            for (int i = readMethods.length - 2; i >= 0; i--) {
                if (Objects.nonNull(readMethods[i])) {
                    readMethods[readMethods.length - 1] = readMethods[i];
                    readMethods[i] = null;
                    break;
                }
            }
        }

        return new Meta(sj.toString(), readMethods);
    }

    private static String generateString(Object[] a, Meta meta) {
        return generateString0(a, meta, new StringBuilder(meta.name));
    }

    private static String generateStringNoProperties(Object[] a, Meta meta) {
        return generateString0(a, meta, new StringBuilder());
    }

    private static String generateString0(Object[] a, Meta meta, StringBuilder b) {
        b.append("{size:").append(a.length).append("}[");
        for (int i = 0, iMax = a.length - 1; ; i++) {
            Object obj = a[i];
            if (obj instanceof Number) {
                b.append(obj);
                if (i == iMax) return b.append(']').toString();
                b.append(',');
                continue;
            }
            if (obj instanceof Collection) {
                b.append(toString((Collection<?>) obj));
                if (i == iMax) return b.append(']').toString();
                b.append(',');
                continue;
            }

            b.append('(');
            Method[] readMethods = meta.readMethods;
            for (int j = 0, jMax = readMethods.length - 1; j <= jMax; j++) {
                Method method = readMethods[j];
                if (Objects.isNull(method)) continue;
                try {
                    b.append(toStringNoProperties(method.invoke(obj)));
                    if (j == jMax) break;
                    b.append(',');
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            b.append(')');
            if (b.length() > maxLength) return b.substring(0, maxLength - 4) + "...";
            if (i == iMax) return b.append(']').toString();
            b.append(',');
        }
    }

    private static String generateString(Object[] a) {
        StringBuilder b = new StringBuilder();
        b.append("{size:").append(a.length).append("}[");

        for (int i = 0, iMax = a.length - 1; ; i++) {
            b.append(toString(a[i]));

            if (b.length() > maxLength)
                return b.substring(0, maxLength - 4) + "...";
            if (i == iMax)
                return b.append(']').toString();

            b.append(',');
        }
    }

    public static String toPrimitiveArrayString(Object a) {
        String type = a.getClass().getComponentType().getSimpleName();
        return type + toPrimitiveArrayStringNoProperties(a);
    }

    public static String toPrimitiveArrayString(Object a, String type) {
        return type + toPrimitiveArrayStringNoProperties(a);
    }

    public static String toPrimitiveArrayStringNoProperties(Object a) {
        if (Objects.isNull(a))
            return "null";

        int length = Array.getLength(a);
        if (length == 0)
            return "[]";

        StringBuilder b = new StringBuilder();
        if (length > 5) b.append("{size:").append(length).append("}["); else b.append('[');
        for (int i = 0, iMax = length - 1; ; i++) {
            b.append(Array.get(a, i));

            if (b.length() > maxLength)
                return b.substring(0, maxLength - 4) + "...";
            if (i == iMax)
                return b.append(']').toString();

            b.append(',');
        }
    }

    private static class Meta {

        final String name;
        final Property[] properties;

        final Method[] readMethods;

        Meta(String name, Property[] properties) {
            this.name = name;
            this.readMethods = null;
            this.properties = properties;
        }

        Meta(String name, Method[] readMethods) {
            this.name = name;
            this.properties = null;
            this.readMethods = readMethods;
        }

        final static Meta UNREACHABLE = new Meta(null, (Property[]) null);

//        private String propertiesString;
/*
        String getProperties() {
            if (Objects.isNull(propertiesString)) {
                boolean hasMeta = false;
                for (Property /property : properties) {
                    if (Objects.nonNull(property.meta)) {
                        hasMeta = true;
                        break;
                    }
                }

                propertiesString = (hasMeta ? "" : name) + getPropertiesString();
            }
            return propertiesString;
        }

        private String getPropertiesString() {
            int iMax = properties.length - 1;
            if (iMax == -1)
                return "";

            StringBuilder b = new StringBuilder();
            b.append('(');
            for (int i = 0; ; i++) {
                b.append(properties[i]);
                if (i == iMax)
                    return b.append(')').toString();
                b.append(",");
            }
        }*/

    }

    private static class Property {
        final Method readMethod;

        final Meta meta;

        Property(Method readMethod, Meta meta) {
            this.readMethod = readMethod;
            this.meta = meta;
        }

        @Override
        public String toString() {
            char[] charArray = this.readMethod.getName().toCharArray();
            charArray[3] += 97 - 65;
            return new String(charArray, 3, charArray.length - 3);
        }
    }

}
