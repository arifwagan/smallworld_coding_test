package com.smallworld.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import com.smallworld.servlets.TransactionServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class JSONFileReader {
    List<Transaction> transactions = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(TransactionServlet.class);

    /**
     * Loads the data from the Transaction.json
     */
    public List<Transaction> loadDataFromJson() {
        try {
            // Load the JSON file from resources
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream jsonFile = classLoader.getResourceAsStream("transactions.json");

            // Parse JSON using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            transactions = objectMapper.readValue(jsonFile, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Transaction.class));

        } catch (IOException e) {
            logger.error("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return transactions;
    }

}
