package com.epam.parsers.controller;

import com.epam.parsers.model.Card;
import com.epam.parsers.service.parser.AbstractCardBuilder;
import com.epam.parsers.service.CardBuilderFactory;
import com.epam.parsers.validator.ValidatorXML;

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

    private static final String XSD_PATH = "C:/epam-training-project5-parsers/out/production/resources/cardList.xsd";
    private static final String XML_PATH = "C:/epam-training-project5-parsers/out/production/resources/cardList.xml";
    private static final String RESULT_PAGE = "/result.jsp";
    private static final String CARDS = "cards";
    private static final String PARSER_TYPE = "parserType";
    private static final String FILE = "file";

    public void init() {
    }

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
        String parserType = req.getParameter(PARSER_TYPE);
        String filePathXML = uploadFile(req);
        ValidatorXML validatorXML = new ValidatorXML();
        if (validatorXML.isFileValid(XSD_PATH, filePathXML)) {
            try {
                AbstractCardBuilder builder = CardBuilderFactory.createCardBuilder(parserType);
                builder.buildSetCards(filePathXML);
                List<Card> cards = builder.getCards().stream()
                        .sorted(Comparator.comparing(Card::getId))
                        .collect(Collectors.toList());
                req.setAttribute(CARDS, cards);
                req.setAttribute(PARSER_TYPE, parserType);
                getServletContext().getRequestDispatcher(RESULT_PAGE).forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException("Error in method executeRequest" + e);
            }
        }
    }

    private String uploadFile(HttpServletRequest req) throws ServletException, IOException {
        String fileName;
        Part filePart = req.getPart(FILE);
        try (InputStream inputStream = filePart.getInputStream()) {
            fileName = filePart.getSubmittedFileName();
            Path filePath = new File(XML_PATH + fileName).toPath();
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return XML_PATH;
    }

    public void destroy() {
    }
}
