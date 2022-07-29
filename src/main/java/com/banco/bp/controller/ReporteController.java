package com.banco.bp.controller;

import com.banco.bp.dto.response.ReporteFinalDTO;
import com.banco.bp.dto.response.MovimientoListDTO;
import com.banco.bp.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<ReporteFinalDTO> getReporte(@RequestParam("fechaInicial")String startDate,@RequestParam("fechaFinal") String endDate, @RequestParam("clienteId")Long clientId ){
        return new ResponseEntity<ReporteFinalDTO>
                (reporteService.getReporte(startDate,endDate,clientId), HttpStatus.OK);
    }

}
