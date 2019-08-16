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

    @Test
    public void testFilterList_givenReportDataAndIsNotSupervisor_thenGetNull() {
        //given
        Sales sales = new Sales();
        SalesReportData salesReportData = mock(SalesReportData.class);
        when(salesReportData.getType()).thenReturn("SalesActivity");
        when(salesReportData.isConfidential()).thenReturn(true);
        List<SalesReportData> reportDataList = Arrays.asList(new SalesReportData(), salesReportData);
        when(salesReportDao.getReportData(sales)).thenReturn(reportDataList);

        //when
        List<SalesReportData> filteredReportDataList = new ArrayList<>();
        filteredReportDataList = salesApp.filterReport(reportDataList, false, filteredReportDataList);

        //then
        Assert.assertEquals(0, filteredReportDataList.size());
    }

	@Test
	public void testGenerateTempList_givenThreeReportDataAndMaxRowIs4_thenGenerateThreeTempList(){
		//given
		Sales sales = new Sales();
		List<SalesReportData> reportDataList = Arrays.asList(new SalesReportData(), new SalesReportData(), new SalesReportData());
		when(salesReportDao.getReportData(sales)).thenReturn(reportDataList);

		//when
		List<SalesReportData> filteredReportDataList = new ArrayList<>();
		filteredReportDataList = salesApp.generateTempList(4, reportDataList);

		//then
		Assert.assertEquals(3, filteredReportDataList.size());
	}

	@Test
	public void testGenerateTempList_givenThreeReportDataAndMaxRowIs2_thenGenerateTwoTempList(){
		//given
		Sales sales = new Sales();
		List<SalesReportData> reportDataList = Arrays.asList(new SalesReportData(), new SalesReportData(), new SalesReportData());
		when(salesReportDao.getReportData(sales)).thenReturn(reportDataList);

		//when
		List<SalesReportData> filteredReportDataList = new ArrayList<>();
		filteredReportDataList = salesApp.generateTempList(2, reportDataList);

		//then
		Assert.assertEquals(2, filteredReportDataList.size());
	}

	
}
