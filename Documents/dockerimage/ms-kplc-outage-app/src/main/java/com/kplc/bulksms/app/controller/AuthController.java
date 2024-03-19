package com.kplc.bulksms.app.controller;

import com.kplc.bulksms.app.model.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends BaseController {

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        return contactService.loginUser(loginDto);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Object> resetPassword(@RequestBody LoginDto loginDto) {
        return contactService.setPassword(loginDto);
    }


    @PostMapping("/password/change")
    public ResponseEntity<Object> changePassword(@RequestBody LoginDto loginDto) {
        return contactService.changePassword(loginDto);
    }
}
