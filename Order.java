import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String id;
    private final String customerUsername;
    private final List<CartItem> items;
    private final LocalDateTime createdAt;
    private final double subtotal;
    private final double tax;
    private final double discountAmount;
    private final double totalAfterDiscount;
    private final PaymentReceipt receipt;
    private OrderStatus status;

    public Order(String id,
                 String customerUsername,
                 List<CartItem> items,
                 double taxRate,
                 DiscountCode discount,
                 PaymentReceipt receipt) {

        this.id = id;
        this.customerUsername = customerUsername;
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
        this.createdAt = LocalDateTime.now();

        double sub = 0;
        for (CartItem ci : this.items) sub += ci.lineTotal();
        this.subtotal = round2(sub);

        this.tax = round2(this.subtotal * taxRate);

        double beforeDiscount = round2(this.subtotal + this.tax);
        double afterDiscount = (discount == null) ? beforeDiscount : discount.apply(beforeDiscount);
        this.totalAfterDiscount = round2(afterDiscount);
        this.discountAmount = round2(beforeDiscount - this.totalAfterDiscount);

        this.receipt = receipt;
        this.status = OrderStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public double getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== ORDER SUMMARY =====\n");
        sb.append("Order ID: ").append(id).append("\n");
        sb.append("Customer: ").append(customerUsername).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("-------------------------\n");
        for (int i = 0; i < items.size(); i++) {
            CartItem ci = items.get(i);
            Orderable it = ci.getItem();
            sb.append(i + 1).append(") ").append(it.getDisplayName())
                    .append(" x").append(ci.getQuantity())
                    .append(" - ").append(String.format("%.2f TL", it.getPrice()))
                    .append(" => ").append(String.format("%.2f TL", ci.lineTotal()))
                    .append(" (").append(it.getCategory()).append(")\n");
        }
        sb.append("-------------------------\n");
        sb.append("Subtotal: ").append(String.format("%.2f TL", subtotal)).append("\n");
        sb.append("Tax: ").append(String.format("%.2f TL", tax)).append("\n");
        sb.append("Discount: ").append(String.format("%.2f TL", discountAmount)).append("\n");
        sb.append("Total: ").append(String.format("%.2f TL", totalAfterDiscount)).append("\n");
        sb.append("Payment: ").append(receipt.getMethodName()).append("\n");
        sb.append("Ref: ").append(receipt.getReference()).append("\n");
        return sb.toString();
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

