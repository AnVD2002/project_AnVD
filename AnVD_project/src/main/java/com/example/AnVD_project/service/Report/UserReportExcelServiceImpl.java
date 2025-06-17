package com.example.AnVD_project.service.Report;

import com.example.AnVD_project.dto.response.report.ReportResponse;
import com.example.AnVD_project.dto.response.user.UserResponseDTO;
import com.example.AnVD_project.service.User.UserService;
import com.example.AnVD_project.until.exception.BusinessException;
import com.example.AnVD_project.until.exception.EErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.AnVD_project.until.Constant.ReportTemplatePath.USER_TEMPLATE;

@Service
@RequiredArgsConstructor
public class UserReportExcelServiceImpl implements UserReportExcelService {
    @Autowired
    private final UserService userService;

    public ResponseEntity<ReportResponse> exportExcel() throws FileNotFoundException {
        try {

            List<UserResponseDTO> userList = userService.getAllUsers();

            if (CollectionUtils.isEmpty(userList)) {
                throw new BusinessException(EErrorResponse.DATA_NOT_FOUND_ERROR);
            }

            InputStream templateStream = getClass().getResourceAsStream(USER_TEMPLATE);

            if (templateStream == null) {
                throw new FileNotFoundException(USER_TEMPLATE);
            }
            Workbook workbook = new XSSFWorkbook(templateStream);

            Sheet sheet = workbook.getSheetAt(0);

            fillDataToSheet(sheet, userList);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            workbook.close();
            templateStream.close();
            outputStream.close();

            String fileName = "user_report.xlsx";

            return new ResponseEntity<>(ReportResponse.builder().name(fileName).data(outputStream.toByteArray()).build(), HttpStatus.OK);

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception e) {
            throw new BusinessException(EErrorResponse.INTERNAL_SERVER_ERROR);
        }

    }

    private void fillDataToSheet(Sheet sheet, List<UserResponseDTO> userList) {
        int rowIndex = 1;
        for (UserResponseDTO user : userList) {
            sheet.createRow(rowIndex).createCell(0).setCellValue(rowIndex);
            sheet.getRow(rowIndex).createCell(1).setCellValue(user.getId());
            sheet.getRow(rowIndex).createCell(2).setCellValue(user.getUsername());
            sheet.getRow(rowIndex).createCell(3).setCellValue(user.getPassword());
            sheet.getRow(rowIndex).createCell(4).setCellValue(user.getNumberPhone());
            rowIndex++;
        }
    }
}
