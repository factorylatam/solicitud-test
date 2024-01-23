package pe.gob.sunarp.app.solicitud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableCaching
@EnableResourceServer
public class SolicitudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolicitudApplication.class, args);
	}
	
	@Bean	
	public WebMvcConfigurer corsConfigurer() {
			
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("HEAD","OPTIONS")
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
				 .allowedOrigins("http://localhost:4200")
				 .allowedMethods("*").allowedHeaders("*");
			}
			
			
		};

	}

}
