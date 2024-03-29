package com.sampaio.cursoestudo.modelo.dto;

import com.sampaio.cursoestudo.validation.ClienteInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteInsert
public class ClienteCompletoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento é obrigatório")
    @Length(min = 5, max =120, message = "O Tamanho dever entre 5 e 120 caracteres")
    private String nome;
    @NotEmpty(message = "Preenchimento é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;
    @NotEmpty(message = "Preenchimento é obrigatório")
    private String cpfCnpj;
    @NotEmpty(message = "Preenchimento é obrigatório")
    private String tipoCliente;
    @NotEmpty(message = "Preenchimento é obrigatório")
    private String senha;

    @NotEmpty(message = "Preenchimento é obrigatório")
    private String logradouro;
    @NotEmpty(message = "Preenchimento é obrigatório")
    private String numero;
    private String complemento;
    private String bairro;
    @NotEmpty(message = "Preenchimento é obrigatório")
    private String cep;

    @NotEmpty(message = "Preenchimento é obrigatório")
    private String telefone1;
    private String telefone2;
    private String telefone3;

    private Long cidadeId;

    public ClienteCompletoDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public Long getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(Long cidadeId) {
        this.cidadeId = cidadeId;
    }
}
