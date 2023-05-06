package pizzashop.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    void test1(){
        Payment payment=new Payment(2,PaymentType.Card,2);
        assertFalse(payment.isInvalid());
    }
    @Test
    void test2(){
        Payment payment=new Payment(-2,PaymentType.Card,2);
        assertTrue(payment.isInvalid());
    }
}
