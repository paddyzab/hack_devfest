package hackathon.devfest.buildingwithblocks;

/**
 * Created by lukasz.
 */
public class TipiHouseSpec implements IHouseSpec {
    public static final int SIZE = 3;

    public int[][] getPlan() {
        int[][] mock = new int[SIZE][SIZE];

                        mock[1][1] = 1;
        mock[2][0] = 1; mock[2][1] = 1; mock[2][2] = 1;

        return mock;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
