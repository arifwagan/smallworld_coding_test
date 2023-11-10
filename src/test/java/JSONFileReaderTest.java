import com.smallworld.data.Transaction;
import com.smallworld.util.JSONFileReader;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

public class JSONFileReaderTest {
    @InjectMocks
    @Spy
    JSONFileReader jsonFileReader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadDataFromJson(){

        List<Transaction> transactions = jsonFileReader.loadDataFromJson();
        Assert.assertNotNull(transactions);
        Assert.assertEquals(13,transactions.size());
    }
}
