package pizzashop.repository;

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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepoMockTest {

    private static final String path="data/test.txt";
    private PaymentRepository paymentRepository;


    void assetIsInFile(String expected){
        try {
            String found=Files.readAllLines(Paths.get(path)).stream().reduce("",(x,y)->x+"\n"+y)+"\n";
            assertEquals("\n"+expected,found);
        } catch (Exception e){
            fail(e);
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        assertTrue(new File(path).createNewFile(),"The file was already there");
        paymentRepository=new PaymentRepository(path);
    }

    @AfterEach
    void tearDown() {
        paymentRepository=null;
        assertTrue(new File(path).delete(),"The file disappeared");
    }

    @Test
    void test1(){
        String test_save="TEST----TEST";
        Payment payment=Mockito.mock(Payment.class);
        Mockito.when(payment.toString()).thenReturn(test_save);
        paymentRepository.add(payment);
        assetIsInFile(test_save+"\n");
    }

    @Test
    void test2(){
        String test_save="EXAMPLE_ONE";
        String test_save2="EXAMPLE_TWO";
        Payment payment=Mockito.mock(Payment.class);
        Mockito.when(payment.toString()).thenReturn(test_save);
        paymentRepository.add(payment);

        Payment payment2=Mockito.mock(Payment.class);
        Mockito.when(payment2.toString()).thenReturn(test_save2);
        paymentRepository.add(payment2);
        assetIsInFile(test_save+"\n"+test_save2+"\n");
    }
}
