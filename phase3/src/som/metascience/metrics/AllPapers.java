package som.metascience.metrics;

import som.metascience.DBInfo;
import som.metascience.MetricData;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.LinkedList;

/**
 * This metric calculates the number of papers per conference per edition (by default, we perform the calculations
 * for the last 5 editions so this metric will actually return 5 values)
 */
public class AllPapers extends SQLMetric {

    /**
     * Constructs the {@link AllPapers} class
     * @param metricData Basic information to calculate the data
     * @param dbInfo Database credentials
     */
    public AllPapers(MetricData metricData, DBInfo dbInfo) {
        super(metricData, dbInfo);
    }

    @Override
    public String getResult() {
        Statement stmt = null;
        ResultSet rs = null;
        List<Integer> papers = new LinkedList<Integer>();
        try {
            String allPapersQuery = "SELECT COUNT(*) as numPapers, year " +
                    "FROM dblp_pub_new " +
                    "WHERE source IN (" + metricData.getSourceInfo() + ") AND source_id IN (" + metricData.getSourceIdInfo() + ") AND type = 'inproceedings' " +
                    "AND calculate_num_of_pages(pages) >= " + Integer.toString(super.FILTER_NUM_PAGES) + " AND year IN (" + toCommaSeparated(metricData.getEditions()) + ") " +
                    "GROUP BY year " +
                    "ORDER BY year DESC;";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(allPapersQuery);

            while (rs.next()) {
                papers.add(rs.getInt("numPapers"));
            }

            rs.close();
            stmt.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String results = "";
        int allPapers = 0;
        for (int p : papers) {
            allPapers = allPapers + p;
            results += p + ",";
        }

        // return results + allPapers; // jlcanovas: we only return the per-year values
        return results.substring(0, results.length() - 1);
    }
}
