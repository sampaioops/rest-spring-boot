package com.sampaio.cursoestudo;

import com.sampaio.cursoestudo.enums.PagamentoStatus;
import com.sampaio.cursoestudo.enums.TipoCliente;
import com.sampaio.cursoestudo.modelo.*;
import com.sampaio.cursoestudo.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class CursoestudoApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(CursoestudoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
