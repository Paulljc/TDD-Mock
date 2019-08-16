package parking;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
        Car myCar = new Car("myCarA");
        ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);
        parkingLot.getParkedCars().add(new Car("othercar"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(true).when(spyVipParkingStrategy).isAllowOverPark(myCar);

        Receipt receipt = spyVipParkingStrategy.park(parkingLots, myCar);

        assertEquals("myCarA", receipt.getCarName());
        assertEquals("Parking Lot 1", receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        Car myCar = new Car("myCar");
        ParkingLot parkingLot = new ParkingLot("Parking Lot 1", 1);
        parkingLot.getParkedCars().add(new Car("othercar"));
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot);

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());
        doReturn(false).when(spyVipParkingStrategy).isAllowOverPark(myCar);

        Receipt receipt = spyVipParkingStrategy.park(parkingLots, myCar);

        verify(spyVipParkingStrategy, times(0)).createReceipt(parkingLot, myCar);
        assertEquals("myCar", receipt.getCarName());
        assertEquals("No Parking Lot", receipt.getParkingLotName());

    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car myCar = new Car("myCarA");

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mCarDao = mock(CarDao.class);
        when(mCarDao.isVip("myCarA")).thenReturn(true);
        doReturn(mCarDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(myCar);

        assertFalse(allowOverPark);

    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        Car myCar = new Car("myCarA");

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        CarDao mCarDao = mock(CarDao.class);
        when(mCarDao.isVip("myCarA")).thenReturn(false);
        doReturn(mCarDao).when(spyVipParkingStrategy).getCarDao();

        boolean allowOverPark = spyVipParkingStrategy.isAllowOverPark(myCar);

        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */


    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */


    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
