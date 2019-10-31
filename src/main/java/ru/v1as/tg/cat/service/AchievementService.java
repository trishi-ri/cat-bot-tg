package ru.v1as.tg.cat.service;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.v1as.tg.cat.jpa.dao.ActionDao;
import ru.v1as.tg.cat.jpa.entities.Action;
import ru.v1as.tg.cat.jpa.entities.ActionType;

@Service
@Slf4j
@RequiredArgsConstructor
public class AchievementService {

    private final ActionDao actionDao;

    @PostConstruct
    public void init() {
        Action action = new Action();
        action.setAction(ActionType.CAT_REQUEST);
        action.setChatId(1L);
        action.setUserId(1L);
        actionDao.save(action);
        log.info(actionDao.findAll().toString());
    }
}