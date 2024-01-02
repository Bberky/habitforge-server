package cz.cvut.fit.tjv.habitforgeserver.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Habit Forge API", version = "1.0", description = "Habit Forge API"))
@SecurityScheme(scheme = "basic", type = SecuritySchemeType.HTTP, name = "basicAuth")
@SecurityRequirement(name = "basicAuth")
public class OpenAPIConfiguration {

}
