package som.metascience.metrics;


import som.metascience.DBInfo;
import som.metascience.MetricData;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by valerio cosentino <valerio.cosentino@gmail.com> on 15/09/2015.
 */
public class AverageNumberOfAuthorsPerPaper extends SQLMetric {

    public AverageNumberOfAuthorsPerPaper(MetricData metricData, DBInfo dbInfo) {
        super(metricData, dbInfo);
    }


    @Override
    public String getResult() {
        Statement stmt = null;
        ResultSet rs = null;
        int average = 0;
        try {
            String query = "SELECT ROUND(AVG(avg_author_per_paper), 2) as avg " +
                           "FROM _avg_number_authors_per_paper_per_conf_per_year " +
                           "WHERE source IN (" + metricData.getSourceInfo() + ") AND source_id IN (" + metricData.getSourceIdInfo() + ")";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            rs.first();
            average = rs.getInt("avg");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(average);
    }
}