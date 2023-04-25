package pizzashop.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    @DisplayName("Empty file")
    void testSave1() {
        r.writeAll();
        try(BufferedReader b=new BufferedReader(new FileReader(path))){
            assertNull(b.readLine(), "something extra was written in the file");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Some data in file")
    void testSave2() {
        Payment toAdd=new Payment(0, PaymentType.Card,Math.PI);
        r.add(toAdd);
        r.writeAll();
        try(BufferedReader b=new BufferedReader(new FileReader(path))){
            assertEquals(toAdd.toString(),b.readLine(), "something different was written in the file");
            assertNull(b.readLine(), "something extra was written in the file");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Re-read file")
    void testSave3() {
        Payment toAdd=new Payment(0, PaymentType.Card,Math.PI);
        r.add(toAdd);
        r.writeAll();
        r=new PaymentRepository(path);
        r.add(toAdd);
        r.writeAll();
        try(BufferedReader b=new BufferedReader(new FileReader(path))){
            assertEquals(toAdd.toString(),b.readLine(), "something different was written in the file");
            assertEquals(toAdd.toString(),b.readLine(), "something different was written in the file");
            assertNull(b.readLine(), "something extra was written in the file");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Invalid file")
    void testSave4() {
        assertThrows(PaymentRepository.DataFileFormatException.class,()-> {
            try(BufferedWriter b=new BufferedWriter(new FileWriter(path))){
                b.write("invalid data");
            } catch (IOException e) {
                fail(e.getMessage());
            }
            r=new PaymentRepository(path);
        });
    }

    @RepeatedTest(3)
    @Tag("BVA")
    void BVA_valid_1(RepetitionInfo repInfo){
        assertDoesNotThrow(()->r.add(new Payment(repInfo.getCurrentRepetition()-1,PaymentType.Card,10)));
        assertEquals(1,r.getAll().size());
        assertEquals(new Payment(repInfo.getCurrentRepetition()-1,PaymentType.Card,10),r.getAll().get(0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    @Tag("BVA")
    void BVA_invalid_1(int number){
        assertThrows(RuntimeException.class,()-> r.add(new Payment(number,PaymentType.Cash,10)));
    }

    @Test
    @Tag("BVA")
    void BVA_valid_2(){
        assertDoesNotThrow(()->r.add(new Payment(5,PaymentType.Card,0)));
    }
    @Test
    @Tag("BVA")
    void BVA_invalid_2() {
        assertThrows(RuntimeException.class, () -> r.add(new Payment(5, PaymentType.Cash, -0.000001)));
    }

    @Test
    @Disabled
    void aisjduhaoips(){
        fail();
    }


    @Test
    @Tag("ECP")
    void ECP_valid_1(){
        assertDoesNotThrow(()->r.add(new Payment(56,PaymentType.Card,41)));
        assertEquals(1,r.getAll().size());
        assertEquals(new Payment(56,PaymentType.Card,41),r.getAll().get(0));
    }
    @Test
    @Tag("ECP")
    void ECP_invalid_1(){
        assertThrows(RuntimeException.class,()-> r.add(new Payment(-1,PaymentType.Cash,10)));
    }
    @Test
    @Tag("ECP")
    void ECP_invalid_2(){
        assertThrows(RuntimeException.class,()-> r.add(new Payment(-9999,PaymentType.Cash,-9999)));
    }
}