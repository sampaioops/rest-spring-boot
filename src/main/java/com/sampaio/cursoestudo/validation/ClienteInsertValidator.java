package com.sampaio.cursoestudo.validation;

import com.sampaio.cursoestudo.enums.TipoCliente;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.dto.ClienteCompletoDTO;
import com.sampaio.cursoestudo.repositorio.ClienteRepositorio;
import com.sampaio.cursoestudo.resources.Exceptions.CampoMessagem;
import com.sampaio.cursoestudo.utils.CNP;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteCompletoDTO> {


    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteCompletoDTO clienteCompletoDTO, ConstraintValidatorContext context) {
        List<CampoMessagem> list = new ArrayList<>();

        TipoCliente tipoCliente = null;

        if (clienteCompletoDTO.getTipoCliente() == null) {
            list.add(new CampoMessagem("tipoCliente", "TipoCliente não pode ser nulo!"));
        } else {
            tipoCliente = TipoCliente.valueOf(clienteCompletoDTO.getTipoCliente().toUpperCase().trim());
        }


        if (clienteCompletoDTO.getCpfCnpj() == null) {
            list.add(new CampoMessagem("CpfCnpj", "CpfCnpj não pode ser nulo!"));
        }

        if (tipoCliente != null) {

            if (clienteCompletoDTO.getCpfCnpj() != null) {
                if (tipoCliente.equals(TipoCliente.PESSOAFISICA)) {

                    if (!CNP.isValidCPF(clienteCompletoDTO.getCpfCnpj())) {
                        list.add(new CampoMessagem("CpfCnpj", "CPF não é válido!"));
                    }

                } else if (tipoCliente.equals(TipoCliente.PESSOAJURIDICA)) {

                    if (!CNP.isValidCNPJ(clienteCompletoDTO.getCpfCnpj())) {
                        list.add(new CampoMessagem("CpfCnpj", "CNPJ não é válido!"));
                    }

                }
            }

        }

        if (clienteCompletoDTO.getEmail() == null) {
            list.add(new CampoMessagem("email", "E-mail não pode ser nulo!"));
        } else {
            Cliente cliente = clienteRepositorio.findByEmail(clienteCompletoDTO.getEmail());

            if (cliente != null) {
                list.add(new CampoMessagem("email", "E-mail já existente no banco de dados!"));
            }
        }


        for (CampoMessagem e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMensagem())
                    .addPropertyNode(e.getNomeCampo()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
