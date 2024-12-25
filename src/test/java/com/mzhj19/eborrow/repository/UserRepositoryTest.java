//package com.mzhj19.eborrow.repository;
//
//import com.mzhj19.eborrow.model.EborrowUser;
//import org.assertj.core.api.Assertions;
////import org.h2.engine.User;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//    private static EborrowUser savedUser;
//
//
//    @BeforeAll
//    private static void initAll(@Autowired UserRepository userRepository) {
//        EborrowUser eborrowUser = EborrowUser.builder()
//                .email("mzh@gmail.com")
//                .password("123456")
//                .build();
//        savedUser = userRepository.save(eborrowUser);
//    }
//
//    @AfterAll
//    private static void cleanAll(@Autowired UserRepository userRepository) {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    void givenUserObject_whenSave_thenReturnUser() {
//        Assertions.assertThat(savedUser).isNotNull();
//        Assertions.assertThat(savedUser.getId()).isNotNull();
//        Assertions.assertThat(savedUser.getEmail()).isEqualTo("mzh@gmail.com");
//    }
//
//    @Test
//    void givenEmail_whenFindByEmail_thenReturnUserOrOptional() {
//        String given = "mzh@gmail.com";
//        Optional<EborrowUser> foundUser = userRepository.findByEmail(given);
//        Assertions.assertThat(foundUser).isNotEmpty();
//        Assertions.assertThat(foundUser.get().getEmail()).isEqualTo(given);
//    }
//
//    @Test
//    void givenEmail_whenExistsByEmail_thenReturnBoolean() {
//        String given = "mzh@gmail.com";
//        boolean foundUser = userRepository.existsByEmail(given);
//        Assertions.assertThat(foundUser).isTrue();
//    }
//}