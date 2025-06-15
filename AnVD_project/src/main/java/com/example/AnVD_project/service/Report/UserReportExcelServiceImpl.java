package com.example.AnVD_project.service.Report;

import com.example.AnVD_project.dto.response.report.ReportResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.example.AnVD_project.until.Constant.ReportTemplatePath.USER_TEMPLATE;

@Service
public class UserReportExcelServiceImpl {
    public ResponseEntity<ReportResponse> exportExcel() throws FileNotFoundException {
        try {
            InputStream templateStream = getClass().getResourceAsStream(USER_TEMPLATE);

            if (templateStream == null) {
                throw new FileNotFoundException(USER_TEMPLATE);
            }
            Workbook workbook = new XSSFWorkbook(templateStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;

    }
}
