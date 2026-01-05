import java.util.UUID;

public class CardPayment implements PaymentMethod {
    private final String last4;

    public CardPayment(String cardNumber) {
        String digits = cardNumber == null ? "" : cardNumber.replaceAll("\\s+", "");
        this.last4 = digits.length() >= 4 ? digits.substring(digits.length() - 4) : "0000";
    }

    @Override
    public String getName() { return "Credit Card (**** " + last4 + ")"; }

    @Override
    public PaymentReceipt pay(double amount) {
        return new PaymentReceipt(true, "Credit Card", "CARD-" + UUID.randomUUID());
    }
}

