package pizzashop.service;

import org.junit.jupiter.api.*;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTest {

    private PizzaService pizzaService;
    private MockPaymentRepository mockPaymentRepository;
    private static final String path="data/test.txt";

    private class MockPaymentRepository extends PaymentRepository
    {
        public ArrayList<Payment> payments = new ArrayList<>();

        @Override
        public List<Payment> getAll()
        {
            return payments;
        }
    }

    @BeforeEach
    void setUp() {
        mockPaymentRepository =new MockPaymentRepository();
        pizzaService = new PizzaService(new MenuRepository(), mockPaymentRepository);
    }

    @AfterEach
    void tearDown() {
        pizzaService = null;
        new File(path).delete();
    }

    @Test
    void F02_TC01()
    {
        mockPaymentRepository.payments = null;

        double result = pizzaService.getTotalAmount(PaymentType.Card);
        assertEquals(.0f, result);

        result = pizzaService.getTotalAmount(PaymentType.Cash);
        assertEquals(.0f, result);
    }

    @Test
    void F02_TC02()
    {
        mockPaymentRepository.payments = new ArrayList<>();

        double result = pizzaService.getTotalAmount(PaymentType.Card);
        assertEquals(.0f, result);

        result = pizzaService.getTotalAmount(PaymentType.Cash);
        assertEquals(.0f, result);
    }

    @Test
    void F02_TC03()
    {
        mockPaymentRepository.payments = new ArrayList<>();
        mockPaymentRepository.payments.add(new Payment(1, PaymentType.Card, 3.0f));
        mockPaymentRepository.payments.add(new Payment(1, PaymentType.Cash, 2.0f));

        double result = pizzaService.getTotalAmount(PaymentType.Card);
        assertEquals(3.0f, result);

        result = pizzaService.getTotalAmount(PaymentType.Cash);
        assertEquals(2.0f, result);
    }
}