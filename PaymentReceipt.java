public class PaymentReceipt {
    private final boolean success;
    private final String methodName;
    private final String reference;

    public PaymentReceipt(boolean success, String methodName, String reference) {
        this.success = success;
        this.methodName = methodName;
        this.reference = reference;
    }

    public boolean isSuccess() { return success; }

    public String getMethodName() { return methodName; }

    public String getReference() { return reference; }
}

