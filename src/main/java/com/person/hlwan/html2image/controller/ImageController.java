package com.person.hlwan.html2image.controller;

import cz.vutbr.web.css.CSSProperty;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.fit.cssbox.demo.ImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.UUID;

@RestController
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Value("${root.path}")
    private String rootPath;


    @GetMapping("/generate")
    public String generate(String html) throws Exception {
//        html=new String(html.getBytes("iso-8859-1"),"utf-8");

        logger.info(html);
        return "/image/load/" + output(html, null, null);
    }

    @PostMapping("/generate")
    public String generateFromBody(@RequestBody String html, @RequestParam(required = false) Integer width, @RequestParam(required = false) Integer height) throws Exception {
//        html=new String(html.getBytes("iso-8859-1"),"utf-8");

        return "/image/load/" + output(html, width, height);
    }

    @GetMapping("/load/{guid}")
    public void loadImage(@PathVariable String guid, HttpServletResponse response) throws Exception {
        File file = new File(rootPath + "/" + guid + ".png");
        IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        response.getOutputStream().flush();
    }

    private String output(String html, Integer width, Integer height) throws Exception {
        String guid = UUID.randomUUID().toString();



        /*HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage(rootPath+guid+".jpg");*/

        ImageRenderer render = new ImageRenderer();
        if (height != null && width != null)
            render.setWindowSize(new Dimension(width, height), false);

        BufferedWriter writer = new BufferedWriter(new FileWriter(rootPath + guid));
        writer.write(html);
        writer.flush();
        writer.close();

        String url = new File(rootPath + guid).toURI().toString();
        FileOutputStream out = new FileOutputStream(new File(rootPath + guid + ".svg"));
        render.renderURL(url, out, ImageRenderer.Type.SVG);
        out.close();

        return guid;
    }

}
