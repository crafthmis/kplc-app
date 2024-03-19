package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactController extends BaseController {


    @GetMapping("/contacts")
    public ResponseEntity<Object> getContacts() {
        return contactService.getContacts();
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Object> getContact(@PathVariable Long id) {
        return contactService.getContact(id);
    }

    @PostMapping("/contact/add")
    public ResponseEntity<Object> addNewContact(@RequestBody Contact contact) {
        return contactService.createNewContact(contact);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable Long id) {
        return contactService.deleteContact(id);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Object> updateContact(@PathVariable("id") Long id, @RequestParam(required = false) String msisdn) {
        return contactService.updateContact(id, msisdn);
    }
}
