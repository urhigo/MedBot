package com.example.medbot.commands;

import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    SendMessage bodyCommand (Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep);
}
