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

public class LookListDoctorsCommand  implements Command{

    @Override
    public SendMessage bodyCommand(Update update, UserRep userRep, BookFromDoctorRep bookFromDoctorRep) {
        if(update.getMessage().getText().equals("look list doctors")){
            List<Doctors> listDoctors = Arrays.stream(Doctors.values()).toList();
            StringBuilder textFPart = new StringBuilder("Information about doctors:\n");
            for (Doctors doctor : listDoctors) {
                List<BookFromDoctorDB> listBooksDoctor = bookFromDoctorRep.findAllByDoctor(doctor);
                List<String> timeIntervals = new GenerateTime().generateTime();
                if (!listBooksDoctor.isEmpty()){
                    for (BookFromDoctorDB bookFromDoctor : listBooksDoctor) {
                        for (int i = 0; i < timeIntervals.size(); i++) {
                            if(bookFromDoctor.getTime().equals(timeIntervals.get(i))){
                                timeIntervals.remove(i);
                                break;
                            }
                        }
                    }
                }
                String textSPart = "\n" + doctor.getTitle() + "has free time:\n" +
                        timeIntervals + "\n" +
                        "--------------";
                textFPart.append(textSPart);

            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            sendMessage.setText(String.valueOf(textFPart));

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();

            row.add("registration");
            row.add("delete book");
            row.add("look all your book");

            keyboardRows.add(row);
            keyboardMarkup.setKeyboard(keyboardRows);
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        }

        return null;
    }
}
