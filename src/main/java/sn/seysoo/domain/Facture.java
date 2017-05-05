package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A Facture.
 */

@Document(collection = "facture")
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("dat_facture")
    private LocalDate datFacture;

    @NotNull
    @Field("num_facture")
    private Integer numFacture;

    @NotNull
    @Field("montant")
    private Double montant;
    
    @DBRef
    Commande commande;
    
    @DBRef
    
    List<Accessoire> accesoires ;

    public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDatFacture() {
        return datFacture;
    }

    public Facture datFacture(LocalDate datFacture) {
        this.datFacture = datFacture;
        return this;
    }

    public void setDatFacture(LocalDate datFacture) {
        this.datFacture = datFacture;
    }

    public Integer getNumFacture() {
        return numFacture;
    }

    public Facture numFacture(Integer numFacture) {
        this.numFacture = numFacture;
        return this;
    }

    public void setNumFacture(Integer numFacture) {
        this.numFacture = numFacture;
    }

    public Double getMontant() {
        return montant;
    }

    public Facture montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Facture facture = (Facture) o;
        if (facture.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, facture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Facture{" +
            "id=" + id +
            ", datFacture='" + datFacture + "'" +
            ", numFacture='" + numFacture + "'" +
            ", montant='" + montant + "'" +
            '}';
    }
}
