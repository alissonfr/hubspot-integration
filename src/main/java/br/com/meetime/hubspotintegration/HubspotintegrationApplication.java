package br.com.meetime.hubspotintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.meetime.hubspotintegration")
public class HubSpotintegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubSpotintegrationApplication.class, args);
	}

}
