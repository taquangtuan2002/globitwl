package com.globits.wl.service;

import com.globits.wl.dto.DashboardVolatilityDto;
import com.globits.wl.dto.functiondto.*;
import com.globits.wl.response.DashboardResponse;

public interface DashboardService {
    DashboardCitesDto Cites(SearchDto searchDto);

    DashboardFarmDto getFarm(SearchDto searchDto);
    
    DashboardGovermentDecreeDto getNumberGovermentDecree(SearchReportForm16Dto searchDto);
    
    DashboardVolatilityDto Volatility(SearchReportForm16Dto searchDto);

    Long farmProvidedCodeInYearNumber (SearchDto searchDto);
    DashBoardCitesVnDto CitesVn(SearchReportForm16Dto searchDto);
    
    DashBoardUpDownDto upDown (SearchReportForm16Dto searchDto);

    DashboardTotalDtoCites totalDtoCites ( DashboardTotalDtoCites dto);

    DashboardTotalGrovermentDecreeDto totalGroverment (DashboardTotalGrovermentDecreeDto dto);


    DashboardResponse getDataDashboard(SearchDto search);

}
