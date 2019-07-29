package com.example.controller;

import com.example.model.CityInfo;
import com.example.service.CityInfoService;
import com.example.session.UserCommandUpdater;
import com.example.session.UserSession;
import com.github.telegram.mvc.api.BotController;
import com.github.telegram.mvc.api.BotRequest;
import com.github.telegram.mvc.api.EnableTelegram;
import com.github.telegram.mvc.api.TelegramRequest;
import com.github.telegram.mvc.config.TelegramBotBuilder;
import com.github.telegram.mvc.config.TelegramMvcConfiguration;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


@EnableTelegram
@BotController
public class TourBotMainController implements TelegramMvcConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TourBotMainController.class);

    private static final String CREATE_CITY_MESSAGE = "Введите имя города и через дефис информацию о нем. Пример: Минск - Лучший город на земле!";
    private static final String UPDATE_CITY_MESSAGE = "Введите имя города информацию о которомо вы хотите изменить и через дефис информацию о нем. " +
            "Пример: Минск - Лучший город на земле!";
    private static final String DELETE_CITY_MESSAGE = "Введите имя города, который вы хотите удалить! Пример: Минск";
    private static final String INFO_MESSAGE = "Это бот для сохранения и вывода информации о городах.\n\nБот чувствителен к пробелам, т.е. описание города должно быть строго" +
            " описано в формате \"Город - описание. \", а не \"Город-описание\".\n\nДля управления ботом используются команды \"/create\",\"/update\",\"/delete\",\"/info\".";

    private CityInfoService cityInfoService;
    private Environment environment;


    @Autowired
    public TourBotMainController(CityInfoService cityInfoService, Environment environment) {
        this.cityInfoService = cityInfoService;
        this.environment = environment;

    }

    @Override
    public void configuration(TelegramBotBuilder telegramBotBuilder) {
        telegramBotBuilder
                .token(environment.getProperty("telegram.bot.token")).alias("myFirsBean");
    }

    @BotRequest("/info")
    BaseRequest hello(String text,
                      Long chatId,
                      TelegramRequest telegramRequest,
                      TelegramBot telegramBot,
                      Update update,
                      Message message,
                      Chat chat,
                      User user
    ) {
        return new SendMessage(chatId, INFO_MESSAGE);
    }

    @BotRequest(value = "/create")
    BaseRequest create(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
        UserSession.setCreate(true);
        UserSession.setUpdate(false);
        UserSession.setDelete(false);

        return new SendMessage(chatId, CREATE_CITY_MESSAGE);

    }

    @BotRequest(value = "/update")
    BaseRequest update(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
        UserSession.setCreate(false);
        UserSession.setUpdate(true);
        UserSession.setDelete(false);


        return new SendMessage(chatId, UPDATE_CITY_MESSAGE);

    }

    @BotRequest(value = "/delete")
    BaseRequest delete(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
        UserSession.setCreate(false);
        UserSession.setUpdate(false);
        UserSession.setDelete(true);


        return new SendMessage(chatId, DELETE_CITY_MESSAGE);

    }

    @BotRequest(value = "{text}")
    BaseRequest answer(String text,
                       Long chatId,
                       TelegramRequest telegramRequest,
                       TelegramBot telegramBot,
                       Update update,
                       Message message,
                       Chat chat,
                       User user,
                       String id
    ) {
        String parametrForAnswerAfterCommand;

        if (UserSession.getCreate()) {
            UserCommandUpdater.userSessionDelete(false);
            String[] aboutCity = text.split(" - ");
            CityInfo cityInfo = new CityInfo(aboutCity[0], aboutCity[1]);
            cityInfoService.addCityInfo(cityInfo);
            parametrForAnswerAfterCommand = "Описание города создано!!!";

        } else if (UserSession.getUpdate()) {
            UserCommandUpdater.userSessionDelete(false);
            String[] aboutCity = text.split(" - ");
            cityInfoService.deleteByName(aboutCity[0]);
            CityInfo cityInfo = new CityInfo(aboutCity[0], aboutCity[1]);
            cityInfoService.addCityInfo(cityInfo);
            parametrForAnswerAfterCommand = "Описание города обновлено!!!";

        } else if (UserSession.getDelete()) {
            UserCommandUpdater.userSessionDelete(false);
            cityInfoService.deleteByName(text);
            parametrForAnswerAfterCommand = "Описание города удалено!!!";

        } else {
            UserCommandUpdater.userSessionDelete(false);
            if (cityInfoService.getByName(text).size() > 0) {
                parametrForAnswerAfterCommand = cityInfoService.getByName(text).get(0).getCityName() + " - " +
                        cityInfoService.getByName(text).get(0).getCityInfo();
            } else
                parametrForAnswerAfterCommand = "Такого города в базе не существует!!!";

        }
        return new SendMessage(chatId, parametrForAnswerAfterCommand);

    }
}
