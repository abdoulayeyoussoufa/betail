package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FicheTracabilite.
 */

@Document(collection = "fiche_tracabilite")
public class FicheTracabilite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("proprietaire_actu")
    private String proprietaireActu;

    @NotNull
    @Field("date_obt")
    private LocalDate dateObt;

    @NotNull
    @Size(max = 30)
    @Field("lieu_actuel")
    private String lieuActuel;

    @Size(max = 30)
    @Field("dernier_prop")
    private String dernierProp;
    
    @DBRef
    @JsonBackReference
    Animal animal;
    
    

    public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProprietaireActu() {
        return proprietaireActu;
    }

    public FicheTracabilite proprietaireActu(String proprietaireActu) {
        this.proprietaireActu = proprietaireActu;
        return this;
    }

    public void setProprietaireActu(String proprietaireActu) {
        this.proprietaireActu = proprietaireActu;
    }

    public LocalDate getDateObt() {
        return dateObt;
    }

    public FicheTracabilite dateObt(LocalDate dateObt) {
        this.dateObt = dateObt;
        return this;
    }

    public void setDateObt(LocalDate dateObt) {
        this.dateObt = dateObt;
    }

    public String getLieuActuel() {
        return lieuActuel;
    }

    public FicheTracabilite lieuActuel(String lieuActuel) {
        this.lieuActuel = lieuActuel;
        return this;
    }

    public void setLieuActuel(String lieuActuel) {
        this.lieuActuel = lieuActuel;
    }

    public String getDernierProp() {
        return dernierProp;
    }

    public FicheTracabilite dernierProp(String dernierProp) {
        this.dernierProp = dernierProp;
        return this;
    }

    public void setDernierProp(String dernierProp) {
        this.dernierProp = dernierProp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FicheTracabilite ficheTracabilite = (FicheTracabilite) o;
        if (ficheTracabilite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ficheTracabilite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FicheTracabilite{" +
            "id=" + id +
            ", proprietaireActu='" + proprietaireActu + "'" +
            ", dateObt='" + dateObt + "'" +
            ", lieuActuel='" + lieuActuel + "'" +
            ", dernierProp='" + dernierProp + "'" +
            '}';
    }
}
