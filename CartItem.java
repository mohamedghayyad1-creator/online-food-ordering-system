public class CartItem {
    private final Orderable item;
    private int quantity;

    public CartItem(Orderable item, int quantity) {
        this.item = item;
        this.quantity = Math.max(1, quantity);
    }

    public Orderable getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int q) {
        quantity += Math.max(1, q);
    }

    public double lineTotal() {
        return round2(item.getPrice() * quantity);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

