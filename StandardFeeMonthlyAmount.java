/**
 *
 * @author Owner
 */
public class StandardFeeMonthlyAmount {
    private int rankH;
    private int rankP;
    private Integer maxSalary;
    private int stH;
    private int stP;

    public StandardFeeMonthlyAmount(int rankH, int rankP, Integer maxSalary, int stH, int stP) {        
        this.rankH = rankH;
        this.rankP = rankP;
        this.maxSalary = maxSalary;
        this.stH = stH;
        this.stP = stP;
    }

    /**
     * @return the rankH
     */
    public int getRankH() {
        return rankH;
    }

    /**
     * @param rankH the rankH to set
     */
    public void setRankH(int rankH) {
        this.rankH = rankH;
    }

    /**
     * @return the rankP
     */
    public int getRankP() {
        return rankP;
    }

    /**
     * @param rankP the rankP to set
     */
    public void setRankP(int rankP) {
        this.rankP = rankP;
    }

    /**
     * @return the maxSalary
     */
    public Integer getMaxSalary() {
        return maxSalary;
    }

    /**
     * @param maxSalary the maxSalary to set
     */
    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    /**
     * @return the stH
     */
    public int getStH() {
        return stH;
    }

    /**
     * @param stH the stH to set
     */
    public void setStH(int stH) {
        this.stH = stH;
    }

    /**
     * @return the stP
     */
    public int getStP() {
        return stP;
    }

    /**
     * @param stP the stP to set
     */
    public void setStP(int stP) {
        this.stP = stP;
    }
}