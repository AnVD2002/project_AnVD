package com.example.AnVD_project.until.report;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public abstract class Excel {
    /**
     * Fill data into the body of the file
     * @param sheet Sheet
     * @param data List<T>
     */
    public abstract void fillData(Sheet sheet, List<T> data);


    /**
     * Copy data from one row to another row
     * @param sourceRow Row
     * @param targetRow Row
     */
    public static void copyRow(Row sourceRow, Row targetRow, int startCell) {
        for (int i = startCell; i < sourceRow.getPhysicalNumberOfCells(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = targetRow.createCell(i);

            if (oldCell == null) {
                continue; // bỏ qua nếu không có ô ở vị trí này
            }

            // Copy cell style
            CellStyle newCellStyle = targetRow.getSheet().getWorkbook().createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            newCell.setCellStyle(newCellStyle);

            // Copy cell value
            switch (oldCell.getCellType()) {
                case STRING:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                default:
                    newCell.setCellValue(oldCell.getStringCellValue());
            }
        }
    }

    /**
     * Write workbook to a file
     * @param workbook Workbook
     * @param outputFilePath String
     * @throws IOException IOException
     */
    public static void writeToFile(Workbook workbook, String outputFilePath) throws IOException {
        // Write a file on local
        try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    /**
     * Change file extension
     * @param fileName String
     * @param newExtension String
     * @return String
     */
    public static String changeExtension(String fileName, String newExtension) {
        if (fileName == null || newExtension == null) {
            return fileName;
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName + newExtension;
        }
        return fileName.substring(0, lastDotIndex) + newExtension;
    }

    /**
     * Copy data from one row to another row
     * @param sourceRow Row
     * @param targetRow Row
     */
    public static void copyRow(Row sourceRow, Row targetRow, int startCell, Row eventRow, Row oddRow) {
        targetRow.setHeight(sourceRow.getHeight());
        for (int i = startCell; i < sourceRow.getPhysicalNumberOfCells(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = targetRow.createCell(i);

            // Copy cell style
            CellStyle newCellStyle;
            if (targetRow.getRowNum() % 2 == 0) {
                newCellStyle = targetRow.getSheet().getWorkbook().createCellStyle();
                newCellStyle.cloneStyleFrom(oddRow.getCell(i).getCellStyle());
            } else {
                newCellStyle = targetRow.getSheet().getWorkbook().createCellStyle();
                newCellStyle.cloneStyleFrom(eventRow.getCell(i).getCellStyle());
            }
            newCell.setCellStyle(newCellStyle);

            // Copy cell value
            switch (oldCell.getCellType()) {
                case STRING:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                default:
                    newCell.setCellValue(oldCell.getStringCellValue());
            }
        }
    }

    public static void copyRowAndCell(Row sourceRow, Row targetRow, int startCell, boolean isFirstRow) {
        if (isFirstRow) {
            for (int i = startCell; i < sourceRow.getPhysicalNumberOfCells(); i++) {
                Cell oldCell = sourceRow.getCell(i);
                Cell newCell = targetRow.createCell(i);
                // Copy cell style
                CellStyle newCellStyle = targetRow.getSheet().getWorkbook().createCellStyle();
                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
                newCell.setCellStyle(newCellStyle);

                // Copy cell value
                switch (oldCell.getCellType()) {
                    case STRING:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(oldCell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(oldCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(oldCell.getCellFormula());
                        break;
                    default:
                        newCell.setCellValue(oldCell.getStringCellValue());
                }
            }
        } else {
            // Đối với các hàng sau, chỉ cần sao chép kiểu dáng
            for (int i = startCell; i < sourceRow.getPhysicalNumberOfCells(); i++) {
                Cell newCell = targetRow.createCell(i);
                CellStyle newCellStyle = targetRow.getSheet().getWorkbook().createCellStyle();
                newCellStyle.cloneStyleFrom(sourceRow.getCell(i).getCellStyle());
                newCell.setCellStyle(newCellStyle);
            }
        }
        copyMergedRegions(sourceRow.getSheet(), sourceRow.getRowNum(), targetRow.getRowNum());
    }

    public static Cell copyRowAndCell(Row sourceRow, Row targetRow, int cellIndex) {
        if (sourceRow == null) {
            return targetRow.createCell(cellIndex);
        }
        Cell sourceCell = sourceRow.getCell(cellIndex);
        Cell targetCell = targetRow.createCell(cellIndex);
        targetCell.setCellStyle(sourceCell.getCellStyle());
        // Copy cell value
        switch (sourceCell.getCellType()) {
            case STRING:
                targetCell.setCellValue(sourceCell.getStringCellValue());
                break;
            case NUMERIC:
                targetCell.setCellValue(sourceCell.getNumericCellValue());
                break;
            case BOOLEAN:
                targetCell.setCellValue(sourceCell.getBooleanCellValue());
                break;
            case FORMULA:
                targetCell.setCellFormula(sourceCell.getCellFormula());
                break;
            default:
                targetCell.setCellValue(sourceCell.getStringCellValue());
        }
        return targetCell;
    }

    private static void copyMergedRegions(Sheet sheet, int sourceRowNum, int targetRowNum) {
        int mergedRegions = sheet.getNumMergedRegions();
        for (int i = 0; i < mergedRegions; i++) {
            CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
            if (mergedRegion.getFirstRow() == sourceRowNum) {
                CellRangeAddress newMergedRegion = new CellRangeAddress(
                        targetRowNum,
                        targetRowNum + (mergedRegion.getLastRow() - mergedRegion.getFirstRow()),
                        mergedRegion.getFirstColumn(),
                        mergedRegion.getLastColumn()
                );
                sheet.addMergedRegion(newMergedRegion);
            }
        }
    }

    /**
     * Set scale initial for each sheet
     * @param sheet Sheet
     * @param scaleFactor short
     */
    protected void setScaleInitital(Sheet sheet, short scaleFactor) {
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setScale(scaleFactor);
    }
}
