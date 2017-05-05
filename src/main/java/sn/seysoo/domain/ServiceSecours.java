package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceSecours.
 */

@Document(collection = "service_secours")
public class ServiceSecours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 30)
    @Field("nom")
    private String nom;

    @Size(max = 30)
    @Field("siege")
    private String siege;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public ServiceSecours nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiege() {
        return siege;
    }

    public ServiceSecours siege(String siege) {
        this.siege = siege;
        return this;
    }

    public void setSiege(String siege) {
        this.siege = siege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceSecours serviceSecours = (ServiceSecours) o;
        if (serviceSecours.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, serviceSecours.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServiceSecours{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", siege='" + siege + "'" +
            '}';
    }
}
