package ru.job4j.cars_storage.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "attached_file")
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "real_name", nullable = false)
    private String realName;

    @NotNull
    @Column(name = "file_size", nullable = false)
    private String fileSize;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "path_to_file")
    private String pathToFile;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "advert_id", foreignKey = @ForeignKey(name="attached_file_advert_id_fk"), nullable = false)
    @Fetch(FetchMode.JOIN)
    private Advert advert;

    public AttachedFile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachedFile that = (AttachedFile) o;
        return id.equals(that.id) &&
                realName.equals(that.realName) &&
                fileSize.equals(that.fileSize) &&
                fileExtension.equals(that.fileExtension) &&
                pathToFile.equals(that.pathToFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, realName, fileSize, fileExtension, pathToFile);
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }
}
