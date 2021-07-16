package matsim.basic;

import org.junit.Test;
import static org.junit.Assert.*;

public class populationTest {

    @Test
    public void m1() {
        //
        var hm=new population();
        String actual=hm.m1();//实际值
        String expected="hello";//期望值
        assertEquals(expected, actual);//断言
    }
}