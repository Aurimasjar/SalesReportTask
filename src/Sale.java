import java.util.Date;

public class Sale {
    private final String transactionId;
    private final String customerId;
    private final String itemId;
    private final Date transactionDate;
    private final Double itemPrice;
    private final Integer itemQuantity;

    Sale(String transactionId, String customerId, String itemId, Date transactionDate, Double itemPrice, Integer itemQuantity) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.itemId = itemId;
        this.transactionDate = transactionDate;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public Double getAllPrice() {
        return itemPrice * itemQuantity;
    }

    public String toString() {
        return "(" + transactionId + ", " + customerId + ", " + itemId + ", " + transactionDate + ", " + itemPrice + ", " + itemQuantity + ")";
    }
}
