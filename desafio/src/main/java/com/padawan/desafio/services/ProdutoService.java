package com.padawan.desafio.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.padawan.desafio.models.Produto;
import com.padawan.desafio.repositories.ProdutoRepository;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private Produto produto;

    @Autowired
    private ProdutoRepository produtoRepository;

    // private static String path =
    // "D:/VisualStudioCode/Back-end/Java/java-padawan-back-end/desafio/src/main/resources/templates/";
    private static String path = "C:/Users/Totem TI/Documents/GitHub/java-padawan-back-end/desafio/src/main/resources/templates/";
    private static String pathImg = "C:/Users/Totem TI/Documents/GitHub/java-padawan-back-end/desafio/src/main/resources/static/";


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
        String pathHtmlTemplate = writeFileHtml(path, "produtos2.html", html);
        String pathPdf = path + "produtos2.pdf";
        var fileInputStream = new FileInputStream(pathHtmlTemplate);
        var fileOutputStream = new FileOutputStream(pathPdf);
        HtmlConverter.convertToPdf(fileInputStream, fileOutputStream);
        byte[] baos = filetoByteArray(pathPdf);
        return new ByteArrayResource(baos);
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

    private String writeFileHtml(String filePath, String fileName, String html) throws Exception {
        filePath = filePath + fileName;
        File file = new File(filePath); // Arquivo Mocado
        // File.createTempFile(filePath, fileName); Arquivo em Memoria
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

    public ByteArrayResource velocityHtmlConvertPdf() throws Exception {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        ve.init();

        Template template = ve.getTemplate("templates/produtos.html");
        VelocityContext context = new VelocityContext();
        mapObjectHtml(context);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        System.out.println(writer.toString());
        InputStream css = this.getClass().getResourceAsStream("/templates/produtos.css");
        return convertHtmlToPdf(writer.toString(), css);
    }

    private ByteArrayResource convertHtmlToPdf(String contentHtml, InputStream fileCss) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();

            final Document pdfDocument = new Document(PageSize.A4.rotate());
            pdfDocument.setMargins(0, 0, 0, 0);

            final PdfWriter writer = PdfWriter.getInstance(pdfDocument, baos);

            pdfDocument.open();
            writer.open();

            final CSSResolver cssResolver = new StyleAttrCSSResolver();
            final CssFile cssFile = XMLWorkerHelper.getCSS(fileCss);
            cssResolver.addCss(cssFile);

            final HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

            final PdfWriterPipeline pdf = new PdfWriterPipeline(pdfDocument, writer);
            final HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            final CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            final XMLWorker worker = new XMLWorker(css, true);
            final XMLParser p = new XMLParser(worker);
            final Reader htmlreader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(contentHtml.getBytes())));
            p.parse(htmlreader);

            writer.close();
            pdfDocument.close();
            baos.close();
            return new ByteArrayResource(baos.toByteArray());

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void mapObjectHtml(VelocityContext context) {
        List<Produto> produtos = produtoRepository.findAll();
        context.put("produtos", produtos);
        context.put("cabecalho", pathImg + "cabecalho.png");
        context.put("rodape", pathImg + "rodape.png");
    
    } 

}
