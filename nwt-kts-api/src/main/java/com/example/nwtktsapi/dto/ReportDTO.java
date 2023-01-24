package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.ReportType;

import java.time.LocalDateTime;

public class ReportDTO {
    private ReportType reportType;
    private Long userId;
    private String start;
    private String end;

    public ReportDTO() {

    }
    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
