package love.mcfxu.medicalPlatform.config;


/**
 * 缓存key管理类
 */
public class CacheKeyManager {

    /**
     * 用户列表缓存key
     */
    public static final String USER_LIST_KEY = "admin:user:list";

    /**
     * 健康资讯缓存key
     */
    public static final String PUB_HEALTH_INFO="pub:health:info";

    /**
     * 平台简介缓存key
     */
    public static final String PUB_PLAT_INFO="pub:plat:info";


    /**
     * 诊断书缓存key, %s是诊断书id
     */
    public static final String DIA_DETAIL = "dia:detail:%s";


    /**
     * 挂号单缓存key
     */
    public static final String REGISTRATION_TABLE_ID = "registration:table:id";


}
