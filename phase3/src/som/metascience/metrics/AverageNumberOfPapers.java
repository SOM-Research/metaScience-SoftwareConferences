package som.metascience.metrics;


import som.metascience.DBInfo;
import som.metascience.MetricData;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by valerio cosentino <valerio.cosentino@gmail.com> on 15/09/2015.
 */
public class AverageNumberOfPapers extends SQLMetric {

    public AverageNumberOfPapers(MetricData metricData, DBInfo dbInfo) {
        super(metricData, dbInfo);
    }


    @Override
    public String getResult() {
        Statement stmt = null;
        ResultSet rs = null;
        int averageNumberOfPapers = 0;
        try {
            String averageNumberOfPapersQuery = "SELECT ROUND(AVG(num_papers), 2) as avg " +
                                                "FROM _num_of_papers_per_conference_per_year " +
                                                "WHERE source IN (" + metricData.getSourceInfo() + ") AND source_id IN (" + metricData.getSourceIdInfo() + ")";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(averageNumberOfPapersQuery);

            rs.first();
            averageNumberOfPapers = rs.getInt("avg");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(averageNumberOfPapers);
    }
}