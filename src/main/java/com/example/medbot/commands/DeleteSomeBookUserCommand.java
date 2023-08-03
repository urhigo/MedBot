package com.example.medbot.commands;

import com.example.medbot.help.Doctors;
import com.example.medbot.help.GenerateTime;
import com.example.medbot.model.BookFromDoctorDB;
import com.example.medbot.model.BookFromDoctorRep;
import com.example.medbot.model.UserRep;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteSomeBookUserCommand implements Command{
    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        List<Doctors> listDoctors = Arrays.stream(Doctors.values()).toList();
        List<String> listTimeInterval = new GenerateTime().generateTime();
        String [] elementsContorol = update.getMessage().getText().split(" ");
        for (Doctors doctor : listDoctors) {
            for (String timeInterval : listTimeInterval) {
                if (elementsContorol[0].equals(doctor.getTitle()) && elementsContorol[1].equals(timeInterval)){
                    List<BookFromDoctorDB> book = bookFromDoctorRep.findByUserIdAndDoctorAndTime(update.getMessage().getChatId(), doctor, timeInterval);
                    bookFromDoctorRep.delete(book.get(0));
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
                    sendMessage.setText("You delete book!");

                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    List<KeyboardRow> keyboardRows = new ArrayList<>();
                    KeyboardRow row = new KeyboardRow();
                    
                    row.add("registration");
                    row.add("look all your book");
                    row.add("look list doctors");

                    keyboardRows.add(row);
                    keyboardMarkup.setKeyboard(keyboardRows);
                    sendMessage.setReplyMarkup(keyboardMarkup);

                    return sendMessage;
                }
            }
        }
        return null;
    }
}
