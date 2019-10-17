package com.sampaio.cursoestudo.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dataHora;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;


    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereco_de_entrega_id")
    private Endereco enderecoEntraga;

    @OneToMany(mappedBy = "id.pedido")
    private Set<PedidoItem> items = new HashSet<>();

    public Pedido() {
    }

    public Pedido(Date dataHora, Cliente cliente, Endereco enderecoEntraga) {
        this.dataHora = dataHora;
        this.cliente = cliente;
        this.enderecoEntraga = enderecoEntraga;
    }

    public Double getValorTotal(){
        double soma = 0;
        for(PedidoItem item : this.getItems()){
            soma = soma + item.getSubTotal();
        }

        return soma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoEntraga() {
        return enderecoEntraga;
    }

    public void setEnderecoEntraga(Endereco enderecoEntraga) {
        this.enderecoEntraga = enderecoEntraga;
    }

    public Set<PedidoItem> getItems() {
        return items;
    }

    public void setItems(Set<PedidoItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", pagamento=" + pagamento +
                ", cliente=" + cliente +
                ", enderecoEntraga=" + enderecoEntraga +
                '}';
    }
}
