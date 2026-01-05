import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant {
    private final String name;
    private final double taxRate;
    private final List<MenuItem> menu = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final AtomicInteger seq = new AtomicInteger(1000);
    private final List<DiscountCode> discountCodes = new ArrayList<>();

    public Restaurant(String name, double taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getMenuSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(menu));
    }

    public List<Order> getOrdersSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(orders));
    }

    public void addMenuItem(Admin admin, MenuItem item) {
        menu.add(item);
    }

    public boolean removeMenuItemById(Admin admin, String id) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getId().equalsIgnoreCase(id)) {
                menu.remove(i);
                return true;
            }
        }
        return false;
    }

    public MenuItem findMenuItemByIndex(int index1Based) {
        int idx = index1Based - 1;
        if (idx < 0 || idx >= menu.size()) return null;
        return menu.get(idx);
    }

    public void addDiscountCode(Admin admin, DiscountCode code) {
        discountCodes.add(code);
    }

    public DiscountCode findActiveDiscount(String code) {
        if (code == null) return null;
        for (DiscountCode dc : discountCodes) {
            if (dc.isActive() && dc.getCode().equalsIgnoreCase(code.trim())) return dc;
        }
        return null;
    }

    public Order placeOrder(Customer customer, PaymentMethod payment, String discountCodeStr) {
        if (customer == null || customer.isCartEmpty()) return null;

        DiscountCode dc = findActiveDiscount(discountCodeStr);

        double subtotal = customer.cartSubtotal();
        double tax = round2(subtotal * taxRate);
        double beforeDiscount = round2(subtotal + tax);
        double finalTotal = (dc == null) ? beforeDiscount : dc.apply(beforeDiscount);

        PaymentReceipt receipt = payment.pay(finalTotal);

        Order order = new Order(
                "BG-" + seq.getAndIncrement(),
                customer.getUsername(),
                customer.getCartItemsSnapshot(),
                taxRate,
                dc,
                receipt
        );

        orders.add(order);
        customer.addOrderToHistory(order);
        customer.clearCart();
        return order;
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
