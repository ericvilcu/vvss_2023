package pizzashop.service;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceRepoIntegrationTest {

    private static final String path="data/test.txt";

    PaymentRepository repository;
    PizzaService service;

    @BeforeEach
    void setUp() throws IOException {
        assertTrue(new File(path).createNewFile(),"The file was already there");
        repository=new PaymentRepository(path);
        service = new PizzaService(null,repository);
    }

    @AfterEach
    void tearDown() {
        repository=null;
        service = null;
        assertTrue(new File(path).delete(),"The file disappeared");
    }

    @Test
    void test1(){
        Payment p = Mockito.mock(Payment.class);
        service.addPayment(p);
        assertEquals(1,service.getPayments().size());
        assertEquals(p,service.getPayments().get(0));
    }

    @Test
    void test2(){
        Payment p1 = Mockito.mock(Payment.class);
        Payment p2 = Mockito.mock(Payment.class);
        service.addPayment(p1);
        service.addPayment(p2);
        assertEquals(2,service.getPayments().size());
        assertEquals(p1,service.getPayments().get(0));
        assertEquals(p2,service.getPayments().get(1));
        assertNotEquals(p1,service.getPayments().get(1));
        assertNotEquals(p2,service.getPayments().get(0));
    }
}
