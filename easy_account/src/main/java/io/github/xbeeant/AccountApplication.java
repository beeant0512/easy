package io.github.xbeeant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/09
 */
@SpringBootApplication
@EnableJdbcHttpSession
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
