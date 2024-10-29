package com.ssafy.domain.user.model.entity;

import com.ssafy.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = com.ssafy.KoalaBackApplication.class)
class UserTest {

    private static final Logger log = LoggerFactory.getLogger(UserTest.class);
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testEntity() throws Exception {
//        Auth auth = new Auth("user");
////        em.persist(auth);
//
//        User user = new User("loginId", "password", auth, "name", "nickname", 0, 0L, 1);
////        log.info("user: {}", user);
//        em.persist(user);
//
//        em.flush();
//        em.clear();
//
//        User findUser = em.find(User.class, user.getUserId());
//
//        assertEquals(user.getUserId(), findUser.getUserId());
//
//        em.remove(findUser);
//
//        assertNull(em.find(User.class, user.getUserId()));
    }

}