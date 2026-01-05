public class DiscountCode {
    private final String code;
    private final int percent;
    private boolean active;

    public DiscountCode(String code, int percent, boolean active) {
        this.code = code;
        this.percent = percent;
        this.active = active;
    }

    public String getCode() { return code; }

    public int getPercent() { return percent; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public double apply(double total) {
        if (!active) return total;
        double discounted = total * (1.0 - (percent / 100.0));
        return round2(discounted);
    }

    private double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}

