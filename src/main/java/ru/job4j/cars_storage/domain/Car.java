package ru.job4j.cars_storage.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "engine", nullable = false)
    private String engine;

    @Column(name = "transmission", nullable = false)
    private String transmission;

    @Column(name = "carcass", nullable = false)
    private String carcass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                Objects.equals(category, car.category) &&
                Objects.equals(brand, car.brand) &&
                Objects.equals(engine, car.engine) &&
                Objects.equals(transmission, car.transmission) &&
                Objects.equals(carcass, car.carcass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, brand, engine, transmission, carcass);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", engine='" + engine + '\'' +
                ", transmission='" + transmission + '\'' +
                ", carcass='" + carcass + '\'' +
                '}';
    }
}
