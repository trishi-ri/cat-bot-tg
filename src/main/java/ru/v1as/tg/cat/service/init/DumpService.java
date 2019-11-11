package ru.v1as.tg.cat.service.init;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DumpService {

    private final EntityManager em;

    public String write() {
        final String fileName = String.format("cat_database_%s.sql", new Date().getTime());
        final String sqlString = String.format("SCRIPT TO '%s';", fileName);
        final Query nativeQuery = em.createNativeQuery(sqlString);
        nativeQuery.getResultList();
        return fileName;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAllAndLoad(String fileName) {
        em.createNativeQuery("DROP ALL OBJECTS").executeUpdate();
        em.createNativeQuery(String.format("RUNSCRIPT FROM '%s';", fileName)).executeUpdate();
    }
}
