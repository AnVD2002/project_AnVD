package com.example.AnVD_project.service.Lines;

import com.example.AnVD_project.DTO.Request.Lines.LinesRequestDTO;
import com.example.AnVD_project.Entity.Lines;
import com.example.AnVD_project.enums.ResponseEnum;
import com.example.AnVD_project.exception.BusinessException;
import com.example.AnVD_project.repository.LineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinesServiceImpl implements LinesService{
    LineRepository lineRepository;
    @Override
    public void saveLines(List<LinesRequestDTO> request) {

        if(CollectionUtils.isEmpty(request)) {
            throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
        }
        List<LinesRequestDTO> isCreate = request
                .stream()
                .filter(rq-> ObjectUtils.isEmpty(rq.getId()))
                .toList();

        List<LinesRequestDTO> isDelete = request
                .stream()
                .filter(LinesRequestDTO::isDeleted)
                .toList();

        List<LinesRequestDTO> isUpdate = request
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

    void createLines(List<LinesRequestDTO> request) {

        try{
            List<Lines> newLines = new ArrayList<>();

            List<String> nmLine = request.stream().map(LinesRequestDTO::getNmLine).toList();

            List<Lines> lines = new ArrayList<>();

            if (!CollectionUtils.isEmpty(nmLine)) {
                lines = lineRepository.findByNmLine(nmLine);
            }

            List<Lines> lineDeletedSoft = new ArrayList<>();

            if (!CollectionUtils.isEmpty(lines)) {
                lineDeletedSoft = lines.stream().filter(l -> l.getDeleteAt() != null ).toList();
            }

            for (LinesRequestDTO rq : request) {
                Lines lineExist = lineDeletedSoft.stream()
                        .filter(line -> line.getNmLine().equals(rq.getNmLine()))
                        .findFirst()
                        .orElse(null);

                if (lineExist != null) {
                    lineExist = setDataForLine(rq, lineExist);
                    newLines.add(lineExist);
                }
                else {
                    Lines l = lines.stream()
                            .filter(line -> line.getNmLine().equals(rq.getNmLine()) && line.getDeleteAt() == null)
                            .findFirst()
                            .orElse(null);

                    if (!ObjectUtils.isEmpty(l)) {
                        throw new BusinessException(ResponseEnum.PRODUCT_ALREADY_EXISTS.getCode(), ResponseEnum.PRODUCT_ALREADY_EXISTS.getMessage());
                    }

                    Lines newLine = Lines.builder()
                            .nmLine(rq.getNmLine())
                            .description(rq.getDescription())
                            .createAt(Instant.now())
                            .cdLine(rq.getCdLine())
                            .image(rq.getImage())
                            .build();

                    newLines.add(newLine);
                }
            }
            lineRepository.saveAll(newLines);
        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", e);
        }

    }

    void updateLines(List<LinesRequestDTO> requests) {
        try {
            List<Long> ids = requests.stream().map(LinesRequestDTO::getId).toList();

            List<Lines> lineExisted = lineRepository.findByIdIn(ids);

            if (!CollectionUtils.isEmpty(lineExisted)) {
                for (Lines line : lineExisted) {
                    LinesRequestDTO request = requests.stream()
                            .filter(l -> l.getId().equals(line.getId())).findFirst().orElse(null);

                    if (!ObjectUtils.isEmpty(request)) {
                        line.setNmLine(request.getNmLine());
                        line.setDescription(request.getDescription());
                        line.setCdLine(request.getCdLine());
                        line.setImage(request.getImage());
                        line.setCreateAt(Instant.now());
                    }
                }

                lineRepository.saveAll(lineExisted);
            }

        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", e);
        }





    }

    Lines setDataForLine(LinesRequestDTO request , Lines line) {
        line.setNmLine(request.getNmLine());
        line.setDescription(request.getDescription());
        line.setCdLine(request.getCdLine());
        line.setImage(request.getImage());
        line.setDeleteAt(null);
        return line;
    }


    void deleteLines(List<LinesRequestDTO> request) {
        List<Long> ids = request.stream().map(LinesRequestDTO::getId).toList();

        List<Lines> lineExisted = lineRepository.findByIdIn(ids);

        for (Lines line : lineExisted) {
            line.setDeleteAt(Instant.now());
        }

        lineRepository.saveAll(lineExisted);
    }


}
