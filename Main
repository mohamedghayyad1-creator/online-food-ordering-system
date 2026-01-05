import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        Restaurant restaurant = new Restaurant("Burger Galaxy", 0.08);

        Admin admin = new Admin("admin", "1234", "Restaurant Office", "+90-534-717-3537");
        Customer customer = new Customer("customer", "1234", "Istanbul", "+90-534-717-3537");

        seedInitialData(restaurant, admin);

        while (true) {
            System.out.println("\n===== " + restaurant.getName() + " =====");
            System.out.println("1) Login as Admin");
            System.out.println("2) Login as Customer");
            System.out.println("3) Exit");
            System.out.print("Choose: ");
            int choice = readInt();

            if (choice == 1) {
                if (login(admin)) adminMenu(restaurant, admin);
                else System.out.println("Login failed.");
            } else if (choice == 2) {
                if (login(customer)) customerMenu(restaurant, customer);
                else System.out.println("Login failed.");
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu(Restaurant restaurant, Admin admin) {
        while (true) {
            System.out.println("\n----- ADMIN MENU -----");
            System.out.println("1) View Menu Items");
            System.out.println("2) Add Menu Item");
            System.out.println("3) Remove Menu Item (by ID)");
            System.out.println("4) Add Discount Code");
            System.out.println("5) View All Orders");
            System.out.println("6) Update Order Status");
            System.out.println("7) Logout");
            System.out.print("Choose: ");
            int choice = readInt();

            if (choice == 1) {
                printMenu(restaurant.getMenuSnapshot());
            } else if (choice == 2) {
                System.out.print("Item ID (e.g., B01, D02): ");
                String id = readLineNonEmpty();
                System.out.print("Name: ");
                String name = readLineNonEmpty();
                System.out.print("Price (TL): ");
                double price = readDouble();
                System.out.print("Category (Burgers/Appetizers/Drinks/Desserts): ");
                String cat = readLineNonEmpty();
                restaurant.addMenuItem(admin, new MenuItem(id, name, price, cat));
                System.out.println("Added.");
            } else if (choice == 3) {
                printMenu(restaurant.getMenuSnapshot());
                System.out.print("Enter ID to remove: ");
                String id = readLineNonEmpty();
                boolean ok = restaurant.removeMenuItemById(admin, id);
                System.out.println(ok ? "Removed." : "Not found.");
            } else if (choice == 4) {
                System.out.print("Discount code (e.g., NJ521): ");
                String code = readLineNonEmpty();
                System.out.print("Percent (e.g., 20): ");
                int percent = readInt();
                restaurant.addDiscountCode(admin, new DiscountCode(code, percent, true));
                System.out.println("Discount code added.");
            } else if (choice == 5) {
                printOrders(restaurant.getOrdersSnapshot());
            } else if (choice == 6) {
                List<Order> orders = restaurant.getOrdersSnapshot();
                if (orders.isEmpty()) {
                    System.out.println("No orders yet.");
                    continue;
                }
                printOrders(orders);
                System.out.print("Choose order number to update: ");
                int idx = readInt() - 1;
                if (idx < 0 || idx >= orders.size()) {
                    System.out.println("Invalid order number.");
                    continue;
                }
                Order order = orders.get(idx);
                System.out.println("1) PENDING");
                System.out.println("2) PREPARING");
                System.out.println("3) ON_THE_WAY");
                System.out.println("4) DELIVERED");
                System.out.print("New status: ");
                int s = readInt();
                if (s == 1) order.setStatus(OrderStatus.PENDING);
                else if (s == 2) order.setStatus(OrderStatus.PREPARING);
                else if (s == 3) order.setStatus(OrderStatus.ON_THE_WAY);
                else if (s == 4) order.setStatus(OrderStatus.DELIVERED);
                else System.out.println("Invalid status choice.");
                System.out.println("Updated.");
            } else if (choice == 7) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerMenu(Restaurant restaurant, Customer customer) {
        while (true) {
            System.out.println("\n----- CUSTOMER MENU -----");
            System.out.println("1) View Menu");
            System.out.println("2) Add Items to Cart (multi + qty)");
            System.out.println("3) View Cart");
            System.out.println("4) Remove Item from Cart");
            System.out.println("5) Checkout (Place Order)");
            System.out.println("6) My Orders");
            System.out.println("7) Logout");
            System.out.print("Choose: ");
            int choice = readInt();

            if (choice == 1) {
                printMenu(restaurant.getMenuSnapshot());
            } else if (choice == 2) {
                List<MenuItem> menu = restaurant.getMenuSnapshot();
                if (menu.isEmpty()) {
                    System.out.println("Menu is empty.");
                    continue;
                }
                printMenu(menu);
                System.out.println("Enter items like: 1 3 5  OR  1x2 3x1 5x4  OR  1x2,3x1,5x4");
                System.out.print("Your selection: ");
                String line = readLineNonEmpty();
                int added = addManyToCart(restaurant, customer, line);
                System.out.println("Added items: " + added);
            } else if (choice == 3) {
                printCart(customer.getCartItemsSnapshot());
                System.out.println("Subtotal: " + String.format("%.2f TL", customer.cartSubtotal()));
            } else if (choice == 4) {
                printCart(customer.getCartItemsSnapshot());
                if (customer.isCartEmpty()) {
                    System.out.println("Cart is empty.");
                    continue;
                }
                System.out.print("Remove item number: ");
                int r = readInt();
                boolean ok = customer.removeFromCart(r);
                System.out.println(ok ? "Removed." : "Invalid index.");
            } else if (choice == 5) {
                if (customer.isCartEmpty()) {
                    System.out.println("Cart is empty.");
                    continue;
                }
                PaymentMethod pm = choosePayment();
                System.out.print("Discount code (or press Enter to skip): ");
                String code = readLineAllowEmpty();
                if (code.isBlank()) code = null;

                Order order = restaurant.placeOrder(customer, pm, code);
                if (order == null) System.out.println("Could not place order.");
                else System.out.println(order.summary());
            } else if (choice == 6) {
                List<Order> my = customer.getOrdersSnapshot();
                if (my.isEmpty()) System.out.println("No orders yet.");
                else for (Order o : my) System.out.println(o.summary());
            } else if (choice == 7) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static int addManyToCart(Restaurant restaurant, Customer customer, String line) {
        String normalized = line.replace(",", " ").trim();
        String[] tokens = normalized.split("\\s+");
        int added = 0;

        for (String t : tokens) {
            if (t.isBlank()) continue;

            int idx;
            int qty = 1;

            String lower = t.toLowerCase();
            if (lower.contains("x")) {
                String[] parts = lower.split("x");
                if (parts.length != 2) continue;
                idx = safeInt(parts[0]);
                qty = safeInt(parts[1]);
            } else if (lower.contains(":")) {
                String[] parts = lower.split(":");
                if (parts.length != 2) continue;
                idx = safeInt(parts[0]);
                qty = safeInt(parts[1]);
            } else {
                idx = safeInt(lower);
                qty = 1;
            }

            if (idx <= 0) continue;
            if (qty <= 0) qty = 1;

            MenuItem item = restaurant.findMenuItemByIndex(idx);
            if (item == null) continue;

            customer.addToCart(item, qty);
            added++;
        }

        return added;
    }

    private static int safeInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private static boolean login(User user) {
        System.out.print("Username: ");
        String u = readLineNonEmpty();
        System.out.print("Password: ");
        String p = readLineNonEmpty();
        return user.getUsername().equals(u) && user.checkPassword(p);
    }

    private static PaymentMethod choosePayment() {
        while (true) {
            System.out.println("\nPayment Methods:");
            System.out.println("1) Cash");
            System.out.println("2) Credit Card");
            System.out.println("3) Wallet");
            System.out.print("Choose: ");
            int p = readInt();
            if (p == 1) return new CashPayment();
            if (p == 2) {
                System.out.print("Card number: ");
                String card = readLineNonEmpty();
                return new CardPayment(card);
            }
            if (p == 3) {
                System.out.print("Wallet name: ");
                String w = readLineNonEmpty();
                return new WalletPayment(w);
            }
            System.out.println("Invalid payment choice.");
        }
    }

    private static void printMenu(List<MenuItem> menu) {
        System.out.println("\n----- MENU -----");
        if (menu.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        String current = "";
        for (int i = 0; i < menu.size(); i++) {
            MenuItem it = menu.get(i);
            if (!it.getCategory().equalsIgnoreCase(current)) {
                current = it.getCategory();
                System.out.println("\n[" + current + "]");
            }
            System.out.println((i + 1) + ") " + it.getDisplayName() + " - " + String.format("%.2f TL", it.getPrice()) + " | ID: " + it.getId());
        }
    }

    private static void printCart(List<CartItem> cart) {
        System.out.println("\n----- CART -----");
        if (cart.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int i = 0; i < cart.size(); i++) {
            CartItem ci = cart.get(i);
            Orderable it = ci.getItem();
            System.out.println((i + 1) + ") " + it.getDisplayName() + " x" + ci.getQuantity() + " - " +
                    String.format("%.2f TL", it.getPrice()) + " => " + String.format("%.2f TL", ci.lineTotal()));
        }
    }

    private static void printOrders(List<Order> orders) {
        System.out.println("\n----- ALL ORDERS -----");
        if (orders.isEmpty()) {
            System.out.println("(no orders)");
            return;
        }
        for (int i = 0; i < orders.size(); i++) {
            Order o = orders.get(i);
            System.out.println((i + 1) + ") " + o.getId() + " | Status: " + o.getStatus() + " | Total: " + String.format("%.2f TL", o.getTotalAfterDiscount()));
        }
    }

    private static void seedInitialData(Restaurant restaurant, Admin admin) {
        restaurant.addMenuItem(admin, new MenuItem("B01", "Classic Burger", 120, "Burgers"));
        restaurant.addMenuItem(admin, new MenuItem("B02", "Double Cheese Burger", 150, "Burgers"));
        restaurant.addMenuItem(admin, new MenuItem("B03", "Galaxy Spicy Burger", 165, "Burgers"));
        restaurant.addMenuItem(admin, new MenuItem("B04", "BBQ Burger", 180, "Burgers"));
        restaurant.addMenuItem(admin, new MenuItem("B05", "Chicken Crispy Burger", 125, "Burgers"));

        restaurant.addMenuItem(admin, new MenuItem("A01", "French Fries", 40, "Appetizers"));
        restaurant.addMenuItem(admin, new MenuItem("A02", "Curly Fries", 50, "Appetizers"));
        restaurant.addMenuItem(admin, new MenuItem("A03", "Onion Rings", 45, "Appetizers"));
        restaurant.addMenuItem(admin, new MenuItem("A04", "Cheese Fries", 60, "Appetizers"));

        restaurant.addMenuItem(admin, new MenuItem("D01", "Coca Cola", 25, "Drinks"));
        restaurant.addMenuItem(admin, new MenuItem("D02", "Sprite", 25, "Drinks"));
        restaurant.addMenuItem(admin, new MenuItem("D03", "Iced Tea Lemon", 35, "Drinks"));
        restaurant.addMenuItem(admin, new MenuItem("D04", "Water", 10, "Drinks"));

        restaurant.addMenuItem(admin, new MenuItem("S01", "Chocolate Lava Cake", 55, "Desserts"));
        restaurant.addMenuItem(admin, new MenuItem("S02", "Brownie", 45, "Desserts"));

        restaurant.addDiscountCode(admin, new DiscountCode("NJ521", 20, true));
    }

    private static int readInt() {
        while (true) {
            String s = in.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Enter a number: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            String s = in.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Enter a number: ");
            }
        }
    }

    private static String readLineNonEmpty() {
        while (true) {
            String s = in.nextLine();
            if (s != null) s = s.trim();
            if (s != null && !s.isEmpty()) return s;
            System.out.print("Enter a value: ");
        }
    }

    private static String readLineAllowEmpty() {
        String s = in.nextLine();
        return s == null ? "" : s.trim();
    }
}
