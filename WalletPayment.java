import java.util.UUID;

public class WalletPayment implements PaymentMethod {
    private final String walletName;

    public WalletPayment(String walletName) {
        this.walletName = (walletName == null || walletName.isBlank()) ? "Wallet" : walletName;
    }

    @Override
    public String getName() { return walletName; }

    @Override
    public PaymentReceipt pay(double amount) {
        return new PaymentReceipt(true, walletName, "WALLET-" + UUID.randomUUID());
    }
}

