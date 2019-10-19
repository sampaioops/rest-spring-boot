package com.sampaio.cursoestudo.config;

import com.sampaio.cursoestudo.servico.DbService;
import com.sampaio.cursoestudo.servico.EmailServico;
import com.sampaio.cursoestudo.servico.MockEmailServico;
import com.sampaio.cursoestudo.servico.SmtpEmailServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DbService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantienteDataBase(){
        if(!"create".equals(strategy)){
            return false;
        }

        dbService.instantianteTestDatabase();

        return true;
    }

    @Bean
    public EmailServico emailServico(){
        return new SmtpEmailServico();
    }


}
