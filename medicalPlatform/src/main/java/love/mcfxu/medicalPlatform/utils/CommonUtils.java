package love.mcfxu.medicalPlatform.utils;

import com.github.pagehelper.PageInfo;
import love.mcfxu.medicalPlatform.domain.model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommonUtils {

    /**
     * 随机生成uuid
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "")
                .substring(0, 32);
    }

    /**
     * MD5加密
     * @param data
     * @return
     */
    public static String MD5(String data)  {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;

    }








    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null ||
                    ipAddress.length() == 0 ||
                    "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress =
                        request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null ||
                    ipAddress.length() == 0 ||
                    "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress =
                        request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null ||
                    ipAddress.length() == 0 ||
                    "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress =
                        request.getRemoteAddr();
                if
                (ipAddress.equals("127.0.0.1")) {
                    // 根据⽹卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet =
                                InetAddress.getLocalHost();
                    } catch
                    (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress =
                            inet.getHostAddress();
                }
            }

            if (ipAddress != null &&
                    ipAddress.length() > 15) {

                if (ipAddress.indexOf(",") > 0)
                {
                    ipAddress =
                            ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }


    public static <T> Object sortPage(PageInfo<T> sheet){
        Map<String, Object> d = new HashMap<>();
        d.put("total_size", sheet.getTotal());
        d.put("total_page", sheet.getPages());
        d.put("current_page", sheet.getPageNum());
        d.put("data", sheet.getList());
        return d;
    }
}
