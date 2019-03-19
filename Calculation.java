/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Owner
 */
@ManagedBean
@RequestScoped
public class Calculation {

    private int salary = 0;
    private int age = 1;
    private int business = 1;

    private int outputHealth;
    private int outputHealthRank;
    private int outputPension;
    private int outputPensionRank;
    private int outputEmployment;
    private int outputTotalSocial;
    private String outputName;
    private double outputRate;

    private int prefId;

    private List<SelectItem> prefList = new ArrayList<>();
    private Map<Integer, PrefecturesInfo> rateMap = new HashMap<>();
    private List<StandardFeeMonthlyAmount> sfmaList = new ArrayList<>();

    FacesContext context = FacesContext.getCurrentInstance();

    @PostConstruct
    public void init() {

        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;

        try {
            con = ConnectionManager.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery("select * from info order by id");
            while (rs.next()) {
                SelectItem item = new SelectItem(rs.getInt("id"), rs.getString("name"));
                getPrefList().add(item);
                getRateMap().put(rs.getInt("id"), new PrefecturesInfo(rs.getBigDecimal("rate")));
            }

            rs = smt.executeQuery("select * from list order by num");
            while (rs.next()) {
                StandardFeeMonthlyAmount sfma = new StandardFeeMonthlyAmount(rs.getInt("rank_h"), rs.getInt("rank_p"),
                        (Integer) rs.getObject("max_Salary"), rs.getInt("st_h"), rs.getInt("st_p"));
                getSfmaList().add(sfma);

            }
        } catch (SQLException e) {
            context.addMessage("code", new FacesMessage(e.getMessage() + "(DB接続時)"));
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
        /**
         * Creates a new instance of ManagedBean
         */
    }

    public void calc() {

        BigDecimal healthRate;
        BigDecimal pensionRate = new BigDecimal("0.183");

        PrefecturesInfo prefInfo = getRateMap().get(getPrefId());
        healthRate = prefInfo.getRate();

        if (this.salary < 0) {
            context.addMessage("code:positiveNumber", new FacesMessage("※総支給は正の整数を半角で入力してください。"));
            return;
        }

        //　介護保険料率対象者への健康保険料率の上乗せ処理です。改定時変更が必要です。	
        if (age == 2) {
            healthRate = healthRate.add(new BigDecimal("0.0173"));
        }

        //　標準報酬月額を確定し、健康保険料と厚生年金保険料を算出します。
        for (StandardFeeMonthlyAmount sfma : sfmaList) {
            if (sfma.getMaxSalary() == null || this.salary <= sfma.getMaxSalary()) {
                outputHealth = new BigDecimal(sfma.getStH()).multiply(healthRate)
                        .divide(new BigDecimal(2), 0, BigDecimal.ROUND_HALF_UP).intValue();
                outputPension = new BigDecimal(sfma.getStP()).multiply(pensionRate)
                        .divide(new BigDecimal(2), 0, BigDecimal.ROUND_HALF_UP).intValue();
                this.outputHealthRank = sfma.getRankH();
                this.outputPensionRank = sfma.getRankP();
                break;
            }
        }

        //雇用保険料を算出します。改定があったら変更して下さい。
        BigDecimal bd, bd1;

        switch (this.business) {
            case 1:
                bd = new BigDecimal(salary).multiply(new BigDecimal("0.003"));
                bd1 = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
                outputEmployment = bd1.intValue();
                break;
            case 2:
                bd = new BigDecimal(salary).multiply(new BigDecimal("0.004"));
                bd1 = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
                outputEmployment = bd1.intValue();
                break;
            default:
                bd = new BigDecimal(salary).multiply(new BigDecimal("0.004"));
                bd1 = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
                outputEmployment = bd1.intValue();
        }

        this.outputTotalSocial = this.outputHealth + this.outputPension + this.outputEmployment;

        try (Connection con = ConnectionManager.getConnection();
                CallableStatement cs = con.prepareCall("{call save_result(?,?,?,?,?,?,?,?,?)}")) {

            con.setAutoCommit(false);

            cs.setInt(1, this.salary);
            cs.setInt(2, this.age);
            cs.setInt(3, this.prefId);
            cs.setInt(4, this.business);
            cs.setInt(5, this.outputHealth);
            cs.setInt(6, this.outputPension);
            cs.setInt(7, this.outputEmployment);
            cs.setInt(8, this.outputTotalSocial);
            cs.registerOutParameter(9, Types.INTEGER);
            //↑後で確認
            cs.execute();
            con.commit();
            //↑ストアド書くとき必要
        } catch (SQLException e) {
            context.addMessage("code", new FacesMessage(e.getMessage() + "(DBへ保存時)"));
        }

    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the business
     */
    public int getBusiness() {
        return business;
    }

    /**
     * @param business the business to set
     */
    public void setBusiness(int business) {
        this.business = business;
    }

    /**
     * @return the outputHealth
     */
    public int getOutputHealth() {
        return outputHealth;
    }

    /**
     * @param outputHealth the outputHealth to set
     */
    public void setOutputHealth(int outputHealth) {
        this.outputHealth = outputHealth;
    }

    /**
     * @return the outputHealthRank
     */
    public int getOutputHealthRank() {
        return outputHealthRank;
    }

    /**
     * @param outputHealthRank the outputHealthRank to set
     */
    public void setOutputHealthRank(int outputHealthRank) {
        this.outputHealthRank = outputHealthRank;
    }

    /**
     * @return the outputPension
     */
    public int getOutputPension() {
        return outputPension;
    }

    /**
     * @param outputPension the outputPension to set
     */
    public void setOutputPension(int outputPension) {
        this.outputPension = outputPension;
    }

    /**
     * @return the outputPensionRank
     */
    public int getOutputPensionRank() {
        return outputPensionRank;
    }

    /**
     * @param outputPensionRank the outputPensionRank to set
     */
    public void setOutputPensionRank(int outputPensionRank) {
        this.outputPensionRank = outputPensionRank;
    }

    /**
     * @return the outputEmployment
     */
    public int getOutputEmployment() {
        return outputEmployment;
    }

    /**
     * @param outputEmployment the outputEmployment to set
     */
    public void setOutputEmployment(int outputEmployment) {
        this.outputEmployment = outputEmployment;
    }

    /**
     * @return the outputTotalSocial
     */
    public int getOutputTotalSocial() {
        return outputTotalSocial;
    }

    /**
     * @param outputTotalSocial the outputTotalSocial to set
     */
    public void setOutputTotalSocial(int outputTotalSocial) {
        this.outputTotalSocial = outputTotalSocial;
    }

    /**
     * @return the outputName
     */
    public String getOutputName() {
        return outputName;
    }

    /**
     * @param outputName the outputName to set
     */
    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    /**
     * @return the outputRate
     */
    public double getOutputRate() {
        return outputRate;
    }

    /**
     * @param outputRate the outputRate to set
     */
    public void setOutputRate(double outputRate) {
        this.outputRate = outputRate;
    }

    /**
     * @return the prefId
     */
    public int getPrefId() {
        return prefId;
    }

    /**
     * @param prefId the prefId to set
     */
    public void setPrefId(int prefId) {
        this.prefId = prefId;
    }

    /**
     * @return the prefList
     */
    public List<SelectItem> getPrefList() {
        return prefList;
    }

    /**
     * @param prefList the prefList to set
     */
    public void setPrefList(List<SelectItem> prefList) {
        this.prefList = prefList;
    }

    /**
     * @return the rateMap
     */
    public Map<Integer, PrefecturesInfo> getRateMap() {
        return rateMap;
    }

    /**
     * @param rateMap the rateMap to set
     */
    public void setRateMap(Map<Integer, PrefecturesInfo> rateMap) {
        this.rateMap = rateMap;
    }

    /**
     * @return the sfmaList
     */
    public List<StandardFeeMonthlyAmount> getSfmaList() {
        return sfmaList;
    }

    /**
     * @param sfmaList the sfmaList to set
     */
    public void setSfmaList(List<StandardFeeMonthlyAmount> sfmaList) {
        this.sfmaList = sfmaList;
    }

}