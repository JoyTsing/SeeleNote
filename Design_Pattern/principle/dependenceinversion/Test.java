package principle.dependenceinversion;

/**
 * @description：TODO
 */
public class Test {
    public static void main(String[] args) {

//        v1
//        User user=new User();
//        user.studyJavaCourse();
//        user.studyFECourse();

        //v2
//        User user=new User();
//        user.studyCourse(new JavaCourse());
//        user.studyCourse(new FECourse());

        //v3
//        User user=new User(new JavaCourse());
//        user.studyCourse();

        User user = new User(new JavaCourse());
        user.studyCourse();
        user.setCourse(new FECourse());
        user.studyCourse();
    }
}
