package plus.lgx.ordermanager.utils;

import cn.hutool.core.net.Ipv4Util;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;


/**
 * Class name: IPUtil
 * Create time: 2023/8/30 20:30
 *
 * @author lgx
 * @version 1.0
 */
public class IPUtil {

    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            return index != -1 ? ip.substring(0, index) : ip;
        } else {
            return request.getRemoteAddr();
        }
    }

    public static long toLongIp(String ip) {
        return Ipv4Util.ipv4ToLong(ip, 0);
    }

}
