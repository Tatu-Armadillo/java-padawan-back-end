package com.padawan.desafio.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;
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

    private static String path = "C:/Users/Totem TI/Documents/GitHub/java-padawan-back-end/desafio/src/main/resources/templates/";

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

    public ByteArrayResource htmlForPdf() throws Exception {
        String pathIn = path + "/produtos.html";
        String pathOut = path + "/produtos.pdf";

        var fileInputStream = new FileInputStream(pathIn);
        var fileOutputStream = new FileOutputStream(pathOut);

        HtmlConverter.convertToPdf(fileInputStream, fileOutputStream);
        byte[] baos = filetoByteArray(pathOut);
        return new ByteArrayResource(baos);
    }

    public ByteArrayResource stringForPdf() throws Exception {
        List<Produto> produtos = produtoRepository.findAll();
        String html = constructHtmlWithAtributtes(produtos);
        String pathHtmlTemplate = writeFileHtml(path, "produtos.html", html);
        String pathPdf = path + "produtos.pdf";
        var fileInputStream = new FileInputStream(pathHtmlTemplate);
        var fileOutputStream = new FileOutputStream(pathPdf);
        HtmlConverter.convertToPdf(fileInputStream, fileOutputStream);
        byte[] baos = filetoByteArray(pathPdf);
        return new ByteArrayResource(baos);
    }

    // #region HTML CONVERT PDF

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

    // #endregion

    private String writeFileHtml(String filePath, String fileName, String html) throws Exception {
        filePath = filePath + fileName;
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        FileWriter fw = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(fw);
        out.write(html);
        out.flush();
        out.close();
        fr.close();
        br.close();
        return file.getPath();
    }

    public String constructHtmlWithAtributtes(List<Produto> produtos) {
        StringBuilder sb = new StringBuilder();
        String style = "<style>table {width: 100%; height: 100%;} table, th, td {border: 1px solid;}</style>";
        sb.append("<html><head>" + style + "</head><body>");
        sb.append(
                "<main><table><thead><tr><th>#</th><th>Nome</th><th>Descricao</th><th>Tamanho</th><th>Preco</tr></thead><tbody>");

        for (Produto produto : produtos) {
            sb.append("<tr>");
            sb.append("<td>" + produto.getIdProduto() + "</td>");
            sb.append("<td>" + produto.getNome() + "</td>");
            sb.append("<td>" + produto.getDescricao() + "</td>");
            sb.append("<td style=\"text-align: center;\">" + produto.getTamanho() + "</td>");
            sb.append("<td style=\"text-align: center;\">" + produto.getPreco() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody></table></main></body></html>");
        return sb.toString();
    }

}
