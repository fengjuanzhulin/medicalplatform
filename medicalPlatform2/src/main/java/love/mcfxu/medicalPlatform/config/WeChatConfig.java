package love.mcfxu.medicalPlatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 微信配置类
 */
@Configuration
@PropertySource(value= "classpath:application.yml")
public class WeChatConfig {


    /**
     * 公众号appid
     */
    @Value("${wxpay.appid}")
    private String appId;

    /**
     * 公众号秘钥
     */
    @Value("${wxpay.appsecret}")
    private String appSecret;


    /**
     * 开放平台appid
     */
    @Value("${wxopen.appid}")
    private String openAppid;

    /**
     * 开放平台appsecret
     */
    @Value("${wxopen.appsecret}")
    private String openAppSecret;


    /**
     * 开放平台回调url
     */
    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;


    /**
     * 微信开放平台二维码连接
     */
    private final static String OPEN_QRCODE_URL= "https://open.weixin." +
            "qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&r" +
            "esponse_type=code&scope=snsapi_login&state=%s#wechat_redirect";


    /**
     * 开放平台获取access_token地址
     */
    private final static String OPEN_ACCESS_TOKEN_URL="https://api.weixin.q" +
            "q.com/sns/oauth2/access_token?appid=%s&secret=%s" +
            "&code=%s&grant_type=authorization_code";


    /**
     * 获取用户信息
     */
    private final static String OPEN_USER_INFO_URL ="https://api.weixin.qq." +
            "com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";


    /**
     * 商户号id
     */
    @Value("${wxpay.mer_id}")
    private String mchId;


    /**
     * 支付key
     */
    @Value("${wxpay.key}")
    private String key;

    /**
     * 微信支付回调url
     */
    @Value("${wxpay.callback}")
    private String payCallbackUrl;


    /**
     * 统一下单url
     */
    //private static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";


    private static final String UNIFIED_ORDER_URL = "https://api.xdclass.net/pay/unifiedorder";

    public String getOpenAppid() {
        return openAppid;
    }

    public void setOpenAppid(String openAppid) {
        this.openAppid = openAppid;
    }

    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER_URL;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public static String getOpenUserInfoUrl() {
        return OPEN_USER_INFO_URL;
    }

    public static String getOpenAccessTokenUrl() {
        return OPEN_ACCESS_TOKEN_URL;
    }

    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }

    public String getOpenAppId() {
        return openAppid;
    }

    public void setOpenAppId(String openAppid) {
        this.openAppid = openAppid;
    }

    public String getOpenAppSecret() {
        return openAppSecret;
    }

    public void setOpenAppSecret(String openAppSecret) {
        this.openAppSecret = openAppSecret;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
