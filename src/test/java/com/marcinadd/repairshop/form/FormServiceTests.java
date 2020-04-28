package com.marcinadd.repairshop.form;

import com.marcinadd.repairshop.RepairShopApplication;
import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import com.marcinadd.repairshop.item.ItemRepository;
import com.marcinadd.repairshop.repairable.Repairable;
import com.marcinadd.repairshop.repairable.RepairableRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepairShopApplication.class)
public class FormServiceTests {
    @Autowired
    private FormService formService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RepairableRepository repairableRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private ItemRepository itemRepository;

    private Client owner;
    private Repairable repairable;

    @Before
    public void init() {
        owner = clientRepository.save(Client.builder()
                .firstName("Sample")
                .build()
        );
        repairable = repairableRepository.save(Repairable.builder()
                .owner(owner)
                .build()
        );
    }

    @Test
    public void whenCreateForm_shouldReturnForm() {
        Form form = Form.builder().clientId(owner.getId()).repairableId(repairable.getId()).build();
        form = formService.createForm(form);
        assertThat(form.getClient(), equalTo(owner));
        assertThat(form.getRepairable(), equalTo(repairable));
    }

    @Test
    public void whenPatchStatus_shouldReturnFormWithPatchedStatus() {
        Form form = Form.builder().status(Status.TO_DO).build();
        form = formRepository.save(form);

        Form patched = Form.builder().status(Status.COMPLETED).build();
        Form form1 = formService.patchFormById(form.getId(), patched);
        assertThat(form1.getStatus(), is(patched.getStatus()));
    }

}
