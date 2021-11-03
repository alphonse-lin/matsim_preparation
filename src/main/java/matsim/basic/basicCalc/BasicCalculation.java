package matsim.basic.basicCalc;

import matsim.basic.publicTrans.SingleVehicle;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BasicCalculation {
    public static double sum(double[] array){
        double result=0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        return result;
    }

    public static int sum(int[] array){
        IntStream stream = Arrays.stream(array);
        return stream.sum();
    }
}
