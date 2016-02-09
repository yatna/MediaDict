package test.com.carwash;

/**
 * Created by DELL on 6/11/2015.
 */
public class Order {
    String order_type,amount,timestamp,description,address1,address2,model,paymentmode;
    int user_id;

    public Order(String order_type, String amount, int user_id, String timestamp,String description, String address1,String address2,String model,String paymentmode) {

        this.order_type = order_type;
        this.amount = amount;
        this.user_id=user_id;
        this.timestamp = timestamp;
        this.description = description;
        this.address1 = address1;
        this.address2 = address2;
        this.model = model;
        this.paymentmode =paymentmode;
    }


}
