package dev.chu.memo.z_test.unit_test_bmi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeightTipsTest2 {

    @Mock
    BMI bmi;

    WeightTips weightTips;

    @Before
    public void init(){
        weightTips = new WeightTips( bmi );
    }

    //Testing the normal state
    @Test
    public void getWeightTipNormalTest(){
        when(bmi.calculateBMI()).thenReturn( 19.01f );
        assertEquals( "normal weight",0,weightTips.getTipAboutYourWeight() );
        // Read personal information from
    }

    //Testing the underweight state
    @Test
    public void getWeightTipUnderWeightTest(){
        when(bmi.calculateBMI()).thenReturn( 16.1f );
        assertEquals( "underweight",-1,weightTips.getTipAboutYourWeight() );
        // Read personal information from
    }

    //Testing the overweight state
    @Test
    public void getWeightTipOverWeightTest(){
        when(bmi.calculateBMI()).thenReturn( 26.36f );
        assertEquals( "overweight",1,weightTips.getTipAboutYourWeight() );
        // Read personal information from
    }

    //Testing the obese state
    @Test
    public void getWeightTipObeseTest(){
        when(bmi.calculateBMI()).thenReturn( 32.36f );
        assertEquals( "obese",2,weightTips.getTipAboutYourWeight() );
        // Read personal information from
    }

}