package ua.skorobahatyi.model;

public class Order {
    private Integer size;
    private TypeOrder typeOrder;

    public Order() {
    }

    public Order(Integer size, TypeOrder typeOrder) {
        this.size = size;
        this.typeOrder = typeOrder;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public TypeOrder getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(TypeOrder typeOrder) {
        this.typeOrder = typeOrder;
    }
}
