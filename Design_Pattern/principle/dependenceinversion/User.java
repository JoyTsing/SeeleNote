package principle.dependenceinversion;

/**
 * @description：TODO
 */
public class User {
    private Course course;

    public void setCourse(Course course) {
        this.course = course;
    }

    public User(Course course){
        this.course=course;
    }
    public void studyCourse(){
        course.studyCourse();
    }

}
