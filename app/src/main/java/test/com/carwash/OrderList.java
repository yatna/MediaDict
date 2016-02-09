package test.com.carwash;

/**
 * Created by nairit on 30/6/15.
 */
public class OrderList {
    String order_id="",order_type="",amount="",timestamp="",status="";
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public void setOrder_type(String order_type){this.order_type=order_type;}
    public void setAmount(String amount){this.amount=amount;}
    public void setTimestamp(String timestamp){this.timestamp=timestamp;}
    public void setStatus(String status){this.status=status;}
    public String getOrder_type() {
        return this.order_type;
    }
    public String getOrder_id(){return this.order_id;}
    public String getAmount(){return this.amount;}
    public String getTimestamp(){return this.timestamp;}
    public String getStatus(){return this.status;}

}
