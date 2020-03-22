package principle.dependenceinversion;

/**
 * @description：TODO
 */
public class JavaCourse implements Course{
    @Override
    public void studyCourse() {
        System.out.println("学习JAVA中");
    }
}
