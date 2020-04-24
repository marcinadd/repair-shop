package com.marcinadd.repairshop.form;

import com.marcinadd.repairshop.client.Client;
import com.marcinadd.repairshop.client.ClientRepository;
import com.marcinadd.repairshop.item.Item;
import com.marcinadd.repairshop.repairable.Repairable;
import com.marcinadd.repairshop.repairable.RepairableRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {

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

    public Form addItem(Form form, Item item) {
        List<Item> currentItems = form.getItems();
        if (currentItems == null) {
            List<Item> items = new ArrayList<>();
            items.add(item);
            form.setItems(items);
            return formRepository.save(form);
        }
        currentItems.add(item);
        return formRepository.save(form);
    }
}
