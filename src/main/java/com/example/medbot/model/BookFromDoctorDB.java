package com.example.medbot.model;

import com.example.medbot.help.Doctors;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BookFromDoctorDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String time;
    @Enumerated(EnumType.STRING)
    private Doctors doctor;
}
