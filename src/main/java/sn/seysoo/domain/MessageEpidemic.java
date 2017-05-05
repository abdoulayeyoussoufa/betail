package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MessageEpidemic.
 */

@Document(collection = "message_epidemic")
public class MessageEpidemic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("nom")
    private String nom;

    @NotNull
    @Field("date")
    private LocalDate date;

    @Size(max = 30)
    @Field("lieu")
    private String lieu;

    @Size(max = 256)
    @Field("description")
    private String description;
    
    
    @DBRef
    ServiceSecours serviceSecours;

    public ServiceSecours getServiceSecours() {
		return serviceSecours;
	}

	public void setServiceSecours(ServiceSecours serviceSecours) {
		this.serviceSecours = serviceSecours;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public MessageEpidemic nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public MessageEpidemic date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public MessageEpidemic lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public MessageEpidemic description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageEpidemic messageEpidemic = (MessageEpidemic) o;
        if (messageEpidemic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, messageEpidemic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MessageEpidemic{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", date='" + date + "'" +
            ", lieu='" + lieu + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
