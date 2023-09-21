package com.cfex.jdbc.thymeleaf;

import com.cfex.jdbc.entity.User;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ThymeleafTest {
    @Autowired private TemplateEngine engine;

    /**
     * 1. https://jxls.sourceforge.net/
     * dynamic generate excel by template with parameters
     * 2. https://www.e-iceblue.com/
     * https://www.finclip.com/news/f/29278.html use spire.xls.free transfer excel to pdf
     * 3. https://purchase.aspose.com/pricing/total/java
     */
    @Test
    void testPdf1() throws IOException {
        Param param = Param.of().setName("tom").setImgUrl("http://localhost:8083/demo/logo")
                .setStudent(Param.Student.of().setName("jerry"));
        Context context = new Context();
        context.setVariable("param", param);
        String hello = engine.process("pdf/pdf", context);
        System.out.println(hello);
        ConverterProperties properties = new ConverterProperties();
//        properties.setBaseUri("http://127.0.0.1:8083/images");
        OutputStream outputStream = Files.newOutputStream(Paths.get("/Users/xingtingbiao/Downloads/test.pdf"));
        List<IElement> iElements = HtmlConverter.convertToElements(hello);
//        HtmlConverter.convertToPdf(hello, outputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(hello, bos);
        byte[] bytes = bos.toByteArray();
        System.out.println(bytes);
    }


    @Test
    void testPdf2() throws IOException {
        Document doc = Jsoup.parse("<html><body><IMG src='https://img2.baidu.com/it/u=3849163905,1274783814&amp;fm=253&amp;fmt=auto&amp;app=138&amp;f=PNG?w=900&amp;h=383' /></body></html>");

        Elements img = doc.select("img");
        System.out.println();
    }

    @Test
    void testPdf3() throws IOException {
        URL url = new URL("https://img2.baidu.com/it/u=3849163905,1274783814&fm=253&fmt=auto&app=138&f=PNG?w=900&h=383");
        InputStream inputStream = url.openConnection().getInputStream();
        URL url2 = new URL("http://localhost:8083/images/logo.png");
        InputStream inputStream2 = url2.openConnection().getInputStream();
        System.out.println();
    }

}
