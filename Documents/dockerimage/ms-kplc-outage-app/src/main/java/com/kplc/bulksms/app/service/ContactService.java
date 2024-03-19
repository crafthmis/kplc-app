package com.kplc.bulksms.app.service;

import com.kplc.bulksms.app.excepions.CustomErrorException;
import com.kplc.bulksms.app.excepions.CustomExtraErrorException;
import com.kplc.bulksms.app.model.Contact;
import com.kplc.bulksms.app.model.dto.ContactDto;
import com.kplc.bulksms.app.model.dto.LoginDto;
import com.kplc.bulksms.app.repository.ContactRepository;
import com.kplc.bulksms.app.util.PasswordEncoder;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;


@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final PasswordEncoder passwordEncoder;


    public ContactService(ContactRepository contactRepository, PasswordEncoder passwordEncoder) {
        this.contactRepository = contactRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> getContacts() {
        List<ContactDto> contactDtos = contactRepository.findAll(Sort.by(Sort.Direction.ASC, "cntId")).stream()
                .filter(Objects::nonNull)
                .map(cnt -> new ContactDto(cnt.getAreaId(), cnt.getArea().getName(), cnt.getMsisdn()))
                .toList();
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", contactDtos);
    }

    public ResponseEntity<Object> getContact(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isEmpty()) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Contact with id: " + id);
        }
        ContactDto contactDto = contact.map(dto -> new ContactDto(dto.getCntId(), dto.getArea().getName(), dto.getMsisdn())).orElseThrow(null);
        throw new CustomExtraErrorException(HttpStatus.OK, "Records successfully retrieved", contactDto);
    }

    public ResponseEntity<Object> createNewContact(Contact contact) {

        Optional<Contact> optionalContact = contactRepository.findByMsisdn(StringUtils.right(contact.getMsisdn(), 9));
        if (optionalContact.isPresent()) {
            throw new CustomErrorException(HttpStatus.FOUND, "Contact already exists");
        }
        if (null != contact.getMsisdn() && !contact.getMsisdn().isEmpty()) {
            contact.setPassword(passwordEncoder.get_SHA_512_SecurePassword(contact.getPassword()));
            contact.setMsisdn(StringUtils.right(contact.getMsisdn(), 9));
            contactRepository.save(contact);
            throw new CustomErrorException(HttpStatus.CREATED, "Contact created successfully");
        } else {
            throw new CustomErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Something has gone wrong");
        }
    }

    public ResponseEntity<Object> deleteContact(Long id) {
        boolean exists = contactRepository.existsById(id);
        if (!exists) {
            throw new CustomErrorException(HttpStatus.NOT_FOUND, "No such Area with id: " + id);
        }
        contactRepository.deleteById(id);
        throw new CustomErrorException(HttpStatus.OK, "Contact deleted Successfully");
    }

    @Transactional
    public ResponseEntity<Object> updateContact(Long ctyId, String msisdn) {
        Contact contact = contactRepository.findById(ctyId).orElseThrow(() ->
                new IllegalArgumentException("No such Contact with id: " + ctyId));
        if (msisdn != null && !msisdn.isEmpty() && !Objects.equals(contact.getMsisdn(), msisdn)) {
            contact.setMsisdn(msisdn);
            contactRepository.save(contact);
            throw new CustomErrorException(HttpStatus.CREATED, "Contact updated successfully");
        }
        throw new CustomErrorException(HttpStatus.CREATED, "No update done");
    }

    public ResponseEntity<Object> changePassword(@RequestBody LoginDto loginDto) {
        Optional<Contact> contact = contactRepository.findByUsername(loginDto.getUsername());
        if (contact.isEmpty()) {
            throw new CustomErrorException(HttpStatus.UNAUTHORIZED, "User does not exist");
        }else if (contact.get().getPassword().equals(passwordEncoder.get_SHA_512_SecurePassword(loginDto.getPassword()))) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Old Password and New Password are the same");
        } else {
            contactRepository.updateForgotPassword(passwordEncoder.get_SHA_512_SecurePassword(loginDto.getPassword()), loginDto.getUsername());
            throw new CustomErrorException(HttpStatus.OK, "Password successfully reset");
        }
    }

    public ResponseEntity<Object> setPassword(@RequestBody LoginDto loginDto) {
        Optional<Contact> contactObj = contactRepository.findByUsername(loginDto.getUsername());
        if (contactObj.isEmpty()) {
            throw new CustomErrorException(HttpStatus.UNAUTHORIZED, "Contact with username " + loginDto.getUsername() + " not found!");
        }
        String token = passwordEncoder.get_SHA_512_SecurePassword(new Random().nextInt(999999) + "_" + System.currentTimeMillis());
        contactRepository.updateRegistrationToken(token, contactObj.get().getUserName());
        JSONObject jsonObj = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("reset_token", token);
        jsonObj.put("data", data);
        throw new CustomExtraErrorException(HttpStatus.OK, "Password set successfully", jsonObj);

    }

    public ResponseEntity<Object> loginUser(@RequestBody LoginDto loginDto) {
        Optional<Contact> contactObj = contactRepository.findByUsernameAndPassword(loginDto.getUsername(), passwordEncoder.get_SHA_512_SecurePassword(loginDto.getPassword()));
        if (contactObj.isEmpty()) {
            throw new CustomErrorException(HttpStatus.UNAUTHORIZED, "Invalid login credentials");
        } else if (contactObj.get().getToken()!=null) {
            throw new CustomErrorException(HttpStatus.UNAUTHORIZED, "Please reset password");
        } else {
            throw new CustomErrorException(HttpStatus.OK, "Login Successful");
        }
    }
}
    

