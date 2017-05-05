package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

import sn.seysoo.domain.enumeration.SexeEnum;

import sn.seysoo.domain.enumeration.VitesseEnum;

import sn.seysoo.domain.enumeration.EnclosEnum;

import sn.seysoo.domain.enumeration.SituationEnum;

/**
 * A Betail.
 */

@Document(collection = "betail")
public class Betail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @Field("sexe")
    private SexeEnum sexe;

    @Field("race")
    private String race;

    @Field("couleur_robe")
    private String couleurRobe;

    @Field("vitesse")
    private VitesseEnum vitesse;

    @Field("enclos")
    private EnclosEnum enclos;

    @Field("situation")
    private SituationEnum situation;

    @Field("poids")
    private String poids;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Betail type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SexeEnum getSexe() {
        return sexe;
    }

    public Betail sexe(SexeEnum sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(SexeEnum sexe) {
        this.sexe = sexe;
    }

    public String getRace() {
        return race;
    }

    public Betail race(String race) {
        this.race = race;
        return this;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCouleurRobe() {
        return couleurRobe;
    }

    public Betail couleurRobe(String couleurRobe) {
        this.couleurRobe = couleurRobe;
        return this;
    }

    public void setCouleurRobe(String couleurRobe) {
        this.couleurRobe = couleurRobe;
    }

    public VitesseEnum getVitesse() {
        return vitesse;
    }

    public Betail vitesse(VitesseEnum vitesse) {
        this.vitesse = vitesse;
        return this;
    }

    public void setVitesse(VitesseEnum vitesse) {
        this.vitesse = vitesse;
    }

    public EnclosEnum getEnclos() {
        return enclos;
    }

    public Betail enclos(EnclosEnum enclos) {
        this.enclos = enclos;
        return this;
    }

    public void setEnclos(EnclosEnum enclos) {
        this.enclos = enclos;
    }

    public SituationEnum getSituation() {
        return situation;
    }

    public Betail situation(SituationEnum situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(SituationEnum situation) {
        this.situation = situation;
    }

    public String getPoids() {
        return poids;
    }

    public Betail poids(String poids) {
        this.poids = poids;
        return this;
    }

    public void setPoids(String poids) {
        this.poids = poids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Betail betail = (Betail) o;
        if (betail.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, betail.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Betail{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", sexe='" + sexe + "'" +
            ", race='" + race + "'" +
            ", couleurRobe='" + couleurRobe + "'" +
            ", vitesse='" + vitesse + "'" +
            ", enclos='" + enclos + "'" +
            ", situation='" + situation + "'" +
            ", poids='" + poids + "'" +
            '}';
    }
}
