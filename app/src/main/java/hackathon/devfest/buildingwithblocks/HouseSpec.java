package hackathon.devfest.buildingwithblocks;

/**
 * Created by lukasz.
 */
public class HouseSpec {
    public final static int SIZE=10;
    public int[][] plan = new int[10][10];

    public HouseSpec() {
                                                                        plan[1][5] = 1;
                                                        plan[2][4] = 1; plan[2][5] = 1; plan[2][6] = 1;
                                        plan[3][3] = 1; plan[3][4] = 1; plan[3][5] = 1; plan[3][6] = 1; plan[3][7] = 1;
                        plan[4][2] = 1; plan[4][3] = 1; plan[4][4] = 1; plan[4][5] = 1; plan[4][6] = 1; plan[4][7] = 1; plan[4][8] = 1;
        plan[5][1] = 1; plan[5][2] = 1; plan[5][3] = 1; plan[5][4] = 1; plan[5][5] = 1; plan[5][6] = 1; plan[5][7] = 1; plan[5][8] = 1; plan[5][9] = 1;
                                        plan[6][3] = 1; plan[6][4] = 1; plan[6][5] = 1; plan[6][6] = 1; plan[6][7] = 1;
                                        plan[7][3] = 1; plan[7][4] = 1; plan[7][5] = 1; plan[7][6] = 1; plan[7][7] = 1;
                                        plan[8][3] = 1; plan[8][4] = 1; plan[8][5] = 1; plan[8][6] = 1; plan[8][7] = 1;
                                        plan[9][3] = 1; plan[9][4] = 1; plan[9][5] = 1; plan[9][6] = 1; plan[9][7] = 1;

    }

    public int[][] getPlan() {
        return plan;
    }


}
