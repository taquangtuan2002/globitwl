package com.globits.wl.dto.functiondto;

public class DashboardFarmDto {
    private Long farmsNumber;
    private Long farmsNeedProvidedCodeNumber;
    private Long farmProvidedCodeNumber;
    private Float farmProvidedCodePercent;

    public DashboardFarmDto(){

    }

    public Long getFarmsNumber() {
        return farmsNumber;
    }

    public void setFarmsNumber(Long farmsNumber) {
        this.farmsNumber = farmsNumber;
    }

    public Long getFarmsNeedProvidedCodeNumber() {
        return farmsNeedProvidedCodeNumber;
    }

    public void setFarmsNeedProvidedCodeNumber(Long farmsNeedProvidedCodeNumber) {
        this.farmsNeedProvidedCodeNumber = farmsNeedProvidedCodeNumber;
    }

    public Long getFarmProvidedCodeNumber() {
        return farmProvidedCodeNumber;
    }

    public void setFarmProvidedCodeNumber(Long farmProvidedCodeNumber) {
        this.farmProvidedCodeNumber = farmProvidedCodeNumber;
    }

    public Float getFarmProvidedCodePercent() {
        return farmProvidedCodePercent;
    }

    public void setFarmProvidedCodePercent(Float farmProvidedCodePercent) {
        this.farmProvidedCodePercent = farmProvidedCodePercent;
    }
}
