package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.enums.PagamentoStatus;
import com.sampaio.cursoestudo.exception.AuthorizationException;
import com.sampaio.cursoestudo.exception.ObjectNotFoundExcpetion;
import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.PagamentoBoleto;
import com.sampaio.cursoestudo.modelo.Pedido;
import com.sampaio.cursoestudo.modelo.PedidoItem;
import com.sampaio.cursoestudo.repositorio.PagamentoRepositorio;
import com.sampaio.cursoestudo.repositorio.PedidoItemRepositorio;
import com.sampaio.cursoestudo.repositorio.PedidoRepositorio;
import com.sampaio.cursoestudo.seguranca.UserSS;
import com.sampaio.cursoestudo.servico.email.EmailServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Service
public class PedidoServico implements Serializable {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private PagamentoRepositorio pagamentoRepositorio;

    @Autowired
    private ProdutoServico produtoServico;

    @Autowired
    private PedidoItemRepositorio pedidoItemRepositorio;

    @Autowired
    private ClienteServico clienteServico;

    @Autowired
    private EmailServico emailServico;

    public Pedido buscar(Long id) {
        Optional<Pedido> pedido = pedidoRepositorio.findById(id);

        return pedido.orElseThrow(() -> new ObjectNotFoundExcpetion(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        pedido.setId(null);
        pedido.setDataHora(new Date());
        pedido.getPagamento().setPagamentoStatus(PagamentoStatus.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        pedido.setCliente(clienteServico.buscar(pedido.getCliente().getId()));

        if (pedido.getPagamento() instanceof PagamentoBoleto) {
            PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
            pagamentoBoleto.setDataVencimento(new Date());
        }

        pedido = pedidoRepositorio.save(pedido);

        pagamentoRepositorio.save(pedido.getPagamento());

        for (PedidoItem item : pedido.getItems()) {
            item.setDesconto(0d);
            item.setProduto(produtoServico.buscar(item.getProduto().getId()));
            item.setPreco(item.getProduto().getPreco());
            item.setPedido(pedido);
        }

        pedidoItemRepositorio.saveAll(pedido.getItems());

        //emailServico.envioaOrdemConfirmacaoEmail(pedido);

        return pedido;
    }

    public Page<Pedido> buscarPaginas(Integer pagina, Integer linhasPorPagina, String orderBy, String direcao) {

        UserSS userSS = UsuarioServico.authenticated();

        if (userSS == null) {
            throw new AuthorizationException("Acesso negado!");
        }

        PageRequest pageRequest = PageRequest.of(pagina, linhasPorPagina, Sort.Direction.valueOf(direcao), orderBy);

        Cliente cliente = clienteServico.buscar(userSS.getId());

        return pedidoRepositorio.findByCliente(cliente, pageRequest);
    }
}
