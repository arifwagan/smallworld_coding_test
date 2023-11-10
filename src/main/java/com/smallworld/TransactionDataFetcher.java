package com.smallworld;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import com.smallworld.util.JSONFileReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    JSONFileReader jsonFileReader;


    @Autowired
    public TransactionDataFetcher(JSONFileReader jsonFileReader) {
        this.jsonFileReader  = jsonFileReader;
    }


    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() throws UnsupportedOperationException {
        List<Transaction> transactions = jsonFileReader.loadDataFromJson();
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();


    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) throws UnsupportedOperationException  {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() throws UnsupportedOperationException {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .max()
                .orElse(0);
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() throws UnsupportedOperationException {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .map(transaction -> transaction.getSenderFullName())
                .distinct()
                .count();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) throws UnsupportedOperationException  {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(clientFullName)
                        || transaction.getBeneficiaryFullName().equals(clientFullName))
                .anyMatch(transaction -> !transaction.isIssueSolved());
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() throws UnsupportedOperationException  {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .collect(Collectors.toMap(Transaction::getBeneficiaryFullName, transaction -> transaction));

    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() throws UnsupportedOperationException {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .filter(transaction -> transaction.getIssueId() !=0 && !transaction.isIssueSolved())
                .map(Transaction::getIssueId)
                .collect(Collectors.toSet());

    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() throws UnsupportedOperationException {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .filter(transaction -> transaction.isIssueSolved())
                .map(Transaction::getIssueMessage)
                .collect(Collectors.toList());

    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() throws UnsupportedOperationException {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getAmount).reversed())
                .limit(3)
                .collect(Collectors.toList());

    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() throws UnsupportedOperationException {
        List<Transaction> transactions    = jsonFileReader.loadDataFromJson();
        Map<String, Double> senderTotalAmountMap = new HashMap<>();
        for (Transaction transaction : transactions) {
            String senderFullName = transaction.getSenderFullName();
            double amount = transaction.getAmount();
            senderTotalAmountMap.put(senderFullName, senderTotalAmountMap.getOrDefault(senderFullName, 0.0) + amount);
        }

        Optional<Map.Entry<String, Double>> topSenderEntry = senderTotalAmountMap.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue));

        return topSenderEntry.map(Map.Entry::getKey);

    }


}
