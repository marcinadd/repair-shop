package com.marcinadd.repairshop.form.auth;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.form.Form;
import com.marcinadd.repairshop.form.FormRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
public class FormAuthServiceTests {
    @Autowired
    private FormAuthService formAuthService;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenGenerateRandomPassword_shouldReturnPassword() {
        String password = formAuthService.generateRandomPassword();
        assertThat(password, is(not(emptyString())));
        assertThat(password.length(), is(FormAuthConfig.PASSWORD_LENGTH));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void whenSavePasswordTo() {
        Form form = Form.builder().items(new ArrayList<>()).build();
        form = formRepository.save(form);
        String rawPassword = formAuthService.generateClientPasswordForForm(form);
        String encodedPassword = formRepository.findById(form.getId()).get().getPassword();
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    @Transactional
    public void whenGetFormInfo_shouldReturnFormInfo() {
        Form form = Form.builder().items(new ArrayList<>()).build();
        form = formRepository.save(form);
        String rawPassword = formAuthService.generateClientPasswordForForm(form);
        Form formInfo = formAuthService.getFormInfo(form.getId(), rawPassword);
        assertThat(formInfo.getId(), is(form.getId()));
    }
}
