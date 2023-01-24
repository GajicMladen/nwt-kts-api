package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.ReportDTO;
import com.example.nwtktsapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(value = "api/report/")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping (value = "reports")
    public ResponseEntity<?> getReports(@RequestBody ReportDTO reportDTO) {
        Map<String, Object> data = reportService.delegateReport(reportDTO);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
