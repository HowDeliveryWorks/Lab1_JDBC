package Data;

import java.io.Serializable;

/**
 * Created by Tanya on 23.03.2017.
 */
public class FoodItem implements Serializable
{
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof FoodItem)) return false;

        FoodItem foodItem = (FoodItem) o;

        if (!name.equals(foodItem.name)) return false;
        return price.equals(foodItem.price);

    }

    @Override
    public int hashCode()
    {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }

    private String name;
    private Integer price;

    public FoodItem(){}

    public FoodItem(String name, Integer price){
        this.name = name;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    @Override
    public String toString(){
        return "Name: " + name + "; Price: " + price;
    }
}
