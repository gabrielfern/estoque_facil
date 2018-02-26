package com.ufcg.si1;

import com.ufcg.si1.controller.FiltroSenha;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages={"com.ufcg.si1"})
public class EstoqueFacilRestApi {

    @Bean
    public FilterRegistrationBean senhaAdminFiltro() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new FiltroSenha());
        frb.addUrlPatterns("/api/admin/*");
        return frb;
    }


	public static void main(String[] args) {
		SpringApplication.run(EstoqueFacilRestApi.class, args);
	}
}
