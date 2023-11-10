package com.smallworld.servlets;

import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/transactions/*")
public class TransactionServlet extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(TransactionServlet.class);

    @Autowired
    private TransactionDataFetcher dataFetcher;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid URL");
            logger.info("Invalid URL");
            return;
        }

        switch (pathInfo) {
            case "/totalTransactionAmount":
                handleTotalTransactionAmount(request, response);
                break;
            case "/totalTransactionAmountSentBy":
                handleTotalTransactionAmountSentBy(request, response);
                break;
            case "/maxTransactionAmount":
                handleMaxTransactionAmount(request, response);
                break;
            case "/countUniqueClients":
                handleCountUniqueClients(request, response);
                break;
            case "/hasOpenComplianceIssues":
                handleHasOpenComplianceIssues(request, response);
                break;
            case "/getTransactionsByBeneficiaryName":
                handleGetTransactionsByBeneficiaryName(request, response);
                break;
            case "/getUnsolvedIssueIds":
                handleGetUnsolvedIssueIds(request, response);
                break;
            case "/getAllSolvedIssueMessages":
                handleGetAllSolvedIssueMessages(request, response);
                break;
            case "/getTop3TransactionsByAmount":
                handleGetTop3TransactionsByAmount(request, response);
                break;
            case "/getTopSender":
                handleGetTopSender(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Endpoint not found");
        }
    }

    private void handleTotalTransactionAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            double totalAmount = dataFetcher.getTotalTransactionAmount();
            response.getWriter().write("Total Transaction Amount: " + totalAmount);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleTotalTransactionAmountSentBy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderFullName = request.getParameter("senderFullName");
        try {
            double totalAmount = dataFetcher.getTotalTransactionAmountSentBy(senderFullName);
            response.getWriter().write("Total Transaction Amount Sent By " + senderFullName + ": " + totalAmount);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleMaxTransactionAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            double maxAmount = dataFetcher.getMaxTransactionAmount();
            response.getWriter().write("Max Transaction Amount: " + maxAmount);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleCountUniqueClients(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            long count = dataFetcher.countUniqueClients();
            response.getWriter().write("Count of Unique Clients: " + count);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleHasOpenComplianceIssues(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String clientFullName = request.getParameter("clientFullName");
        try {
            boolean hasOpenIssues = dataFetcher.hasOpenComplianceIssues(clientFullName);
            response.getWriter().write("Has Open Compliance Issues: " + hasOpenIssues);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleGetTransactionsByBeneficiaryName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Map<String, Transaction> transactions = dataFetcher.getTransactionsByBeneficiaryName();
            // You can serialize and send the transactions as JSON here
            response.getWriter().write("Transactions by Beneficiary Name: " + transactions);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleGetUnsolvedIssueIds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Set<Integer> issueIds = dataFetcher.getUnsolvedIssueIds();
            // You can serialize and send the issueIds as JSON here
            response.getWriter().write("Unsolved Issue IDs: " + issueIds);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleGetAllSolvedIssueMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<String> issueMessages = dataFetcher.getAllSolvedIssueMessages();
            // You can serialize and send the issueMessages as JSON here
            response.getWriter().write("Solved Issue Messages: " + issueMessages);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleGetTop3TransactionsByAmount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Transaction> top3Transactions = dataFetcher.getTop3TransactionsByAmount();
            // You can serialize and send the top3Transactions as JSON here
            response.getWriter().write("Top 3 Transactions by Amount: " + top3Transactions);
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    private void handleGetTopSender(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Optional<String> topSender = dataFetcher.getTopSender();
            response.getWriter().write("Top Sender: " + topSender.orElse("No top sender found"));
        } catch (UnsupportedOperationException e) {
            logger.error("Error: " + e.getMessage());
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
