import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SalesManager salesManager = new SalesManagerImpl();
        try {
            List<Sale> sales = salesManager.readSalesData();
            Double totalRevenue = salesManager.calculateTotalRevenue(sales);
            Long numberOfUniqueCustomers = salesManager.numberOfUniqueCustomers(sales);
            List<String> mostPopularItemsId = salesManager.mostPopularItemsId(sales);
            Date dateWithHighestRevenue = salesManager.dateWithHighestRevenue(sales);
            salesManager.generateReport(totalRevenue, numberOfUniqueCustomers, mostPopularItemsId, dateWithHighestRevenue);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}