package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import sn.seysoo.domain.enumeration.Language;

import sn.seysoo.domain.enumeration.Sexe;

/**
 * A Eleveur.
 */

@Document(collection = "eleveur")
public class Eleveur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("numero")
    private Integer numero;

    @NotNull
    @Size(max = 30)
    @Field("prenom")
    private String prenom;

    @NotNull
    @Size(max = 30)
    @Field("nom")
    private String nom;

    @Field("langue")
    private Language langue;

    @Field("sex")
    private Sexe sex;

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Field("email")
    private String email;

    @NotNull
    @Pattern(regexp = "^[1-2](\\w){3}(\\d){4}(\\d){5}$")
    @Field("numero_cni")
    private String numeroCNI;

    @Field("telephone")
    private String telephone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Eleveur numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPrenom() {
        return prenom;
    }

    public Eleveur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Eleveur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Language getLangue() {
        return langue;
    }

    public Eleveur langue(Language langue) {
        this.langue = langue;
        return this;
    }

    public void setLangue(Language langue) {
        this.langue = langue;
    }

    public Sexe getSex() {
        return sex;
    }

    public Eleveur sex(Sexe sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sexe sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public Eleveur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroCNI() {
        return numeroCNI;
    }

    public Eleveur numeroCNI(String numeroCNI) {
        this.numeroCNI = numeroCNI;
        return this;
    }

    public void setNumeroCNI(String numeroCNI) {
        this.numeroCNI = numeroCNI;
    }

    public String getTelephone() {
        return telephone;
    }

    public Eleveur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Eleveur eleveur = (Eleveur) o;
        if (eleveur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eleveur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Eleveur{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", prenom='" + prenom + "'" +
            ", nom='" + nom + "'" +
            ", langue='" + langue + "'" +
            ", sex='" + sex + "'" +
            ", email='" + email + "'" +
            ", numeroCNI='" + numeroCNI + "'" +
            ", telephone='" + telephone + "'" +
            '}';
    }
}
