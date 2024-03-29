package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private final MenuRepository menuRepo;
    private final PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(Payment payment){
        payRepo.add(payment);
    }


    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        List<Payment> l=getPayments();
        if (l==null)
            return  total;
        if(l.size()==0)
            return total;
        for (int index=0; index < l.size(); index++){
            if (l.get(index).getType().equals(type))
                total+=l.get(index).getAmount();
        }
        return total;
    }

}
