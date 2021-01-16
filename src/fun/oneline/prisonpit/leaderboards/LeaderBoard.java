package fun.oneline.prisonpit.leaderboards;

public class LeaderBoard {

    private String name;
    private int count;

    public LeaderBoard(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

}
