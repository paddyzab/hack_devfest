package building_with_blocks.hackathon.devfest.buildingwithblocks;

/**
 * Created by lukasz.
 */
public class HouseSpec {
    private int[][] plan = new int[10][10];

    public HouseSpec() {
        plan[5][1] = 1;
        plan[4][2] = 1;
        plan[5][2] = 1;
        plan[6][2] = 1;
        plan[3][3] = 1;
        plan[4][3] = 1;
        plan[5][3] = 1;
        plan[6][3] = 1;
        plan[7][3] = 1;
        plan[2][4] = 1;
        plan[3][4] = 1;
        plan[5][4] = 1;
        plan[6][4] = 1;
        plan[7][4] = 1;
        plan[8][4] = 1;

        plan[1][5] = 1;
        plan[2][5] = 1; plan[2][6] = 1;
        plan[3][5] = 1; plan[3][6] = 1;  plan[3][7] = 1;
        plan[5][6] = 1; plan[3][6] = 1;  plan[3][7] = 1;
        plan[6][7] = 1;
        plan[7][8] = 1;
        plan[8][9] = 1;
    }
}
