package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Accessoire.
 */

@Document(collection = "accessoire")
public class Accessoire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 5)
    @Field("code")
    private String code;

    @Size(max = 4)
    @Field("type")
    private String type;

    @Size(max = 10)
    @Field("couleur")
    private String couleur;

    @Size(max = 10)
    @Field("prix")
    private String prix;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Accessoire code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public Accessoire type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouleur() {
        return couleur;
    }

    public Accessoire couleur(String couleur) {
        this.couleur = couleur;
        return this;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getPrix() {
        return prix;
    }

    public Accessoire prix(String prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Accessoire accessoire = (Accessoire) o;
        if (accessoire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, accessoire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Accessoire{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", type='" + type + "'" +
            ", couleur='" + couleur + "'" +
            ", prix='" + prix + "'" +
            '}';
    }
}
