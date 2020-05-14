package com.marcinadd.repairshop.form;

import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import com.marcinadd.repairshop.helper.StringHelper;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.item.buyable.Buyable;
import com.marcinadd.repairshop.repairable.Repairable;
import com.marcinadd.repairshop.repairable.RepairableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormService {
    private final static int PAGE_SIZE = 10;

    private final ClientRepository clientRepository;
    private final RepairableRepository repairableRepository;
    private final FormRepository formRepository;

    public FormService(ClientRepository clientRepository, RepairableRepository repairableRepository, FormRepository formRepository) {
        this.clientRepository = clientRepository;
        this.repairableRepository = repairableRepository;
        this.formRepository = formRepository;
    }

    public Form createForm(Form form) {
        Optional<Client> optionalClient = clientRepository.findById(form.getClientId());
        Optional<Repairable> optionalRepairable = repairableRepository.findById(form.getRepairableId());
        if (optionalClient.isPresent() && optionalRepairable.isPresent()) {
            form.setClient(optionalClient.get());
            form.setRepairable(optionalRepairable.get());
            form.setStatus(Status.TO_DO);
            return formRepository.save(form);
        }
        return null;
    }

    public List<Form> findAllForms() {
        return formRepository.findAll();
    }

    public Form findFormById(Long id) {
        return formRepository.findById(id).orElse(null);
    }

    public Form patchFormById(Long formId, Form patchedValues) {
        Optional<Form> optionalForm = formRepository.findById(formId);
        if (optionalForm.isPresent()) {
            Form form = optionalForm.get();
            if (patchedValues.getStatus() != null)
                form.setStatus(patchedValues.getStatus());
            return formRepository.save(form);
        }
        return null;
    }

    public Form hidePersonalDataInForm(Form form) {
        Client client = form.getClient();
        if (client != null) {
            client.setFirstName(StringHelper.replaceCharsWithAsterisks(client.getFirstName(), 1, 1));
            client.setLastName(StringHelper.replaceCharsWithAsterisks(client.getLastName(), 1, 1));
            client.setEmail(StringHelper.replaceCharsWithAsterisks(client.getEmail(), 2, 4));
            client.setPhone(StringHelper.replaceCharsWithAsterisks(client.getPhone(), 2, 2));
            client.setRepairables(null);
            form.setClient(client);
        }
        Repairable repairable = form.getRepairable();
        if (repairable != null) {
            repairable.setSerial(StringHelper.replaceCharsWithAsterisks(repairable.getSerial(), 2, 2));
            form.setRepairable(repairable);
        }
        List<Item> items = form.getItems();
        if (items != null && !items.isEmpty()) {
            items.forEach(item -> {
                Buyable buyable = item.getBuyable();
                buyable.setDeleted(null);
                buyable.setPrice(null);
                item.setBuyable(buyable);
            });
        }
        return form;
    }

    public Page<Form> findFormByStatusSortByDateGetPage(Status status, int pageNumber, int itemsPerPage) {
        return formRepository.findAllByStatusOrderByCreatedDateDesc(status, PageRequest.of(pageNumber, itemsPerPage));
    }
}
