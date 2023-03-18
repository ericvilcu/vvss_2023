package pizzashop.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pizzashop.model.PaymentType;
import pizzashop.service.PizzaService;

import java.util.Optional;

public class PaymentAlert {
    private static final Logger log = LogManager.getLogger(PaymentAlert.class);
    private PizzaService service;

    public PaymentAlert(PizzaService service){
        this.service=service;
    }

    public void cardPayment() {
        log.info("--------------------------");
        log.info("Paying by card...");
        log.info("Please insert your card!");
        log.info("--------------------------");
    }
    
    public void cashPayment() {
        log.info("--------------------------");
        log.info("Paying cash...");
        log.info("Please show the cash...!");
        log.info("--------------------------");
    }
    
    public void cancelPayment() {
        log.info("--------------------------");
        log.info("Payment choice needed...");
        log.info("--------------------------");
    }
      public void showPaymentAlert(int tableNumber, double totalAmount ) {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table "+tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if (result.isPresent() && result.get() == cardPayment) {
            cardPayment();
            service.addPayment(tableNumber, PaymentType.Card,totalAmount);
        } else if (result.isPresent() && result.get() == cashPayment) {
            cashPayment();
            service.addPayment(tableNumber, PaymentType.Cash,totalAmount);
        } else {
            cancelPayment();
        }
    }
}
