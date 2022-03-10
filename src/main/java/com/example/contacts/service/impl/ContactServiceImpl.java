package com.example.contacts.service.impl;

import com.example.contacts.model.Contact;
import com.example.contacts.repository.ContactRepository;
import com.example.contacts.service.ContactService;
import com.example.contacts.util.Validation;
import com.example.contacts.util.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact create(Contact contact) {
        if ((!Validator.checkLabel(contact.getEmailLabel(), Validation.label)) ||
                (!Validator.checkLabel(contact.getPhoneLabel(), Validation.label)) ||
                (!Validator.checkEmail(contact.getEmail(), Validation.regexEmail)) ||
                (!Validator.checkPhone(contact.getPhone(), Validation.regexPhone))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong Email");
        }
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    @Transactional
    public Contact update(Contact contact, int id) {
        if ((!Validator.checkLabel(contact.getEmailLabel(), Validation.label)) ||
                (!Validator.checkLabel(contact.getPhoneLabel(), Validation.label)) ||
                (!Validator.checkEmail(contact.getEmail(), Validation.regexEmail)) ||
                (!Validator.checkPhone(contact.getPhone(), Validation.regexPhone))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong Email");
        }
        Contact fromDB = contactRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        });
        fromDB.setName(contact.getName());
        fromDB.setSurname(contact.getSurname());
        fromDB.setPhone(contact.getPhone());
        fromDB.setPhoneLabel(contact.getPhoneLabel());
        fromDB.setEmail(contact.getEmail());
        fromDB.setEmailLabel(contact.getEmailLabel());
        return fromDB;
    }

    @Override
    public void delete(int id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> getByKey(String key) {

        return getAll()
                .stream()
                .filter((item) ->
                        item.stringify().toLowerCase(Locale.ROOT).contains(key.toLowerCase(Locale.ROOT))
                ).collect(Collectors.toList());

    }
}