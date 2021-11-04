package com.develop.reportGenerator.controllers;

import com.develop.reportGenerator.request.Report;
import com.develop.reportGenerator.services.ReportService;
import org.springframework.web.bind.annotation.*;


@RestController
public class ReportController {

    final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(value = "/makeReport",
            produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public byte[] makeReport(@RequestBody Report requestReport, @RequestParam("templateId") Long templateId){
        return reportService.makeReport(requestReport, templateId);
    }
}
