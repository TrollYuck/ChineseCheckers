package pwr.tp.server.DB;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DataOperator {

  @Bean
  private static DataSource getDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://127.0.0.1:3308/SavedGames");
    dataSource.setUsername("root");
    dataSource.setPassword("1234");
    return dataSource;
  }

  @Bean
  public static JdbcTemplate jdbcTemplate() {
    DataSource dataSource=getDataSource();
    return new JdbcTemplate(dataSource);
  }
}
