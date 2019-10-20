package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.enums.TipoCliente;
import com.sampaio.cursoestudo.exception.DataIntegrityException;
import com.sampaio.cursoestudo.exception.ObjectNotFoundExcpetion;
import com.sampaio.cursoestudo.modelo.Categoria;
import com.sampaio.cursoestudo.modelo.Cidade;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.Endereco;
import com.sampaio.cursoestudo.modelo.dto.ClienteCompletoDTO;
import com.sampaio.cursoestudo.modelo.dto.ClienteDTO;
import com.sampaio.cursoestudo.repositorio.ClienteRepositorio;
import com.sampaio.cursoestudo.repositorio.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServico implements Serializable {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private EnderecoRepositorio enderecoRepositorio;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente buscar(Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);

        return cliente.orElseThrow(() -> new ObjectNotFoundExcpetion(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente salvar(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepositorio.save(cliente);

        enderecoRepositorio.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente cliente) {
        Cliente clienteAtualizado = buscar(cliente.getId());
        updateData(clienteAtualizado, cliente);
        return clienteRepositorio.save(clienteAtualizado);
    }

    public void deletar(Long id) {
        buscar(id);
        try {
            clienteRepositorio.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir porque há pedidos relacionados");
        }
    }


    public List<Cliente> buscarTodos() {
        return clienteRepositorio.findAll();
    }

    public Page<Cliente> buscarPaginas(Integer pagina, Integer linhasPorPagina, String orderBy, String direcao) {

        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), orderBy);

        return clienteRepositorio.findAll(pageRequest);
    }

    public Cliente retornaCliente(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail());
    }

    public Cliente retornaCliente(ClienteCompletoDTO clienteCompletoDTO) {
        Cliente cliente = new Cliente(clienteCompletoDTO.getNome(), clienteCompletoDTO.getEmail(), clienteCompletoDTO.getCpfCnpj(), TipoCliente.valueOf(clienteCompletoDTO.getTipoCliente().toUpperCase().trim()), bCryptPasswordEncoder.encode(clienteCompletoDTO.getSenha()));
        Cidade cidade = new Cidade(clienteCompletoDTO.getCidadeId(), null, null);
        Endereco endereco = new Endereco(clienteCompletoDTO.getLogradouro(), clienteCompletoDTO.getNumero(), clienteCompletoDTO.getComplemento(), clienteCompletoDTO.getBairro(), clienteCompletoDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);
        cliente.getTelefones().add(clienteCompletoDTO.getTelefone1());

        if (clienteCompletoDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteCompletoDTO.getTelefone2());
        }

        if (clienteCompletoDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteCompletoDTO.getTelefone3());
        }

        return cliente;
    }

    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }
}
