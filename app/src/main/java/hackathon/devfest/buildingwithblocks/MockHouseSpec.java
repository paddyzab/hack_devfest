package hackathon.devfest.buildingwithblocks;

/**
 * Created by lukasz.
 */
public class MockHouseSpec implements IHouseSpec {

    public static final int SIZE = 2;

    public int[][] getPlan() {
        int[][] mock = new int[SIZE][SIZE];

        mock[1][0] = 1;
        mock[1][1] = 1; mock[0][1] = 1;

        return mock;
    }

    @Override
    public int getSize() {
        return 2;
    }
}
