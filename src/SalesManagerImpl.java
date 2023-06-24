import java.io.*;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class SalesManagerImpl implements SalesManager {

    static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public List<Sale> readSalesData() throws IOException, ParseException {
        List<Sale> sales = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("src/data.csv"));
        String headers = br.readLine();
        String line = br.readLine();
        while (line != null) {
            String[] attributes = line.split(",");
            Sale sale = createSale(attributes);
            sales.add(sale);
            line = br.readLine();
        }
        return sales;
    }

    public void generateReport(Double totalRevenue,
                               Long numberOfUniqueCustomers,
                               List<String> mostPopularItemsId,
                               Date dateWithHighestRevenue
    ) throws IOException {
        Format formatter = new SimpleDateFormat(DATE_FORMAT);
        String dateWithHighestRevenueAsStr = formatter.format(dateWithHighestRevenue);

        BufferedWriter writer = new BufferedWriter(new FileWriter("src/report.txt"));
        writer.write("Total Revenue: " + totalRevenue + '\n');
        writer.write("Unique Customers: " + numberOfUniqueCustomers + '\n');
        writer.write("Most Popular Items: " + mostPopularItemsId + '\n');
        writer.write("Date with Highest Revenue: " + dateWithHighestRevenueAsStr + '\n');
        writer.close();
    }


    private Sale createSale(String[] metadata) throws ParseException {
        String transactionId = metadata[0];
        String customerId = metadata[1];
        String itemId = metadata[2];
        Date transactionDate = new SimpleDateFormat(DATE_FORMAT).parse(metadata[3]);
        Double itemPrice = Double.parseDouble(metadata[4]);
        Integer itemQuantity = Integer.parseInt(metadata[5]);
        return new Sale(transactionId, customerId, itemId, transactionDate, itemPrice, itemQuantity);
    }

    @Override
    public Double calculateTotalRevenue(List<Sale> sales) {
        return sales.stream().map(Sale::getAllPrice).reduce(0.0, Double::sum);
    }

    @Override
    public Long numberOfUniqueCustomers(List<Sale> sales) {
        return sales.stream().map(Sale::getCustomerId).distinct().count();
    }

    @Override
    public List<String> mostPopularItemsId(List<Sale> sales) {
        Map<String, List<Sale>> itemSales = sales.stream().collect(Collectors.groupingBy(Sale::getItemId));
        int maxSize = 0;
        List<String> mostPopularItemIds = new ArrayList<>();
        for (var entry : itemSales.entrySet()) {
            if(entry.getValue().size() > maxSize) {
                maxSize = entry.getValue().size();
                mostPopularItemIds.clear();
                mostPopularItemIds.add(entry.getKey());
            }
            else if(entry.getValue().size() == maxSize) {
                mostPopularItemIds.add(entry.getKey());
            }
        }
        return mostPopularItemIds;
    }

    @Override
    public Date dateWithHighestRevenue(List<Sale> sales) {
        try {
            Map<Date, List<Sale>> dateSales = sales.stream()
                .collect(Collectors.groupingBy(Sale::getTransactionDate, TreeMap::new, Collectors.toList()));
            double dateRevenue = 0.0;
            Date dateWithHighestRevenue = sales.get(0).getTransactionDate();
            for (var entry : dateSales.entrySet()) {
                Double currentDateRevenue = calculateTotalRevenue(entry.getValue());
                if(Double.compare(currentDateRevenue, dateRevenue) >= 0) {
                    dateRevenue = currentDateRevenue;
                    dateWithHighestRevenue = entry.getKey();
                }
            }
            return dateWithHighestRevenue;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
