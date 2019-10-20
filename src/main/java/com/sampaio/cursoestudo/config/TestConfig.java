package com.sampaio.cursoestudo.config;

import com.sampaio.cursoestudo.servico.DbService;
import com.sampaio.cursoestudo.servico.email.EmailServico;
import com.sampaio.cursoestudo.servico.email.MockEmailServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DbService dbService;

    @Bean
    public boolean instantienteDataBase(){
        dbService.instantianteTestDatabase();
        return true;
    }

    @Bean
    public EmailServico emailServico(){
        return new MockEmailServico();
    }


}
