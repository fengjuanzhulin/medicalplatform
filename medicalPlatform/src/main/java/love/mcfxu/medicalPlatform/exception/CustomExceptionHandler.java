package love.mcfxu.medicalPlatform.exception;


import love.mcfxu.medicalPlatform.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handle(Exception e){

        logger.error("[ 系统异常 ]{}",e.getMessage());

        if( e instanceof Exception ){

            Exception fyException = e;

            return JsonData.buildError(fyException.getMsg(),fyException.getCode());

        }else {

            return JsonData.buildError("全局异常，未知错误");

        }


    }
}
