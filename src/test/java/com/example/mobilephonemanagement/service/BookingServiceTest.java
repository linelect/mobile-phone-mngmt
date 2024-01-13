package com.example.mobilephonemanagement.service;

import com.example.mobilephonemanagement.exception.MobilePhoneNotFoundException;
import com.example.mobilephonemanagement.exception.WrongOperationException;
import com.example.mobilephonemanagement.model.MobilePhone;
import com.example.mobilephonemanagement.repository.MobilePhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private MobilePhoneRepository mobilePhoneRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService(mobilePhoneRepository);
    }

    @Test
    public void testGetAllPhones() {
        bookingService.getAllPhones();

        verify(mobilePhoneRepository).findAll();
    }

    @Test
    public void testBookSuccess() {
        long id = 1;
        MobilePhone mobilePhone = MobilePhone.builder().id(1).available(true).version(0L).build();
        when(mobilePhoneRepository.findById(id)).thenReturn(Optional.of(mobilePhone));

        bookingService.bookMobilePhone(id, mobilePhone);

        assertThat(mobilePhone.getBookingTime()).isNotNull();
        assertThat(mobilePhone.isAvailable()).isFalse();
        verify(mobilePhoneRepository).save(mobilePhone);
    }

    @Test
    public void testBookNotAvailableAndGetWrongOperationException() {
        long id = 1;
        MobilePhone mobilePhone = MobilePhone.builder().id(1).available(false).version(0L).build();
        when(mobilePhoneRepository.findById(id)).thenReturn(Optional.of(mobilePhone));

        WrongOperationException exception = assertThrows(WrongOperationException.class,
                () -> bookingService.bookMobilePhone(id, mobilePhone));

        assertThat(exception.getMessage()).isEqualTo("Unable to book. Mobile phone is already booked.");
    }

    @Test
    public void testBookUnExistingMobilePhoneAndGetMobilePhoneNotFoundException() {
        long id = 1;
        MobilePhone mobilePhone = MobilePhone.builder().id(1).available(true).version(0L).build();
        when(mobilePhoneRepository.findById(id)).thenReturn(Optional.empty());

        MobilePhoneNotFoundException exception = assertThrows(MobilePhoneNotFoundException.class,
                () -> bookingService.bookMobilePhone(id, mobilePhone));

        assertThat(exception.getMessage()).isEqualTo("Mobile phone not found");
    }

    @Test
    public void testReturnSuccess() {
        long id = 1;
        MobilePhone mobilePhone = MobilePhone.builder().id(1).available(false).version(0L).build();
        when(mobilePhoneRepository.findById(id)).thenReturn(Optional.of(mobilePhone));

        bookingService.returnMobilePhone(id, mobilePhone);

        assertThat(mobilePhone.getBookingTime()).isNull();
        assertThat(mobilePhone.isAvailable()).isTrue();
        verify(mobilePhoneRepository).save(mobilePhone);
    }

    @Test
    public void testReturnAvailableAndGetWrongOperationException() {
        long id = 1;
        MobilePhone mobilePhone = MobilePhone.builder().id(1).available(true).version(0L).build();
        when(mobilePhoneRepository.findById(id)).thenReturn(Optional.of(mobilePhone));

        WrongOperationException exception = assertThrows(WrongOperationException.class,
                () -> bookingService.returnMobilePhone(id, mobilePhone));

        assertThat(exception.getMessage()).isEqualTo("Unable to return. Mobile phone is not booked.");
    }

    @Test
    public void testReturnUnExistingMobilePhoneAndGetMobilePhoneNotFoundException() {
        long id = 1;
        MobilePhone mobilePhone = MobilePhone.builder().id(1).available(true).version(0L).build();
        when(mobilePhoneRepository.findById(id)).thenReturn(Optional.empty());

        MobilePhoneNotFoundException exception = assertThrows(MobilePhoneNotFoundException.class,
                () -> bookingService.returnMobilePhone(id, mobilePhone));

        assertThat(exception.getMessage()).isEqualTo("Mobile phone not found");
    }
}