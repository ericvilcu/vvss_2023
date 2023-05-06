package pizzashop.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class ServiceMockTest {

    private PizzaService pizzaService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private MenuRepository menuRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @AfterEach
    void tearDown() {
        pizzaService = null;
    }

    @Test
    void test1(){
        Mockito.when(paymentRepository.getAll()).thenReturn(new ArrayList<>());
        assertEquals(0,pizzaService.getPayments().size());
    }

    @Test
    void test2(){
        ArrayList<Payment> payments=new ArrayList<>();
        payments.add(new Payment(6,PaymentType.Card, 2.0));
        Mockito.when(paymentRepository.getAll()).thenReturn(payments);
        assertEquals(1,pizzaService.getPayments().size());
        assertEquals(2.0,pizzaService.getPayments().get(0).getAmount());
        assertEquals(6,pizzaService.getPayments().get(0).getTableNumber());
        assertEquals(PaymentType.Card,pizzaService.getPayments().get(0).getType());
    }
}
