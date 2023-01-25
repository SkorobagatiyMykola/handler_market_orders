package ua.skorobahatyi.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ua.skorobahatyi.model.OrderBook;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CreationHttpPage {
    private static final String fileReport = "orderBook.html";

    public static void generateOrderBookForHtml(OrderBook orderBook) throws IOException{
        Configuration cfg = new Configuration();
        Map<String, Object> input = new HashMap<>();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        input.put("date", LocalDate.now());
        input.put("time", time.format(format));
        input.put("orders", orderBook.getOrderBook());
        input.put("bestAskPrice", orderBook.getBestAskPrice());
        input.put("bestBidPrice", orderBook.getBestBidPrice());

        String lastComment = "© 2023. %s All rights reserved.";
        input.put("comment", String.format(lastComment, "The task was completed by Mykola Skorobahatyi."));
        input.put("developer", "Mykola Skorobahatyi");

        cfg.setClassForTemplateLoading(CreationHttpPage.class, "/templates");
        Template template = cfg.getTemplate("template_report.ftl");

        Writer fileWriter = new FileWriter(fileReport);
        try {
            template.process(input, fileWriter);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } finally {
            fileWriter.close();
        }
    }
}
