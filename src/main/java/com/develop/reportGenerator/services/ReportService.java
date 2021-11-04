package com.develop.reportGenerator.services;

import com.develop.reportGenerator.request.Report;

public interface ReportService {

    byte[] makeReport(Report requestReport, Long templateId);
}
