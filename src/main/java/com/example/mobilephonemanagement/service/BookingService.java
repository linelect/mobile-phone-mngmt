package com.example.mobilephonemanagement.service;

import com.example.mobilephonemanagement.exception.MobilePhoneNotFoundException;
import com.example.mobilephonemanagement.exception.WrongOperationException;
import com.example.mobilephonemanagement.model.MobilePhone;
import com.example.mobilephonemanagement.repository.MobilePhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final MobilePhoneRepository mobilePhoneRepository;

    @Transactional
    public MobilePhone bookMobilePhone(long id, MobilePhone mobilePhone) {
        validateBooking(id);
        mobilePhone.setId(id);
        mobilePhone.setAvailable(false);
        mobilePhone.setBookingTime(LocalDateTime.now());
        return mobilePhoneRepository.save(mobilePhone);
    }

    private void validateBooking(long id) {
        Optional<MobilePhone> mobilePhoneOptional = mobilePhoneRepository.findById(id);
        if (mobilePhoneOptional.isEmpty()) {
            throw new MobilePhoneNotFoundException("Mobile phone not found");
        }
        if (!mobilePhoneOptional.get().isAvailable()) {
            throw new WrongOperationException("Unable to book. Mobile phone is already booked.");
        }
    }

    @Transactional
    public MobilePhone returnMobilePhone(long id, MobilePhone mobilePhone) {
        validateReturn(id);
        mobilePhone.setId(id);
        mobilePhone.setAvailable(true);
        mobilePhone.setBookingTime(null);
        mobilePhone.setPhoneUser(null);
        return mobilePhoneRepository.save(mobilePhone);
    }

    private void validateReturn(long id) {
        Optional<MobilePhone> mobilePhoneOptional = mobilePhoneRepository.findById(id);
        if (mobilePhoneOptional.isEmpty()) {
            throw new MobilePhoneNotFoundException("Mobile phone not found");
        }
        if (mobilePhoneOptional.get().isAvailable()) {
            throw new WrongOperationException("Unable to return. Mobile phone is not booked.");
        }
    }

    public List<MobilePhone> getAllPhones() {
        return mobilePhoneRepository.findAll();
    }
}
