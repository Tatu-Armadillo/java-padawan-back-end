package com.padawan.desafio.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.padawan.desafio.models.Cliente;
import com.padawan.desafio.models.Funcionario;
import com.padawan.desafio.models.Movimento;
import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.MovimentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentoService {

    public Movimento movimento;

    @Autowired
    private MovimentoRepository movimentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private FuncionarioService funcionarioService;

    public void gerarPedido(Produto produto, Cliente cliente, Funcionario funcionario) {
        String codigo = this.gerarCodigo();
        Produto item = produtoService.inserirProduto(produto.getIdProduto());
        Cliente comprador = clienteService.inserirCliente(cliente.getIdCliente());
        Funcionario vendedor = funcionarioService.inserirFuncionario(funcionario.getIdFuncionario());
        LocalDateTime dateTime = LocalDateTime.now();
        movimento = new Movimento(codigo, codigo, dateTime, "S", item, vendedor, comprador);
        movimentoRepository.save(movimento);
    }

    private String gerarCodigo() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSSS");
        System.out.println(dateTime.format(dateTimeFormatter));
        String dataCodigo = dateTime.format(dateTimeFormatter);

        return "BH#" + dataCodigo;
    }

}
