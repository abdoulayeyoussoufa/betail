package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Veterinaire.
 */

@Document(collection = "veterinaire")
public class Veterinaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("prenom")
    private String prenom;

    @NotNull
    @Size(max = 30)
    @Field("nom")
    private String nom;
    
    @DBRef
    @JsonBackReference
    List<Animal> animaux;

    public List<Animal> getAnimaux() {
		return animaux;
	}

	public void setAnimaux(List<Animal> animaux) {
		this.animaux = animaux;
	}

	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Field("email")
    private String email;

    @Field("telephone")
    private String telephone;

    @Pattern(regexp = "^[1-2](\\w){3}(\\d){4}(\\d){5}$")
    @Field("numero_cni")
    private String numeroCNI;
    
    @DBRef
    @JsonManagedReference
    Adresse adresse;

    public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public Veterinaire prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Veterinaire nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public Veterinaire email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public Veterinaire telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumeroCNI() {
        return numeroCNI;
    }

    public Veterinaire numeroCNI(String numeroCNI) {
        this.numeroCNI = numeroCNI;
        return this;
    }

    public void setNumeroCNI(String numeroCNI) {
        this.numeroCNI = numeroCNI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Veterinaire veterinaire = (Veterinaire) o;
        if (veterinaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, veterinaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Veterinaire{" +
            "id=" + id +
            ", prenom='" + prenom + "'" +
            ", nom='" + nom + "'" +
            ", email='" + email + "'" +
            ", telephone='" + telephone + "'" +
            ", numeroCNI='" + numeroCNI + "'" +
            '}';
    }
}
