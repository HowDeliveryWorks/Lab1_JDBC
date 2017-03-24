package Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by LevelNone on 23.03.2017.
 */
public class Order implements Serializable {

    public Order(UUID uuid, int sum) {
        this.uuid = uuid;
        this.sum = sum;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    private UUID uuid;
    private int sum;

    public Order(){
        uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (sum != order.sum) return false;
        return uuid.equals(order.uuid);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + sum;
        return result;
    }

    @Override
    public String toString(){
        return "Order sum: " + sum + "; UUID: " + uuid.toString();
    }
}
