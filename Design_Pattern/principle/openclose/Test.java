package principle.openclose;

/**
 * @description：TODO
 */
public class Test {
    public static void main(String[] args) {
        Course iCourse=new JavaDiscountCourse(96,"JAVA",348d);
        JavaDiscountCourse javaCourse=(JavaDiscountCourse)iCourse;
        System.out.println("ID"+javaCourse.getID()+" NAME"+javaCourse.getName()+" Discount price"+javaCourse.getPrice()+" Price"+javaCourse.getOriginPrice());
    }
}
