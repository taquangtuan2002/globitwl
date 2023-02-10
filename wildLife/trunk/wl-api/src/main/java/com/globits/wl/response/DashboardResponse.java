package com.globits.wl.response;

import java.util.List;

public class DashboardResponse {
    List<DashboardTableResponse> cites;
    List<DashboardTableResponse> vn06;
    private Integer countFarmCertificatedGroupI;
    private Integer countFarmCertificatedGroupII;

    public List<DashboardTableResponse> getCites() {
        return cites;
    }

    public void setCites(List<DashboardTableResponse> cites) {
        this.cites = cites;
    }

    public List<DashboardTableResponse> getVn06() {
        return vn06;
    }

    public void setVn06(List<DashboardTableResponse> vn06) {
        this.vn06 = vn06;
    }

    public Integer getCountFarmCertificatedGroupI() {
        return countFarmCertificatedGroupI;
    }

    public void setCountFarmCertificatedGroupI(Integer countFarmCertificatedGroupI) {
        this.countFarmCertificatedGroupI = countFarmCertificatedGroupI;
    }

    public Integer getCountFarmCertificatedGroupII() {
        return countFarmCertificatedGroupII;
    }

    public void setCountFarmCertificatedGroupII(Integer countFarmCertificatedGroupII) {
        this.countFarmCertificatedGroupII = countFarmCertificatedGroupII;
    }
}
