package ru.job4j.cars_storage.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.job4j.cars_storage.domain.Advert;

import java.sql.Timestamp;


@Service
public class FilterService {

    public Specification<Advert> getUserFilter(String login) {
        return (advert, cq, cb) -> {
            return cb.equal(advert.get("user").get("login"), login);};
    }

    public Specification<Advert> getCategoryFilter(String category) {
        return (advert, cq, cb) -> {
            return cb.equal(advert.get("car").get("category"), category);
        };
    }

    public Specification<Advert> getDateFilter(Timestamp date) {
        return (advert, cq, cb) -> {
            return cb.greaterThan(advert.get("created"), date);
        };
    }
}
