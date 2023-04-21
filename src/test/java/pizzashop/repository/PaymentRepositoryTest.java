package pizzashop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private static final String path="data/test.txt";
    PaymentRepository r;
    @BeforeEach
    void setUp() throws IOException {
        assertTrue(new File(path).createNewFile(),"The file was already there");
        r=new PaymentRepository(path);
    }

    @AfterEach
    void tearDown() {
        r=null;
        assertTrue(new File(path).delete(),"The file disappeared");
    }

    @Test
    void testSave1() {
        r.writeAll();
        try(BufferedReader b=new BufferedReader(new FileReader(path))){
            assertNull(b.readLine(), "something was written in the file");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testSave2() {
        Payment toAdd=new Payment(0, PaymentType.Card,Math.PI);
        r.add(toAdd);
        r.writeAll();
        try(BufferedReader b=new BufferedReader(new FileReader(path))){
            assertEquals(toAdd.toString(),b.readLine(), "something was written in the file");
            assertNull(b.readLine(), "something was written in the file");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testSave3() {
        Payment toAdd=new Payment(0, PaymentType.Card,Math.PI);
        r.add(toAdd);
        r.writeAll();
        r=new PaymentRepository(path);
        r.add(toAdd);
        r.writeAll();
        try(BufferedReader b=new BufferedReader(new FileReader(path))){
            assertEquals(toAdd.toString(),b.readLine(), "something was written in the file");
            assertEquals(toAdd.toString(),b.readLine(), "something was written in the file");
            assertNull(b.readLine(), "something was written in the file");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testSave4() {
        assertThrows(NullPointerException.class,()-> {
            Payment toAdd = new Payment(0, PaymentType.Card, Math.PI);
            r.add(null);
            r.writeAll();
            try (BufferedReader b = new BufferedReader(new FileReader(path))) {
                assertEquals(toAdd.toString(), b.readLine(), "something was written in the file");
                assertNull(b.readLine(), "something was written in the file");
            } catch (IOException e) {
                fail(e.getMessage());
            }
        });
    }

    @Test
    void testSave5() {
        assertThrows(PaymentRepository.DataFileFormatException.class,()-> {
            try(BufferedWriter b=new BufferedWriter(new FileWriter(path))){
                b.write("invalid data");
            } catch (IOException e) {
                fail(e.getMessage());
            }
            r=new PaymentRepository(path);
        });
    }
}