package sn.seysoo.multiplex.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Assure.
 */
@Entity
@Table(name = "assure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String prenom;

    @Column(name = "sexe")
    @JsonView(DataTablesOutput.View.class)
    private String sexe;

    @Column(name = "date_naissance")
    @JsonView(DataTablesOutput.View.class)
    private String date_naissance;

    @Column(name = "lieu")
    @JsonView(DataTablesOutput.View.class)
    private String lieu;

    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "email")
    @JsonView(DataTablesOutput.View.class)
    private String email;

    @Column(name = "tel_f")
    private String telF;

    @Column(name = "tel_p")
    private String telP;

    @Column(name = "matricule")
    @JsonView(DataTablesOutput.View.class)
    private String matricule;

    @ManyToOne
    @JsonView(DataTablesOutput.View.class)
    private Police assure_police;

    @ManyToOne
    @JsonView(DataTablesOutput.View.class)
    private Groupe assure_groupe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Assure nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Assure prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public Assure sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public Assure date_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
        return this;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getLieu() {
        return lieu;
    }

    public Assure lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getEmail() {
        return email;
    }

    public Assure email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelF() {
        return telF;
    }

    public Assure telF(String telF) {
        this.telF = telF;
        return this;
    }

    public void setTelF(String telF) {
        this.telF = telF;
    }

    public String getTelP() {
        return telP;
    }

    public Assure telP(String telP) {
        this.telP = telP;
        return this;
    }

    public void setTelP(String telP) {
        this.telP = telP;
    }

    public String getMatricule() {
        return matricule;
    }

    public Assure matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Police getAssure_police() {
        return assure_police;
    }

    public Assure assure_police(Police police) {
        this.assure_police = police;
        return this;
    }

    public void setAssure_police(Police police) {
        this.assure_police = police;
    }

    public Groupe getAssure_groupe() {
        return assure_groupe;
    }

    public Assure assure_groupe(Groupe groupe) {
        this.assure_groupe = groupe;
        return this;
    }

    public void setAssure_groupe(Groupe groupe) {
        this.assure_groupe = groupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Assure assure = (Assure) o;
        if(assure.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, assure.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Assure{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", sexe='" + sexe + "'" +
            ", date_naissance='" + date_naissance + "'" +
            ", lieu='" + lieu + "'" +
            ", email='" + email + "'" +
            ", telF='" + telF + "'" +
            ", telP='" + telP + "'" +
            ", matricule='" + matricule + "'" +
            '}';
    }
}
