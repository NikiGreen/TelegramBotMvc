package com.example.controller;

import com.example.model.CityInfo;
import com.example.service.CityInfoService;
import com.example.session.UserCommand;
import com.example.session.UserCommandUpdater;
import com.example.session.UserSession;
import com.example.session.UserSessionVer;
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

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;


@EnableTelegram
@BotController
public class TourBotMainController implements TelegramMvcConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(TourBotMainController.class);

    public static final String CREATE_CITY_MESSAGE = "Введите имя города и через дефис информацию о нем. Пример: Минск - Лучший город на земле!";
    public static final String UPDATE_CITY_MESSAGE = "Введите имя города информацию о которомо вы хотите изменить и через дефис информацию о нем. " +
            "Пример: Минск - Лучший город на замле!";
    public static final String DELETE_CITY_MESSAGE = "Введите имя города, который вы хотите удалить! Пример: Минск";

    private CityInfoService cityInfoService;
    private Environment environment;
    private UserSession userSession;

    @Autowired
    public TourBotMainController(CityInfoService cityInfoService, Environment environment, UserSession userSession) {
        this.cityInfoService = cityInfoService;
        this.environment = environment;
        this.userSession = userSession;
    }

    @Override
    public void configuration(TelegramBotBuilder telegramBotBuilder) {
        telegramBotBuilder
                .token(environment.getProperty("telegram.bot.token")).alias("myFirsBean");
    }

    @BotRequest("/start")
    BaseRequest hello(String text,
                      Long chatId,
                      TelegramRequest telegramRequest,
                      TelegramBot telegramBot,
                      Update update,
                      Message message,
                      Chat chat,
                      User user
    ) {
        return new SendMessage(chatId, message.text());
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
        UserSessionVer.setCreate(true);
        userSession.setCurrentUserCommandCreate(true);

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
        UserSessionVer.setUpdate(true);
        userSession.setCurrentUserCommandcUpdate(true);

        return new SendMessage(chatId, UPDATE_CITY_MESSAGE);

    }@BotRequest(value = "/delete")
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
        UserSessionVer.setDelete(true);
        userSession.setCurrentUserCommandcDelete(true);

        return new SendMessage(chatId, DELETE_CITY_MESSAGE);

    }@BotRequest(value = "{text}")
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
       String param="";
       /* if(userSession.getCreate()){
param="create";
            userSession.setCurrentUserCommandCreate(false);
        }else if(userSession.getUdate()){
param="update";
            userSession.setCurrentUserCommandcUpdate(false);
        }else if(userSession.getDelete()){
param="delete";
            userSession.setCurrentUserCommandcDelete(false);
        }else {
            if(cityInfoService.getByName(text)!=null)
            cityInfoService.getByName(text);
        }*/

        if(UserSessionVer.getCreate()){
            String[] aboutCity=text.split(" - ");
            CityInfo cityInfo=new CityInfo(aboutCity[0],aboutCity[1]);
            cityInfoService.addCityInfo(cityInfo);
            param="Описание города создано!!!";
            UserCommandUpdater.userSessionDelete(false);
        }else if(UserSessionVer.getUpdate()){
            cityInfoService.deleteByName(text);
            String[] aboutCity=text.split(" - ");
            CityInfo cityInfo=new CityInfo(aboutCity[0],aboutCity[1]);
            cityInfoService.addCityInfo(cityInfo);
            param="Описание города обновлено!!!";
            UserCommandUpdater.userSessionDelete(false);
        }else if(UserSessionVer.getDelete()){
            cityInfoService.deleteByName(text);
            param="Описание города удалено!!!";
            UserCommandUpdater.userSessionDelete(false);
        }else {

            if(cityInfoService.getByName(text).size()>0){
                param=cityInfoService.getByName(text).get(0).getCityName()+" - "+
                        cityInfoService.getByName(text).get(0).getCityInfo();
            }
            else
                param="Такого города в базе не существует!!!";
            UserCommandUpdater.userSessionDelete(false);
        }
        return new SendMessage(chatId, param);

    }
}
