package com.example.AnVD_project.service.Lines;

import com.example.AnVD_project.DTO.Request.Lines.CrudLinesRequestDTO;
import com.example.AnVD_project.Entity.Lines;
import com.example.AnVD_project.enums.ResponseEnum;
import com.example.AnVD_project.exception.BusinessException;
import com.example.AnVD_project.repository.LineRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinesServiceImpl implements LinesService{
    LineRepository lineRepository;

    void CrudLines(List<CrudLinesRequestDTO> request) {
        if(CollectionUtils.isEmpty(request)) {
            throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
        }
        List<CrudLinesRequestDTO> isCreate = request
                .stream()
                .filter(rq-> ObjectUtils.isEmpty(rq.getId()))
                .toList();

        List<CrudLinesRequestDTO> isDelete = request
                .stream()
                .filter(CrudLinesRequestDTO::isDeleted)
                .toList();

        List<CrudLinesRequestDTO> isUpdate = request
                .stream()
                .filter(rq->!rq.isDeleted() && ObjectUtils.isEmpty(rq.getId()))
                .toList();


        if (CollectionUtils.isEmpty(isCreate)) {
            createLines(isCreate);
        }
        if (CollectionUtils.isEmpty(isUpdate)) {
            updateLines(isUpdate);
        }
        if (CollectionUtils.isEmpty(isDelete)) {
            deleteLines(isDelete);
        }
    }

    void createLines(List<CrudLinesRequestDTO> request) {

        List<Lines> newLines = new ArrayList<>();

        List<String> nmLine = request.stream().map(CrudLinesRequestDTO::getNmLine).toList();

        List<Lines> lines = new ArrayList<>();

        if (!CollectionUtils.isEmpty(nmLine)) {
            lines = lineRepository.findByNmLine(nmLine);
        }

        for (CrudLinesRequestDTO rq : request) {
            Lines l = lines.stream()
                    .filter(line -> line.getNmLine().equals(rq.getNmLine()))
                    .findFirst()
                    .orElse(null);
            if (!ObjectUtils.isEmpty(l)) {
                throw new BusinessException(ResponseEnum.PRODUCT_ALREADY_EXISTS.getCode(), ResponseEnum.PRODUCT_ALREADY_EXISTS.getMessage());
            }

            Lines newLine = Lines.builder()
                    .nmLine(rq.getNmLine())
                    .description(rq.getDescription())
                    .createAt(Instant.now())
                    .image(rq.getImage())
                    .build();

            newLines.add(newLine);
        }

        lineRepository.saveAll(newLines);

        for (Lines l : newLines) {
            l.setCdLine("LINE_" + l.getId());
        }

        lineRepository.saveAll(newLines);
    }

    void updateLines(List<CrudLinesRequestDTO> request) {

    }

    void deleteLines(List<CrudLinesRequestDTO> request) {

    }


}
