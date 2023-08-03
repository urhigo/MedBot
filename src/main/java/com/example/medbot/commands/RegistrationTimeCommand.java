package com.example.medbot.commands;

import com.example.medbot.help.Doctors;
import com.example.medbot.help.GenerateTime;
import com.example.medbot.model.BookFromDoctorDB;
import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.User;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrationTimeCommand implements Command{
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {

        List<String> timeIntervals = new GenerateTime().generateTime();
        for (String interval : timeIntervals) {
            if(update.getMessage().getText().equals(interval)){
                Optional<User> user = userRep.findById(update.getMessage().getChatId());

                BookFromDoctorDB bookFromDoctor = new BookFromDoctorDB();
                bookFromDoctor.setTime(update.getMessage().getText());
                bookFromDoctor.setDoctor(Doctors.valueOf(user.get().getTimePointRegFromDoctor()));
                bookFromDoctor.setUserId(update.getMessage().getChatId());

                bookFromDoctorRep.save(bookFromDoctor);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                sendMessage.setText("You are registrate from " + user.get().getTimePointRegFromDoctor() + " at: " + update.getMessage().getText());
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();

                row.add("registration");
                row.add("look all your book");
                keyboardRows.add(row);

                row = new KeyboardRow();

                row.add("delete book");
                row.add("look list doctors");

                keyboardRows.add(row);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                return  sendMessage;
            }
        }
        return null;
    }
}