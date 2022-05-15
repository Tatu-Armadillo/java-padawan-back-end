package com.padawan.desafio.services;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Optional;

import com.itextpdf.html2pdf.HtmlConverter;
import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private Produto produto;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void update(Long idProduto, Produto produto) {
        produto = this.produtoRepository.getById(idProduto);
        produto.setNome(produto.getNome());
        produto.setDiretorio(produto.getDiretorio());
        produto.setUrl(produto.getUrl());
        produto.setPreco(produto.getPreco());
        produto.setTamanho(produto.getTamanho());
        produto.setDescricao(produto.getDescricao());
    }

    public Produto inserirProduto(Long idProduto) {
        Optional<Produto> produto = this.produtoRepository.findById(idProduto);
        if (produto.isPresent()) {
            return produto.get();
        } else {
            System.out.println("Produto não encontrado");
            return null;
        }
    }

    public Produto getProduto() {
        return this.produto;
    }

    public ByteArrayResource relatorioProdutos() throws Exception {
        String path = "D:/VisualStudioCode/Back-end/Java/java-padawan-back-end/desafio/src/main/resources/templates/produtos.html";
        var fileInputStream = new FileInputStream(path);
        var fileOutputStream = new FileOutputStream("D:/VisualStudioCode/Back-end/Java/java-padawan-back-end/desafio/src/main/resources/templates/produtos.pdf");
        HtmlConverter.convertToPdf(fileInputStream, fileOutputStream);
        return new ByteArrayResource(filetoByteArray("D:/VisualStudioCode/Back-end/Java/java-padawan-back-end/desafio/src/main/resources/templates/produtos.pdf"));
    }

    private byte[] filetoByteArray(String path) {
        byte[] data;
        try {
            InputStream input = new FileInputStream(path);
            int byteReads;
            ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
            while ((byteReads = input.read()) != -1) {
                output.write(byteReads);
            }

            data = output.toByteArray();
            output.close();
            input.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
