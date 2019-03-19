import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Owner
 */
public class Datatable {

    private List tableData;
    private String query;
    FacesContext context = FacesContext.getCurrentInstance();

    public Datatable() {
        this.createRecords();
    }

    /**
     * @return the tableData
     */
    public List getTableData() {
        return tableData;
    }

    /**
     * @param tableData the tableData to set
     */
    public void setTableData(List tableData) {
        this.tableData = tableData;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    public void createRecords() {
        this.createRecords("");
    }
    
    public void createRecords(String sql) {
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        query = "select * from result left join age_master on age = age_id left join info on pref_id = id left join business_master on business = business_id";
        query += (sql == null || sql.equals("")) ? "" : " where " + sql;
        tableData = new ArrayList();
        try {
            con = ConnectionManager.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);

            while (rs.next()) {
                int inputNumData = rs.getInt("input_num");
                int salaryData = rs.getInt("salary");
                String ageDivisionData = rs.getString("age_division");
                String prefName = rs.getString("name");
                String occupationsData = rs.getString("occupations");
                int healthData = rs.getInt("health");
                int pensionData = rs.getInt("pension");
                int employmentData = rs.getInt("employment");
                int totalSocialData = rs.getInt("total");
                Timestamp inputTimeData = rs.getTimestamp("input_time");
                tableData.add(new InputInfo(inputNumData, salaryData, ageDivisionData,
                        prefName, occupationsData, healthData, pensionData,
                        employmentData, totalSocialData, inputTimeData));
            }
        } catch (SQLException e) {
            context.addMessage("code", new FacesMessage(e.getMessage() + "(DBから値の再取得時)"));
        } finally {
            try {
                rs.close();
                smt.close();
                con.close();
            } catch (SQLException e) {
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException e) {
                } finally {
                    try {
                        if (smt != null) {
                            smt.close();
                        }
                    } catch (SQLException e) {
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                        } catch (SQLException e) {
                        }
                    }
                }
            }
        }
    }
    
    
}