package love.mcfxu.medicalPlatform;

import love.mcfxu.medicalPlatform.utils.CommonUtils;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class Test {

    @org.junit.jupiter.api.Test
    public void test(){
        Logger logger = LoggerFactory.getLogger(this.getClass());

        Logger dataLogger = LoggerFactory.getLogger("dataLogger");

        logger.error("woaini");

        dataLogger.error("woyeaini");

    }



    private Object doubleMD5(String pwd){
        String hashName = "md5";
        Object result = new SimpleHash(hashName, pwd, null, 2);
        return result;
    }

    @org.junit.jupiter.api.Test
    public void MD5(){
        Logger dataLogger = LoggerFactory.getLogger("dataLogger");
        Object o = doubleMD5("666");
        dataLogger.error(o.toString());


        //597042ee35c3a0a937c9ef4afff842f2

    }

}
