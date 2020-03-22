package principle.dependenceinversion;

/**
 * @description：TODO
 */
public class FECourse implements Course{

    @Override
    public void studyCourse() {
        System.out.println("学习FE中");
    }
}
