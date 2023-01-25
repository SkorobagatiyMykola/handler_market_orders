package ua.skorobahatyi.model;

public enum TypeOrder {
    ASK("ask"),
    BID("bid"),
    SPREAD("spread"),
    ERROR("error");

    private String typeOrder;

    TypeOrder(String typeOrder) {
        this.typeOrder = typeOrder;
    }

    public String getTypeOrder() {
        return typeOrder;
    }
}
