package love.mcfxu.medicalPlatform.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 简单java对象转json
     * @param data
     * @return
     */
    public static String objectToJson(Object data){
        try {

            return MAPPER.writeValueAsString(data);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * json转简单java对象
     * @param jsonData
     * @param beanType
     * @param <T>
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType){

        try {
            T t = MAPPER.readValue(jsonData,beanType);
            return t;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;


    }
}
