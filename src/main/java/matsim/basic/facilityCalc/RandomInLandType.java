package matsim.basic.facilityCalc;

import matsim.basic.basicCalc.WeightRandom;

public class RandomInLandType {
    public WeightRandom<String> C_WR;
    public WeightRandom<String> H_WR;
    public WeightRandom<String> O_WR;
    public WeightRandom<String> M_WR;
    public WeightRandom<String> W_WR;
    public WeightRandom<String> School_WR;

    public RandomInLandType(WeightRandom<String> cwr,WeightRandom<String> hwr, WeightRandom<String> owr,
                            WeightRandom<String> mwr,WeightRandom<String> wwr, WeightRandom<String> school_wr
                            ){
        this.C_WR=cwr;
        this.H_WR=hwr;
        this.O_WR=owr;
        this.M_WR=mwr;
        this.W_WR=wwr;
        this.School_WR=school_wr;
    }

    public RandomInLandType(WeightRandom<String> cwr,WeightRandom<String> hwr, WeightRandom<String> owr,
                            WeightRandom<String> mwr,WeightRandom<String> wwr
    ){
        this.C_WR=cwr;
        this.H_WR=hwr;
        this.O_WR=owr;
        this.M_WR=mwr;
        this.W_WR=wwr;
    }
}
