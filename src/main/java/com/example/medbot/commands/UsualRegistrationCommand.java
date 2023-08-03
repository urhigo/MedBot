package com.example.medbot.commands;

import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class UsualRegistrationCommand implements Command{
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if(update.getMessage().getText().equals("registration")){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Choose doctor");
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();

            row.add("TERAPEVT");
            row.add("OKULIST");
            row.add("LOR");

            keyboardRows.add(row);

            row = new KeyboardRow();

            row.add("GASTROENTEROLOG");
            row.add("HIRURG");
            row.add("GINEKOLOG");

            keyboardRows.add(row);

            keyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        }
        return null;
    }
}
