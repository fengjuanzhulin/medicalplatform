package love.mcfxu.medicalPlatform.controller;

import com.google.code.kaptcha.Producer;
import love.mcfxu.medicalPlatform.utils.CommonUtils;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
@RestController
@RequestMapping("pub/captcha")
public class CaptchaController {

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 建带有文本的验证码图像
     */
    @Autowired
    Producer captchaProducer;


    /**
     * 获取图形验证码
     * @param request
     * @param response
     */

    @GetMapping("get_captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){

        String captchaText = captchaProducer.createText();

        String key = getCaptchaKey(request);

        redisTemplate.opsForValue().set(key,captchaText,7,TimeUnit.MINUTES);

        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);

        ServletOutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            ImageIO.write(bufferedImage,"jpg",outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 验证码校验
     * @param to
     * @param captcha
     * @param request
     * @return
     */
    @GetMapping("check_code")
    public JsonData sendCode(@RequestParam(value = "to",required = true)String to,
                             @RequestParam(value = "captcha",required = true) String captcha,
                             HttpServletRequest request){

        String key = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(key);

        if(captcha!=null && cacheCaptcha!=null && cacheCaptcha.equalsIgnoreCase(captcha)){
            redisTemplate.delete(key);

            return JsonData.buildSuccess();
        }else {
            return JsonData.buildError("验证码错误");
        }
    }


    /**
     * 获取缓存的key
     * @param request
     * @return
     */
    public String getCaptchaKey(HttpServletRequest request){
        String ip = CommonUtils.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "user-service:captcha:"+CommonUtils.MD5(ip+userAgent);
        return key;
    }
}
