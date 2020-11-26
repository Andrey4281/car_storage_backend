package ru.job4j.cars_storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "advert_id", foreignKey = @ForeignKey(name="attached_file_advert_id_fk"), nullable = false)
    @Fetch(FetchMode.JOIN)
    private Advert advert;

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
}
