package com.sampaio.cursoestudo.validation;

import com.sampaio.cursoestudo.enums.TipoCliente;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.dto.ClienteCompletoDTO;
import com.sampaio.cursoestudo.modelo.dto.ClienteDTO;
import com.sampaio.cursoestudo.repositorio.ClienteRepositorio;
import com.sampaio.cursoestudo.resources.Exceptions.CampoMessagem;
import com.sampaio.cursoestudo.utils.CNP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {
        List<CampoMessagem> list = new ArrayList<>();

        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Long id = Long.valueOf(map.get("id").trim());

        if (clienteDTO.getEmail() == null) {
            list.add(new CampoMessagem("email", "E-mail não pode ser nulo!"));
        } else {
            Cliente cliente = clienteRepositorio.findByEmail(clienteDTO.getEmail());

            if (cliente != null && !cliente.getId().equals(id)) {
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
