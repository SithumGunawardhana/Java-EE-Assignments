package dto;

public class ItemDTO {
    private String code;
    private String name;
    private int qty;
    private double uniPrice;

    public ItemDTO() {
    }

    public ItemDTO(String code, String name, int qty, double uniPrice) {
        this.code = code;
        this.name = name;
        this.qty = qty;
        this.uniPrice = uniPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUniPrice() {
        return uniPrice;
    }

    public void setUniPrice(double uniPrice) {
        this.uniPrice = uniPrice;
    }
}
