package register.modules.simple.entity.employee;

public enum Company {
    IBM("IBM"),
    APPLE("Apple"),
    MICROSOFT("Microsoft"),
    ADIDAS("Adidas"),
    NIKE("Nike");

    private final String value;

    Company(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
