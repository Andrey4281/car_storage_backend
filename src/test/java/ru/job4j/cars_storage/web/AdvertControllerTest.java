package ru.job4j.cars_storage.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.job4j.cars_storage.domain.Advert;
import ru.job4j.cars_storage.service.AdvertService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Unit AdvertController Test")
class AdvertControllerTest {

    @Test
    @DisplayName("Should return some advert")
    void whenGetSomeAdvertShouldGetIt() {
        AdvertService advertService = mock(AdvertService.class);
        when(advertService.findOne(1L)).thenReturn(Optional.of(new
                Advert(1L)));
        AdvertController advertController = new AdvertController(advertService);

        assertEquals(Optional.of(new Advert(1L)), advertController.getAdvert(1L).getBody());
    }

    @Test
    @DisplayName("Should create new advert")
    void whenCreateNewAdvertThenShouldGetIt() {
        Advert advert = new Advert();
        advert.setId(1L);
        advert.setDescription("test");
        advert.setCreated(Timestamp.from(Instant.now()));
        List<Advert> adverts = new LinkedList<>();
        AdvertService advertService = mock(AdvertService.class);
        when(advertService.save(advert, null, null))
                .thenAnswer(new Answer<Advert>() {
                    @Override
                    public Advert answer(InvocationOnMock invocationOnMock) throws Throwable {
                        adverts.add(advert);
                        return advert;
                    }
                });

        AdvertController advertController = new AdvertController(advertService);
        Advert actual = advertController.createAdvert(advert, null).getBody();

        assertEquals(adverts.get(0), actual);
    }

    @Test
    @DisplayName("Should delete advert")
    void whenDeleteAdvertThenShouldNotGetIt() {
        Advert advert = new Advert();
        advert.setId(1L);
        advert.setDescription("test");
        advert.setCreated(Timestamp.from(Instant.now()));
        List<Advert> adverts = new LinkedList<>();
        adverts.add(advert);
        AdvertService advertService = mock(AdvertService.class);
        when(advertService.findOne(1L)).thenReturn(Optional.of(advert));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                adverts.removeIf(u -> u.getId() == 1L);
                return null;
            }
        }).when(advertService).delete(1L);

        AdvertController advertController = new AdvertController(advertService);
        advertController.deleteAdvert(1L);

        assertEquals(0, adverts.size());
    }

    @Test
    @DisplayName("Should update advert")
    void whenUpdateAdvertThenShouldReturnUpdatedAdvert() throws BadRequestException {
        Advert oldAdvert = new Advert();
        oldAdvert.setId(1L);
        oldAdvert.setDescription("test");
        oldAdvert.setCreated(Timestamp.from(Instant.now()));
        List<Advert> adverts = new LinkedList<>();
        adverts.add(oldAdvert);
        Advert newAdvert = new Advert();
        newAdvert.setId(1L);
        newAdvert.setDescription("test2");
        newAdvert.setCreated(Timestamp.from(Instant.now()));
        AdvertService advertService = mock(AdvertService.class);
        when(advertService.save(newAdvert, null, null))
                .thenAnswer(new Answer<Advert>() {
                    @Override
                    public Advert answer(InvocationOnMock invocationOnMock) throws Throwable {
                        adverts.set(0, newAdvert);
                        return newAdvert;
                    }
                });

        AdvertController advertController = new AdvertController(advertService);
        Advert actual = advertController.updateAdvert(newAdvert, null, null).getBody();

        assertEquals(adverts.get(0), actual);
        assertEquals(1, adverts.size());
    }


}
