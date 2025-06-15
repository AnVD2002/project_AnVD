package com.example.AnVD_project.service.User;

import com.example.AnVD_project.dto.response.report.ReportResponse;
import com.example.AnVD_project.entity.User;
import com.example.AnVD_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static com.example.AnVD_project.until.Constant.ReportTemplatePath.USER_TEMPLATE;


@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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





    public Optional<User> loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }
}
