package com.example.AnVD_project.service.Report;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public interface ReportService {
    void fillDataToSheet(Sheet sheet, List<T> data);
}
