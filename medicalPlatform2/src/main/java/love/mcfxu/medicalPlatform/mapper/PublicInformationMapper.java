package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.PublicInformation;
import love.mcfxu.medicalPlatform.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PublicInformationMapper {

    @Select("select * from public_information")
    List<PublicInformation> findAllPublicInformation();

    @UpdateProvider(type = SqlProvider.class,method = "updatePublicInformation")
    int updatePublicInformation(PublicInformation publicInformation);

    @Delete("delete from public_information where public_information_id=#{publicInformationId}")
    int deletePublicInformationById(int publicInformationId);

    @Insert("insert into `public_information` (`contact_number`,`summary`,`url`) values" +
            "(#{contactNumber},#{summary},#{url})")
    @Options(useGeneratedKeys = true,keyProperty = "public_information_id",keyColumn = "publicInformationId")
    int addPublicInformation(PublicInformation publicInformation);
}
