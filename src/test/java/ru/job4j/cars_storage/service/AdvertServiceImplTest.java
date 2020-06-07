package ru.job4j.cars_storage.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.job4j.cars_storage.domain.Advert;
import ru.job4j.cars_storage.domain.Car;
import ru.job4j.cars_storage.repository.AdvertRepository;
import ru.job4j.cars_storage.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Integration AdvertService Test")
@TestPropertySource(locations = "classpath:application-h2.properties")
@SpringBootTest
class AdvertServiceImplTest {

    @Autowired
    AdvertService advertService;

    @Autowired
    AdvertRepository advertRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Should find advert")
    @SqlGroup({
            @Sql(value = "classpath:db/test-data-find.sql",
            config = @SqlConfig(encoding = "utf-8",
            separator = ";", commentPrefix = "--"),
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/clean-up-find.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";", commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void whenGetAdvertShouldGetIt() {
        Optional<Advert> advert = advertService.findOne(1L);
        assertEquals(1L, advert.get().getId());
        assertEquals(1L, advert.get().getCar().getId());
        assertEquals(1L, advert.get().getUser().getId());
    }

    @Test
    @DisplayName("Should delete advert")
    @SqlGroup({
            @Sql(value = "classpath:db/test-data-delete.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";", commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/clean-up-delete.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";", commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void whenDeleteAdvertThenShouldNotGetIt() {
        advertService.delete(2L);
        Optional<Advert> advert = advertRepository.findById(2L);
        assertEquals(false, advert.isPresent());
    }

    @Test
    @DisplayName("Should save advert")
    @SqlGroup({
            @Sql(value = "classpath:db/test-data-save.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";", commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/clean-up-save.sql",
            config = @SqlConfig(encoding = "utf-8",
                    separator = ";", commentPrefix = "--"),
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void whenSaveAdvertThenShouldGetIt() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        UserDetails mockSimpleUserObject = userRepository.findById(3L).get();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(mockSimpleUserObject);
        Advert advertExpected = new Advert();
        advertExpected.setDescription("saveTest");
        Car car = new Car("saveTest", "saveTest", "saveTest", "saveTest", "saveTest");
        advertExpected.setCar(car);

        Advert advertActual = advertService.save(advertExpected, null, null);

        assertEquals(advertExpected, advertActual);
    }

    @Test
    @DisplayName("Should get adverts by criteria")
    @SqlGroup({
            @Sql(value = "classpath:db/test-data-criteria.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";", commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(value = "classpath:db/clean-up-criteria.sql",
                    config = @SqlConfig(encoding = "utf-8",
                            separator = ";", commentPrefix = "--"),
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)})
    void whenFindAdvertsByCriteriaThenShouldGetThem() {
        List<Advert> userAdverts = new LinkedList<>();
        List<Advert> soldAdverts = new LinkedList<>();
        List<Advert> carCategoryAdverts = new LinkedList<>();
        List<Advert> allAdverts = new LinkedList<>();
        initializeAdvertsLists(userAdverts, soldAdverts, carCategoryAdverts, allAdverts);

        assertEquals(1, userAdverts.size());
        assertEquals(4L, userAdverts.iterator().next().getId());
        assertEquals(1, soldAdverts.size());
        assertEquals(5L, soldAdverts.iterator().next().getId());
        assertEquals(1, carCategoryAdverts.size());
        assertEquals(4L, carCategoryAdverts.iterator().next().getId());
        assertEquals(2, allAdverts.size());
    }


    private void initializeAdvertsLists(List<Advert> userAdverts, List<Advert> soldAdverts
    ,List<Advert> carCategoryAdverts, List<Advert> allAdverts) {
        Map<String, String> reqParam = new HashMap<>();
        reqParam.put("userLogin", "criteriaTest");
        reqParam.put("page", "0");
        reqParam.put("size", "20");
        userAdverts.addAll(advertService.findAll(reqParam).getContent());

        reqParam.remove("userLogin");
        reqParam.put("advertStatus", "true");
        soldAdverts.addAll(advertService.findAll(reqParam).getContent());

        reqParam.remove("advertStatus");
        reqParam.put("carCategory", "criteriaTest");
        carCategoryAdverts.addAll(advertService.findAll(reqParam).getContent());

        reqParam.remove("carCategory");
        allAdverts.addAll(advertService.findAll(reqParam).getContent());
    }
}
