package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    SalesDao salesDao;
    SalesReportDao salesReportDao;
    EcmService ecmService;

	public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {

        List<String> headers = null;

        if (salesId == null) {
            return;
        }

        Sales sales = salesDao.getSalesBySalesId(salesId);

        if (validateDate(sales)) {
            return;
        }

        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);

        List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();

        filteredReportDataList = filterReport(reportDataList, isSupervisor, filteredReportDataList);

        List<SalesReportData> tempList = generateTempList(maxRow, reportDataList);

        filteredReportDataList = tempList;

		headers = generateHeaders(isNatTrade);

        SalesActivityReport report = this.generateReport(headers, reportDataList);

		transferReportToXml(ecmService, report);

    }

	private void transferReportToXml(EcmService ecmService, SalesActivityReport report) {
		ecmService.uploadDocument(report.toXml());
	}

	protected List<String> generateHeaders(boolean isNatTrade) {
		List<String> headers;
		if (isNatTrade) {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		} else {
			headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
		}
		return headers;
	}

	protected List<SalesReportData> generateTempList(int maxRow, List<SalesReportData> reportDataList) {
        List<SalesReportData> tempList = new ArrayList<>();
        for (int i = 0; i < reportDataList.size() && i < maxRow; i++) {
            tempList.add(reportDataList.get(i));
        }
        return tempList;
    }

    protected List<SalesReportData> filterReport(List<SalesReportData> reportDataList, boolean isSupervisor, List<SalesReportData> filteredReportDataList) {
        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        filteredReportDataList.add(data);
                    }
                } else {
                    filteredReportDataList.add(data);
                }
            }
        }
        return filteredReportDataList;
    }

    protected boolean validateDate(Sales sales) {
        Date today = new Date();
        if (today.after(sales.getEffectiveTo()) || today.before(sales.getEffectiveFrom())) {
            return true;
        }
        return false;
    }

    private SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

}
