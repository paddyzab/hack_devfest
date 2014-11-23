package hackathon.devfest.buildingwithblocks;

/**
 * Created by lukasz.
 */
public class MockHouseSpec implements IHouseSpec {
    public int[][] getPlan() {
        int[][] mock = new int[HouseSpec.SIZE][HouseSpec.SIZE];

        mock[8][5] = 1;
        mock[9][5] = 1; mock[9][6] = 1;

        return mock;
    }
}
