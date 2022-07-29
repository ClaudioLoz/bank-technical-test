package com.banco.bp.service;

import com.banco.bp.dto.response.ReporteFinalDTO;

public interface ReporteService {
    ReporteFinalDTO getReporte(String startDate, String endDate,Long clientId);
}
