package com.example.medbot.model;

import com.example.medbot.help.Doctors;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookFromDoctorRep extends CrudRepository<BookFromDoctorDB, Long> {
    List<BookFromDoctorDB> findAllByDoctor(Doctors doctor);
    List<BookFromDoctorDB> findAllByUserId(Long userId);
    List<BookFromDoctorDB> findByUserIdAndDoctorAndTime(Long userId, Doctors doctor, String time);
}
