package com.marcinadd.repairshop.form.auth;

import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import com.marcinadd.repairshop.pdf.PdfService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.marcinadd.repairshop.form.auth.FormAuthConfig.PASSWORD_LENGTH;

@Service
public class FormAuthService {
    private final PasswordEncoder passwordEncoder;
    private final FormRepository formRepository;
    private final PdfService pdfService;

    public FormAuthService(PasswordEncoder passwordEncoder, FormRepository formRepository, PdfService pdfService) {
        this.passwordEncoder = passwordEncoder;
        this.formRepository = formRepository;
        this.pdfService = pdfService;
    }

    public String generateRandomPassword() {
        Random random = new Random();
        return random.ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(PASSWORD_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public String generateClientPasswordForForm(Form form) {
        String password = generateRandomPassword();
        form.setPassword(passwordEncoder.encode(password));
        formRepository.save(form);
        return password;
    }

    public ResponseEntity<byte[]> regeneratePasswordForFormSaveToPdf(Long formId) {
        Optional<Form> optionalForm = formRepository.findById(formId);
        if (optionalForm.isPresent()) {
            Form form = optionalForm.get();
            String password = generateClientPasswordForForm(form);
            return pdfService.createPdfFormPassword(form, password);
        }
        return null;
    }
}
