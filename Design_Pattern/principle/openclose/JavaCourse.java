package principle.openclose;

import javax.naming.Name;
import javax.xml.bind.PrintConversionEvent;

/**
 * @description：TODO
 */
public class JavaCourse implements Course{
    private Integer Id;
    private String name;
    private Double price;

    public JavaCourse(Integer id, String name, Double price) {
        Id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public Integer getID() {
        return Id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
        return price;
    }

}
