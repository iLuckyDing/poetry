package com.iashin.poetry.util;

import com.iashin.poetry.constants.CommonConstant;
import com.iashin.poetry.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 业务工具类
 * @date: 2024/7/11
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@Slf4j
public class PoetryUtil {

    /**
     * 将整个xdb文件加载到内存中(11M左右),此种创建方式支持多线程,因此只需要加载一次
     */
    private final static Searcher SEARCHER;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("ip2region.xdb");
            // 获取真实文件路径
            String path = resource.getURL().getPath();
            byte[] cBuff = Searcher.loadContentFromFile(path);
            SEARCHER = Searcher.newWithBuffer(cBuff);
            log.info("加载了ip2region.xdb文件,Searcher初始化完成!");
        } catch (Exception e) {
            log.error("初始化ip2region.xdb文件失败,报错信息:[{}]", e.getMessage(), e);
            throw new RuntimeException("系统异常!");
        }
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static void checkEmail() {
        /*
        User user = (User) PoetryCache.get(PoetryUtil.getToken());
        if (!StringUtils.hasText(user.getEmail())) {
            throw new PoetryRuntimeException("请先绑定邮箱！");
        }*/
    }

    public static String getToken() {
        String token = PoetryUtil.getRequest().getHeader(CommonConstant.TOKEN_HEADER);
        return "null".equals(token) ? null : token;
    }

    /**
     * 获取当前用户
     * @return user
     */
    public static User getCurrentUser() {
        // todo 缓存获取当前用户
        return null;
    }

    /**
     * 解析ip地址
     * @param ipStr 字符串类型ip 例:192.168.0.1
     * @return 返回结果形式(国家|区域|省份|城市|ISP) 例 [中国, 0, 河北省, 衡水市, 电信]
     */
    public static List<String> parse(String ipStr) {
        return parse(ipStr, null);
    }

    /**
     * 自定义解析ip地址
     * @param ipStr ip 字符串类型ip 例:1970753539(经过转换后的)
     * @param index 想要获取的区间 例如:只想获取 省,市 index = [2,3]
     * @return 返回结果例 [北京,北京市]
     */
    public static List<String> parse(String ipStr, int[] index) {
        try {
            long ip = Searcher.checkIP(ipStr);
            return parse(ip, index);
        } catch (Exception e) {
            log.error("ip解析为long错误,ipStr:[{}],错误信息:[{}]", ipStr, e.getMessage(), e);
            throw new RuntimeException("系统异常!");
        }
    }

    /**
     * 自定义解析ip地址
     * @param ip    ip Long类型ip
     * @param index 想要获取的区间 例如:只想获取 省,市 index = [2,3]
     * @return 返回结果例 [河北省, 衡水市]
     */
    public static List<String> parse(Long ip, int[] index) {
        //获取xdb文件资源
        List<String> regionList = new ArrayList<>();
        try {
            String region = SEARCHER.search(ip);
            String[] split = region.split("\\|");
            if (index == null) {
                regionList = Arrays.asList(split);
            } else {
                for (int i : index) {
                    regionList.add(split[i]);
                }
            }
            //关闭资源
            SEARCHER.close();
        } catch (Exception e) {
            log.error("根据ip解析地址失败,ip:[{}],index[{}],报错信息:[{}]", ip, index, e.getMessage(), e);
            throw new RuntimeException("系统异常!");
        }
        return regionList;
    }

    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ipAddress;
        try
        {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "unknown";
        }
        return ipAddress;
    }


    public static int hashLocation(String key, int length) {
        int h = key.hashCode();
        return (h ^ (h >>> 16)) & (length - 1);
    }

}
