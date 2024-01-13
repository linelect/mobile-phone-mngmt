package com.example.mobilephonemanagement.controller;

import com.example.mobilephonemanagement.model.MobilePhone;
import com.example.mobilephonemanagement.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/phones")
    public ResponseEntity<List<MobilePhone>> getAllPhones() {
        return ResponseEntity.ok(bookingService.getAllPhones());
    }

    @PutMapping("/phones/{id}/book")
    public ResponseEntity<MobilePhone> bookPhone(@PathVariable long id, @RequestBody MobilePhone mobilePhone) {
        return ResponseEntity.ok(bookingService.bookMobilePhone(id, mobilePhone));
    }

    @PutMapping("/phones/{id}/return")
    public ResponseEntity<MobilePhone> returnPhone(@PathVariable long id, @RequestBody MobilePhone mobilePhone) {
        return ResponseEntity.ok(bookingService.returnMobilePhone(id, mobilePhone));
    }
}
