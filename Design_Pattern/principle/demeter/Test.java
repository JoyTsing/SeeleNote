package principle.demeter;

/**
 * @description：TODO
 */
public class Test {
    public static void main(String[] args) {
        BOSS boss=new BOSS();
        TeamLeader teamLeader=new TeamLeader();
        boss.commandCheckNumber(teamLeader);
    }
}
