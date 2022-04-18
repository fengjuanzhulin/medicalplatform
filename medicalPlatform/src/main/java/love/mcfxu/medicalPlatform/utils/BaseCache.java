package love.mcfxu.medicalPlatform.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BaseCache {



    private Cache<String,Object> twoMinuteCache = CacheBuilder.newBuilder()

            //设置缓存初始大小，应该合理设置，后续会扩容
            .initialCapacity(10)
            //最大值
            .maximumSize(100)
            //并发数设置
            .concurrencyLevel(5)

            //缓存过期时间，写入后2分钟过期
            .expireAfterWrite(120,TimeUnit.SECONDS)

            //统计缓存命中率
            .recordStats()

            .build();


    private Cache<String,Object> twentySecondCache = CacheBuilder.newBuilder()


            .initialCapacity(10)

            .maximumSize(100)

            .concurrencyLevel(5)


            .expireAfterWrite(20,TimeUnit.SECONDS)


            .recordStats()

            .build();



    private Cache<String,Object> oneHourCache = CacheBuilder.newBuilder()


            .initialCapacity(30)

            .maximumSize(100)

            .concurrencyLevel(5)


            .expireAfterWrite(3600,TimeUnit.SECONDS)


            .recordStats()

            .build();





    public Cache<String, Object> getOneHourCache() {
        return oneHourCache;
    }

    public void setOneHourCache(Cache<String, Object> oneHourCache) {
        this.oneHourCache = oneHourCache;
    }

    public Cache<String, Object> getTwoMinuteCache() {
        return twoMinuteCache;
    }

    public void setTwoMinuteCache(Cache<String, Object> twoMinuteCache) {
        this.twoMinuteCache = twoMinuteCache;
    }

    public Cache<String, Object> getTwentySecondCache() {
        return twentySecondCache;
    }

    public void setTwentySecondCache(Cache<String, Object> twentySecondCache) {
        this.twentySecondCache = twentySecondCache;
    }
}
