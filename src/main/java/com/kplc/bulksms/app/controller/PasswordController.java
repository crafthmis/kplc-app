package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController extends BaseController {

   // @PostMapping("/contact/login")


    @PostMapping("/contact/password/set")
    public ResponseEntity<Object> setPassword(@RequestBody Contact contact) {
        return contactService.setPassword(contact);
    }

    @PostMapping("/contact/password/change")
    public ResponseEntity<Object> changePassword(String username, String password) {
        return contactService.changePassword(username,password);
    }

    @PostMapping("/contact/password/forgot")
    public ResponseEntity<Object> resetPassword(String username, String password) {
        return contactService.changePassword(username,password);
    }
}
