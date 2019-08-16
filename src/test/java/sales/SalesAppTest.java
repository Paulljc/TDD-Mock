package sales;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
    @Mock
    SalesDao salesDao;
    @Mock
    SalesReportDao salesReportDao;
    @Mock
    EcmService ecmService;

    @Test
    public void testValidateDate() {
        //given
        SalesApp salesApp = new SalesApp();
        Sales sales = spy(Sales.class);
        doReturn(new Date(new Date().getTime() + 60 * 60 * 1000)).when(sales).getEffectiveTo();
        doReturn(new Date(new Date().getTime() - 60 * 60 * 1000)).when(sales).getEffectiveFrom();
        //when
        boolean isValidDate = salesApp.validateDate(sales);
        //then
        Assert.assertFalse(isValidDate);
    }

}
