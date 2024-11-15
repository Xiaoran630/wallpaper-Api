package com.example.wallpaper.common.constant;

/**
 * &#064;Description:  通用常量
 * @author XiaoRan
 */
public interface CommonConstant {
    /**
     * {@code 500 Server Error} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /**
     * {@code 200 OK} (HTTP/1.0 - RFC 1945)
     */
    Integer SC_OK_200 = 200;

    String X_ACCESS_TOKEN = "X-Access-Token";
    String X_SIGN = "X-Sign";
    String X_TIMESTAMP = "X-TIMESTAMP";
    /**
     * 租户请求头 更名为：X-Tenant-Id
     */
    String TENANT_ID = "X-Tenant-Id";

    /**
     * POST请求
     */
    String HTTP_POST = "POST";

    /**
     * PUT请求
     */
    String HTTP_PUT = "PUT";

    /**
     * PATCH请求
     */
    String HTTP_PATCH = "PATCH";

    /**
     * 未知的
     */
    String UNKNOWN = "unknown";

    /**
     * 字符串http
     */
    String STR_HTTP = "http";

    /**
     * String 类型的空值
     */
    String STRING_NULL = "null";
}
