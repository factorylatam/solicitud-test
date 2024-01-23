package pe.gob.sunarp.app.solicitud.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class ConfiguraDataBase {

	private static Logger log = Logger.getLogger(ConfiguraDataBase.class);

	@Value("${spring.datasource.driver-class-name}")
	private String driver;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.url}")
	private String desaappsunarp;

	@Bean(name = { "dsappsunarp" })
	public DataSource appSunarpDataSource() throws Exception {
		log.info("desaappsunarp = " + desaappsunarp);
		HikariDataSource dataSource = null;
		HikariConfig config = new HikariConfig();
		try {

			config.setDriverClassName(driver);
			config.setJdbcUrl(desaappsunarp);
			config.setUsername(username);
			config.setPassword(password); // decodeJCE(password));
//			config.setPoolName("51) DATASOURCE-CHICLAYO");

			dataSource = new HikariDataSource(config);

		} catch (Exception e) {
			log.error(config.getPoolName());
			log.error(e.getMessage());
		}
		return dataSource;
	}

	@Bean(name = { "1101jdbc" })
	@Autowired
	public JdbcTemplate appSunarpJdbcTemplate(@Qualifier("dsappsunarp") DataSource dsappsunarp) throws Exception {
		JdbcTemplate jdbcTemplate = null;
		try {
			if (dsappsunarp != null) {
				jdbcTemplate = new JdbcTemplate(dsappsunarp);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return jdbcTemplate;
	}
}
