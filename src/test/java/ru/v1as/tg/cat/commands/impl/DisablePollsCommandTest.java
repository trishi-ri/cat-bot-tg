package ru.v1as.tg.cat.commands.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.v1as.tg.cat.AbstractCatBotTest;
import ru.v1as.tg.cat.jpa.dao.ChatDetailsDao;

public class DisablePollsCommandTest extends AbstractCatBotTest {

    @Autowired ChatDetailsDao chatDetailsDao;

    @Test
    public void shouldEnableAndDisablePollsInChat() {
        assertFalse(chatDetailsDao.getOne(getChatId()).isCatPollEnabled());

        bob.inPublic().sendCommand("/enable_polls");
        inPublic.getSendMessage().assertText("Создание опросов теперь включено");
        assertTrue(chatDetailsDao.getOne(getChatId()).isCatPollEnabled());

        bob.inPublic().sendPhotoMessage();
        bob.inPublic().getSendMessage().assertContainText("Это кот?");
        assertMethodsQueueIsEmpty();

        bob.inPublic().sendCommand("/enable_polls");
        inPublic.getSendMessage().assertText("Создание опросов уже включено");
        assertTrue(chatDetailsDao.getOne(getChatId()).isCatPollEnabled());

        bob.inPublic().sendPhotoMessage();
        bob.inPublic().getSendMessage().assertContainText("Это кот?");
        assertMethodsQueueIsEmpty();

        bob.inPublic().sendCommand("/disable_polls");
        inPublic.getSendMessage().assertText("Создание опросов теперь выключено");
        assertFalse(chatDetailsDao.getOne(getChatId()).isCatPollEnabled());

        bob.inPublic().sendPhotoMessage();
        assertMethodsQueueIsEmpty();

        bob.inPublic().sendCommand("/disable_polls");
        inPublic.getSendMessage().assertText("Создание опросов уже выключено");
        assertFalse(chatDetailsDao.getOne(getChatId()).isCatPollEnabled());

        bob.inPublic().sendPhotoMessage();
        assertMethodsQueueIsEmpty();
    }
}
