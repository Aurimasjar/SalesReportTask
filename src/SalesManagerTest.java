import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class SalesManagerTest {

    private SalesManager salesManager;

    static final double EPSILON = 1e-09;

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    @Before
    public void setUp() throws Exception {
        salesManager = new SalesManagerImpl();
    }

    @After
    public void tearDown() throws Exception {
        salesManager = null;
    }

    @Test
    public void testCalculateTotalRevenue() {
        List<Sale> sales = new ArrayList<>() {};
        assertEquals(0, salesManager.calculateTotalRevenue(sales), EPSILON);

        sales.add(new Sale("1001", "cust-001", "product-001", parseDate("2022-01-01"), 12.50, 3));
        assertEquals(37.50, salesManager.calculateTotalRevenue(sales), EPSILON);

        sales.add(new Sale("1002", "cust-002", "product-002", parseDate("2022-01-02"), 1.50, 2));
        assertEquals(40.50, salesManager.calculateTotalRevenue(sales), EPSILON);
    }

    @Test
    public void testNumberOfUniqueCustomers() {
        List<Sale> sales = new ArrayList<>() {};
        assertEquals(Long.valueOf(0), salesManager.numberOfUniqueCustomers(sales));

        sales.add(new Sale("1001", "cust-001", "product-001", parseDate("2022-01-01"), 12.50, 3));
        assertEquals(Long.valueOf(1), salesManager.numberOfUniqueCustomers(sales));

        sales.add(new Sale("1002", "cust-001", "product-002", parseDate("2022-01-02"), 1.50, 2));
        assertEquals(Long.valueOf(1), salesManager.numberOfUniqueCustomers(sales));

        sales.add(new Sale("1002", "cust-002", "product-002", parseDate("2022-01-02"), 1.50, 2));
        assertEquals(Long.valueOf(2), salesManager.numberOfUniqueCustomers(sales));
    }

    @Test
    public void testMostPopularItemsId() {
        List<Sale> sales = new ArrayList<>() {};
        assertArrayEquals(new String[] {}, salesManager.mostPopularItemsId(sales).toArray());

        sales.add(new Sale("1001", "cust-001", "product-001", parseDate("2022-01-01"), 12.50, 3));
        assertArrayEquals(new String[] { "product-001" }, salesManager.mostPopularItemsId(sales).toArray());

        sales.add(new Sale("1002", "cust-002", "product-002", parseDate("2022-01-02"), 1.50, 2));
        assertArrayEquals(new String[] { "product-002", "product-001" }, salesManager.mostPopularItemsId(sales).toArray());

        sales.add(new Sale("1002", "cust-002", "product-002", parseDate("2022-01-02"), 1.50, 2));
        assertArrayEquals(new String[] { "product-002" }, salesManager.mostPopularItemsId(sales).toArray());
    }

    @Test
    public void testDateWithHighestRevenue() {
        List<Sale> sales = new ArrayList<>() {};
        assertNull(salesManager.dateWithHighestRevenue(sales));

        sales.add(new Sale("1001", "cust-001", "product-001", parseDate("2022-01-01"), 12.50, 3));
        assertEquals(parseDate("2022-01-01"), salesManager.dateWithHighestRevenue(sales));

        sales.add(new Sale("1001", "cust-001", "product-001", parseDate("2022-01-02"), 1.50, 2));
        assertEquals(parseDate("2022-01-01"), salesManager.dateWithHighestRevenue(sales));

        sales.add(new Sale("1001", "cust-001", "product-001", parseDate("2022-01-02"), 12.50, 3));
        assertEquals(parseDate("2022-01-02"), salesManager.dateWithHighestRevenue(sales));
    }
}
