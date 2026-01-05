import java.util.UUID;

public class CashPayment implements PaymentMethod {
    @Override
    public String getName() { return "Cash"; }

    @Override
    public PaymentReceipt pay(double amount) {
        return new PaymentReceipt(true, getName(), "CASH-" + UUID.randomUUID());
    }
}

