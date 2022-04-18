package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.HealthCommonSense;
import love.mcfxu.medicalPlatform.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface HealthCommonSenseMapper {

    @Select("select * from health_common_sense")
    @Options(useGeneratedKeys = true,keyColumn = "health_common_sense_id",keyProperty = "healthCommonSenseId")
    List<HealthCommonSense> findAllHealthCommonSense();

    @UpdateProvider(type = SqlProvider.class,method = "updateHealthCommonSense")
    int updateHealthCommonSense(HealthCommonSense healthCommonSense);

    @Delete("delete from health_common_sense where health_common_sense_id=#{healthCommonSenseId}")
    int delectHealthCommonSenseById(int healthCommonSenseId);

    @Insert("insert into `health_common_sense` (`author_name`,`topic`,`release_date`) values" +
            "(#{authorName},#{topic},#{releaseDate})")
    @Options(useGeneratedKeys = true,keyProperty = "healthCommonSenseId",keyColumn = "health_common_sense_id")
    int addHealthCommonSense(HealthCommonSense healthCommonSense);
}
