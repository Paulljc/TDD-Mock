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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {
    @Mock
    SalesDao salesDao;
    @Mock
    SalesReportDao salesReportDao;
    @Mock
    EcmService ecmService;
    @InjectMocks
    SalesApp salesApp = new SalesApp();

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

    @Test
    public void testFilterList_givenReportDataAndIsSupervisor_thenGetFilteredReportDataList() {
        //given
        Sales sales = new Sales();
        SalesReportData salesReportData = mock(SalesReportData.class);
        when(salesReportData.getType()).thenReturn("SalesActivity");
        List<SalesReportData> reportDataList = Arrays.asList(new SalesReportData(), salesReportData);
        when(salesReportDao.getReportData(sales)).thenReturn(reportDataList);

        //when
        List<SalesReportData> filteredReportDataList = new ArrayList<>();
        filteredReportDataList = salesApp.filterReport(reportDataList, true, filteredReportDataList);

        //then
        Assert.assertEquals(1, filteredReportDataList.size());
        Assert.assertEquals("SalesActivity", filteredReportDataList.get(0).getType());
    }

    
}
