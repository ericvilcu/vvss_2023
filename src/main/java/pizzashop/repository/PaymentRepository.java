package pizzashop.repository;

import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class PaymentRepository {

    public static class DataFileFormatException extends RuntimeException{
        DataFileFormatException(Exception e){super(e);}
    }
    private static final String DEFAULT_FILENAME = "data/payments.txt";
    private final String filename;
    private final List<Payment> paymentList;

    public PaymentRepository(){
        this.paymentList = new ArrayList<>();
        filename =DEFAULT_FILENAME;
        readPayments();
    }
    public PaymentRepository(String file){
        this.paymentList = new ArrayList<>();
        filename =file;
        readPayments();
    }

    private void readPayments(){
        File file = new File(filename);
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = null;
            while((line=br.readLine())!=null){
                Payment payment=getPayment(line);
                paymentList.add(payment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Payment getPayment(String line){
        try {
            Payment item;
            if (line==null|| line.equals("")) return null;
            StringTokenizer st=new StringTokenizer(line, ",");
            int tableNumber= Integer.parseInt(st.nextToken());
            String type= st.nextToken();
            double amount = Double.parseDouble(st.nextToken());
            item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
            return item;
        } catch (NumberFormatException|NullPointerException|NoSuchElementException e){
            throw new DataFileFormatException(e);
        }
    }

    public void add(Payment payment){
        paymentList.add(payment);
        writeAll();
    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){
        File file = new File(filename);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for (Payment p:paymentList) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
