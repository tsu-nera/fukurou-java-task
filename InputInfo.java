import java.sql.Timestamp;

/**
 *
 * @author Owner
 */
public class InputInfo {

    private int inputNumData;
    private int salaryData;
    private String ageDivisionData;
    private String occupationsData;
    private String prefNameData;
    private int healthData;
    private int pensionData;
    private int employmentData;
    private int totalSocialData;
    private Timestamp inputTimeData;

    public InputInfo(int inputNumData, int salaryData, String ageDivisionData, String occupationsData, 
            String prefNameData, int healthData, int pensionData, int employmentData,
            int totalSocialData, Timestamp inputTimeData) {
        
        this.inputNumData = inputNumData;
        this.salaryData = salaryData;
        this.ageDivisionData = ageDivisionData;
        this.occupationsData = occupationsData;
        this.prefNameData = prefNameData;
        this.healthData = healthData;
        this.pensionData = pensionData;
        this.employmentData = employmentData;
        this.totalSocialData = totalSocialData;
        this.inputTimeData = inputTimeData;
    }

    /**
     * @return the inputNumData
     */
    public int getInputNumData() {
        return inputNumData;
    }

    /**
     * @param inputNumData the inputNumData to set
     */
    public void setInputNumData(int inputNumData) {
        this.inputNumData = inputNumData;
    }

    /**
     * @return the salaryData
     */
    public int getSalaryData() {
        return salaryData;
    }

    /**
     * @param salaryData the salaryData to set
     */
    public void setSalaryData(int salaryData) {
        this.salaryData = salaryData;
    }

    /**
     * @return the ageDivisionData
     */
    public String getAgeDivisionData() {
        return ageDivisionData;
    }

    /**
     * @param ageDivisionData the ageDivisionData to set
     */
    public void setAgeDivisionData(String ageDivisionData) {
        this.ageDivisionData = ageDivisionData;
    }

    /**
     * @return the occupationsData
     */
    public String getOccupationsData() {
        return occupationsData;
    }

    /**
     * @param occupationsData the occupationsData to set
     */
    public void setOccupationsData(String occupationsData) {
        this.occupationsData = occupationsData;
    }

    /**
     * @return the prefNameData
     */
    public String getPrefNameData() {
        return prefNameData;
    }

    /**
     * @param prefNameData the prefNameData to set
     */
    public void setPrefNameData(String prefNameData) {
        this.prefNameData = prefNameData;
    }

    /**
     * @return the healthData
     */
    public int getHealthData() {
        return healthData;
    }

    /**
     * @param healthData the healthData to set
     */
    public void setHealthData(int healthData) {
        this.healthData = healthData;
    }

    /**
     * @return the pensionData
     */
    public int getPensionData() {
        return pensionData;
    }

    /**
     * @param pensionData the pensionData to set
     */
    public void setPensionData(int pensionData) {
        this.pensionData = pensionData;
    }

    /**
     * @return the employmentData
     */
    public int getEmploymentData() {
        return employmentData;
    }

    /**
     * @param employmentData the employmentData to set
     */
    public void setEmploymentData(int employmentData) {
        this.employmentData = employmentData;
    }

    /**
     * @return the totalSocialData
     */
    public int getTotalSocialData() {
        return totalSocialData;
    }

    /**
     * @param totalSocialData the totalSocialData to set
     */
    public void setTotalSocialData(int totalSocialData) {
        this.totalSocialData = totalSocialData;
    }

    /**
     * @return the inputTimeData
     */
    public Timestamp getInputTimeData() {
        return inputTimeData;
    }

    /**
     * @param inputTimeData the inputTimeData to set
     */
    public void setInputTimeData(Timestamp inputTimeData) {
        this.inputTimeData = inputTimeData;
    }

}