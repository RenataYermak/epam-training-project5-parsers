package com.epam.parsers.controller;

import com.epam.parsers.model.Card;
import com.epam.parsers.service.parser.AbstractCardBuilder;
import com.epam.parsers.service.CardBuilderFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(urlPatterns = {"/parserController"})
public class ParserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        executeRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        executeRequest(req, resp);
    }

    private void executeRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String parserType = req.getParameter("parserType");
        String filePath = uploadFile(req);
        AbstractCardBuilder builder = CardBuilderFactory.createCardBuilder(parserType);
        builder.buildSetCards(filePath);
        List<Card> cards = builder.getCards().stream()
                .sorted(Comparator.comparing(Card::getId))
                .collect(Collectors.toList());
        req.setAttribute("cards", cards);
        req.setAttribute("parserType", parserType);
        getServletContext().getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    private String uploadFile(HttpServletRequest req) throws ServletException, IOException {
        String fileName = null;
        Part filePart = req.getPart("file");
        try (InputStream inputStream = filePart.getInputStream()) {
            fileName = filePart.getSubmittedFileName();
            Path filePath = new File("C:\\test\\" + fileName).toPath();
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("C:\\test\\%s", fileName);
    }
}
