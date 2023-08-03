package com.example.medbot.commands.doctors;

import com.example.medbot.commands.Command;
import com.example.medbot.help.RegistrationHelper;
import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BookFromGinekologCommand implements Command {

    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if (update.getMessage().getText().equals("GINEKOLOG")) {
            new RegistrationHelper().setInfDoctorTimePointReg(update, userRep);
            return new RegistrationHelper().getFreeTimeIntervals(update, bookFromDoctorRep);
        }
        return null;
    }
}
