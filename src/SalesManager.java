import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface SalesManager {

    List<Sale> readSalesData() throws IOException, ParseException;

    void generateReport(Double totalRevenue,
                        Long numberOfUniqueCustomers,
                        List<String> mostPopularItemsId,
                        Date dateWithHighestRevenue
    ) throws IOException;

    Double calculateTotalRevenue(List<Sale> sales);

    Long numberOfUniqueCustomers(List<Sale> sales);

    List<String> mostPopularItemsId(List<Sale> sales);

    Date dateWithHighestRevenue(List<Sale> sales);
}
