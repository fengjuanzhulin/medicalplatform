package love.mcfxu.medicalPlatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("love.mcfxu.medicalPlatform.mapper")
@EnableTransactionManagement
//@EnableSwagger2
public class MedicalPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalPlatformApplication.class, args);
	}

}
