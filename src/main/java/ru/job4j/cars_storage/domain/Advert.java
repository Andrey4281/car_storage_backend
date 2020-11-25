package ru.job4j.cars_storage.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="adverts")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="created")
    private Timestamp created;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", foreignKey = @ForeignKey(name="adverts_users_id_fk"), nullable = false)
    @Fetch(FetchMode.JOIN)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name="car_id", foreignKey = @ForeignKey(name="adverts_cars_id_fk"), nullable = false)
    @Fetch(FetchMode.JOIN)
    private Car car;

    @OneToMany(mappedBy = "advert", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @Fetch(FetchMode.JOIN)
    private List<AttachedFile> attachedFiles = new LinkedList<>();

    public List<AttachedFile> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<AttachedFile> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public Advert(Long id) {
        this.id = id;
    }

    public Advert() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advert advert = (Advert) o;
        return id == advert.id &&
                status == advert.status &&
                Objects.equals(created, advert.created) &&
                Objects.equals(description, advert.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, description, status);
    }

    @Override
    public String toString() {
        return "Advert{" +
                "id=" + id +
                ", created=" + created +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", user=" + user +
                ", car=" + car +
                '}';
    }
}
