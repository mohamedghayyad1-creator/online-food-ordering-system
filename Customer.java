import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer extends User {
    private final List<CartItem> cart = new ArrayList<>();
    private final List<Order> myOrders = new ArrayList<>();

    public Customer(String username, String password, String address, String phone) {
        super(username, password, address, phone);
    }

    public void addToCart(Orderable item, int qty) {
        int q = Math.max(1, qty);
        for (CartItem ci : cart) {
            if (ci.getItem() instanceof MenuItem && item instanceof MenuItem) {
                if (((MenuItem) ci.getItem()).getId().equalsIgnoreCase(((MenuItem) item).getId())) {
                    ci.addQuantity(q);
                    return;
                }
            }
            if (ci.getItem().getDisplayName().equalsIgnoreCase(item.getDisplayName())
                    && ci.getItem().getCategory().equalsIgnoreCase(item.getCategory())
                    && Math.abs(ci.getItem().getPrice() - item.getPrice()) < 0.000001) {
                ci.addQuantity(q);
                return;
            }
        }
        cart.add(new CartItem(item, q));
    }

    public boolean removeFromCart(int index1Based) {
        int idx = index1Based - 1;
        if (idx < 0 || idx >= cart.size()) return false;
        cart.remove(idx);
        return true;
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }

    public List<CartItem> getCartItemsSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(cart));
    }

    public double cartSubtotal() {
        double sum = 0;
        for (CartItem ci : cart) sum += ci.lineTotal();
        return round2(sum);
    }

    public void addOrderToHistory(Order order) {
        myOrders.add(order);
    }

    public List<Order> getOrdersSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(myOrders));
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

