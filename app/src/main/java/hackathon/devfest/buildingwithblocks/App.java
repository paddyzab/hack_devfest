package hackathon.devfest.buildingwithblocks;

public class App {

    private static IHouseSpec spec=new MockHouseSpec();

    public static IHouseSpec getSpec() {
        return spec;
    }

    public static void setSpec(IHouseSpec newSpec){
        spec=newSpec;
    }
}
