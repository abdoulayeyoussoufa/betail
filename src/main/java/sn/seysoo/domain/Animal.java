package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import sn.seysoo.domain.enumeration.Sexe;

/**
 * A Animal.
 */

@Document(collection = "animal")
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("numero")
    private String numero;

    @Size(max = 15)
    @Field("couleur_robe")
    private String couleurRobe;

    @Field("sexe")
    private Sexe sexe;

    @Field("taille")
    private Double taille;

    @Field("poids")
    private Double poids;

    @Field("age")
    private Integer age;
    
    @DBRef
    @JsonManagedReference
    List<Maladie> maladies;
    
    @DBRef
    @JsonManagedReference
    List<Veterinaire> veterinaires;
    
    @DBRef
    @JsonManagedReference
    CarnetSante carnetSante;
    
    @DBRef
    @JsonManagedReference
    FicheAnimal ficheAnimal;
    
    @DBRef
    @JsonBackReference
    Troupeau troupeau;
    
    @DBRef
    @JsonManagedReference
    FicheGeolocalisatio positionCourante;
    
    @DBRef
    @JsonManagedReference
    List<FicheGeolocalisatio> positionAvant;
    
    

    public List<FicheGeolocalisatio> getPositionAvant() {
		return positionAvant;
	}

	public void setPositionAvant(List<FicheGeolocalisatio> positionAvant) {
		this.positionAvant = positionAvant;
	}

	public FicheGeolocalisatio getPositionCourante() {
		return positionCourante;
	}

	public void setPositionCourante(FicheGeolocalisatio positionCourante) {
		this.positionCourante = positionCourante;
	}

	public Troupeau getTroupeau() {
		return troupeau;
	}

	public void setTroupeau(Troupeau troupeau) {
		this.troupeau = troupeau;
	}

	public FicheAnimal getFicheAnimal() {
		return ficheAnimal;
	}

	public void setFicheAnimal(FicheAnimal ficheAnimal) {
		this.ficheAnimal = ficheAnimal;
	}

	public CarnetSante getCarnetSante() {
		return carnetSante;
	}

	public void setCarnetSante(CarnetSante carnetSante) {
		this.carnetSante = carnetSante;
	}

	public List<Veterinaire> getVeterinaires() {
		return veterinaires;
	}

	public void setVeterinaires(List<Veterinaire> veterinaires) {
		this.veterinaires = veterinaires;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Animal numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCouleurRobe() {
        return couleurRobe;
    }

    public Animal couleurRobe(String couleurRobe) {
        this.couleurRobe = couleurRobe;
        return this;
    }

    public void setCouleurRobe(String couleurRobe) {
        this.couleurRobe = couleurRobe;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Animal sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Double getTaille() {
        return taille;
    }

    public Animal taille(Double taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(Double taille) {
        this.taille = taille;
    }

    public Double getPoids() {
        return poids;
    }

    public Animal poids(Double poids) {
        this.poids = poids;
        return this;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Integer getAge() {
        return age;
    }

    public Animal age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    public List<Maladie> getMaladies() {
        return maladies;
    }

    public Animal maladies(List<Maladie> maladies) {
        this.maladies = maladies;
        return this;
    }

    public void setMaladies(List<Maladie> maladies) {
        this.maladies = maladies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        if (animal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", couleurRobe='" + couleurRobe + "'" +
            ", sexe='" + sexe + "'" +
            ", taille='" + taille + "'" +
            ", poids='" + poids + "'" +
            ", age='" + age + "'" +
            '}';
    }
}
