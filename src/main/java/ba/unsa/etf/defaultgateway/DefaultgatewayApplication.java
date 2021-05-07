package ba.unsa.etf.defaultgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
public class DefaultgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefaultgatewayApplication.class, args);
    }

}
