import com.smallworld.TransactionDataFetcher;
import com.smallworld.data.Transaction;
import com.smallworld.util.JSONFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class TransactionDataFetcherTest {
    @InjectMocks
    @Spy
    private TransactionDataFetcher transactionDataFetcher;

    @Mock
    JSONFileReader jsonFileReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTotalTransactionAmount() {
        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);

        double expectedTotal = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        double actualTotal = transactionDataFetcher.getTotalTransactionAmount();


        assertEquals(expectedTotal, actualTotal);
    }


    @Test
    public void testGetTotalTransactionAmountSentBy() {
        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);

        double totalAmount = transactionDataFetcher.getTotalTransactionAmountSentBy("Ahmed");
        assertEquals(500.0, totalAmount);
    }

    @Test
    public void testGetMaxTransactionAmount() {

        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);
        double maxAmount = transactionDataFetcher.getMaxTransactionAmount();
        assertEquals(400.0, maxAmount, 0.001);
    }


    @Test
    public void testCountUniqueClients() {
        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);

        long count = transactionDataFetcher.countUniqueClients();
        assertEquals(3, count); // Assuming there are 3 unique clients in the mock data
    }

    @Test
    public void testHasOpenComplianceIssues() {

        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);

        boolean hasOpenIssues = transactionDataFetcher.hasOpenComplianceIssues("Ali");
        assertEquals(false, hasOpenIssues);
    }

    @Test
    public void testGetTransactionsByBeneficiaryName() {

        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);

        // Assuming there are 4 transactions with unique beneficiary names in the mock data
        assertEquals(4, transactionDataFetcher.getTransactionsByBeneficiaryName().size());
    }

    @Test
    public void testGetUnsolvedIssueIds() {

        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);
        // Assuming there are no unsolved issues in the mock data
        assertEquals(0, transactionDataFetcher.getUnsolvedIssueIds().size());
    }

    @Test
    public void testGetAllSolvedIssueMessages() {

        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);
        // Assuming there are no solved issues in the mock data
        assertEquals(3, transactionDataFetcher.getAllSolvedIssueMessages().size());
    }

    @Test
    public void testGetTop3TransactionsByAmount() {


        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);
        List<Transaction> top3Transactions = transactionDataFetcher.getTop3TransactionsByAmount();
        // Assuming there are 3 transactions in the mock data, so only 2 should be returned
        assertEquals(3, top3Transactions.size());
    }

    @Test
    public void testGetTopSender() {
        List<Transaction> transactions = buildTransactions();

        when(jsonFileReader.loadDataFromJson()).thenReturn(transactions);

        Optional<String> topSender = transactionDataFetcher.getTopSender();
        assertEquals("Ahmed", topSender.orElse(null));
    }
    private List<Transaction> buildTransactions() {
        return Arrays.asList(
                new Transaction(1234567,100.0,"Ahmed",23,"Ali",20,12678,true,"issue solved"),
                new Transaction(1234567,200.0,"Abdullah",23,"Asad",20,6554,true,"issue solved"),
                new Transaction(1234567,300.0,"Ali",23,"Wahaj",20,9875,true,"issue solved"),
                new Transaction(1234567,400.0,"Ahmed",23,"Aslam",20,0,false,"issue solved")
        );
    }
}




