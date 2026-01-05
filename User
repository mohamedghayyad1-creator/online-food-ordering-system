public class User {
    private final String username;
    private String password;
    private String address;
    private String phone;

    public User(String username, String password, String address, String phone) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public String getUsername() { return username; }

    public boolean checkPassword(String raw) {
        return password != null && password.equals(raw);
    }

    public void setPassword(String newPassword) { this.password = newPassword; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }
}

