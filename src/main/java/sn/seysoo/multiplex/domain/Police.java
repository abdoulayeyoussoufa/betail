package sn.seysoo.multiplex.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * A Police.
 */
@Entity
@Table(name = "police")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Police implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @NotNull
    @Column(name = "raison", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String raison;

    @Column(name = "adresse")
    @JsonView(DataTablesOutput.View.class)
    private String adresse;

    @NotNull
    @Column(name = "tel", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String tel;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "email", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private String email;

    @Column(name = "interloc")
    @JsonView(DataTablesOutput.View.class)
    private String interloc;

    @Column(name = "date_debut")
    @JsonView(DataTablesOutput.View.class)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    @JsonView(DataTablesOutput.View.class)
    private LocalDate dateFin;

    @Column(name = "tiers")
    private String tiers;

    @Column(name = "tpx")
    private Double tpx;

    @Column(name = "activite")
    private String activite;

    @Column(name = "formule_soins")
    private String formule_soins;

    @Column(name = "territoire")
    private String territoire;

    @Column(name = "plafond")
    private Integer plafond;

    @Column(name = "complement")
    private String complement;

    @Column(name = "consultation_taux")
    private Float consultation_taux;

    @Column(name = "consultation_frais")
    private String consultation_frais;

    @NotNull
    @Column(name = "consultation_limite", nullable = false)
    private String consultation_limite;

    @Column(name = "consultation_jour")
    private Float consultation_jour;

    @Column(name = "consultation_an")
    private Float consultation_an;

    @NotNull
    @Column(name = "consultation_deux_an", nullable = false)
    private Float consultation_deux_an;

    @Column(name = "pharmacie_taux")
    private Float pharmacie_taux;

    @Column(name = "pharmacie_frais")
    private String pharmacie_frais;

    @Column(name = "pharmacie_limite")
    private String pharmacie_limite;

    @Column(name = "pharmacie_jour")
    private Float pharmacie_jour;

    @Column(name = "pharmacie_an")
    private Float pharmacie_an;

    @Column(name = "pharmacie_deux_an")
    private Float pharmacie_deux_an;

    @Column(name = "analyse_taux")
    private Float analyse_taux;

    @Column(name = "analyse_frais")
    private String analyse_frais;

    @Column(name = "analyse_limite")
    private String analyse_limite;

    @Column(name = "analyse_jour")
    private Float analyse_jour;

    @Column(name = "analyse_an")
    private Float analyse_an;

    @Column(name = "analyse_deux_an")
    private Float analyse_deux_an;

    @Column(name = "acte_taux")
    private Float acte_taux;

    @Column(name = "acte_frais")
    private String acte_frais;

    @Column(name = "acte_limite")
    private String acte_limite;

    @Column(name = "acte_jour")
    private Float acte_jour;

    @Column(name = "acte_an")
    private Float acte_an;

    @Column(name = "acte_deux_an")
    private Float acte_deux_an;

    @Column(name = "principal_chambre_taux")
    private Float principal_chambre_taux;

    @Column(name = "principal_chambre_frais")
    private String principal_chambre_frais;

    @Column(name = "principal_chambre_limite")
    private String principal_chambre_limite;

    @Column(name = "principal_chambre_jour")
    private Float principal_chambre_jour;

    @Column(name = "principal_chambre_an")
    private Float principal_chambre_an;

    @Column(name = "principal_chambre_deux_an")
    private Float principal_chambre_deux_an;

    @Column(name = "principal_frais_taux")
    private Float principal_frais_taux;

    @Column(name = "principal_frais_limite")
    private String principal_frais_limite;

    @Column(name = "principal_frais_jour")
    private Float principal_frais_jour;

    @Column(name = "principal_frais_an")
    private Float principal_frais_an;

    @Column(name = "principal_frais_deux_an")
    private Float principal_frais_deux_an;

    @Column(name = "prive_chambre_taux")
    private Float prive_chambre_taux;

    @Column(name = "prive_chambre_frais")
    private String prive_chambre_frais;

    @Column(name = "prive_chambre_limite")
    private String prive_chambre_limite;

    @Column(name = "prive_chambre_jour")
    private Float prive_chambre_jour;

    @Column(name = "prive_chambre_an")
    private Float prive_chambre_an;

    @Column(name = "prive_chambre_deux_an")
    private Float prive_chambre_deux_an;

    @Column(name = "prive_frais_taux")
    private Float prive_frais_taux;

    @Column(name = "prive_frais_limite")
    private String prive_frais_limite;

    @Column(name = "prive_frais_jour")
    private Float prive_frais_jour;

    @Column(name = "prive_frais_an")
    private Float prive_frais_an;

    @Column(name = "prive_frais_deux_an")
    private Float prive_frais_deux_an;

    @Column(name = "public_chambre_taux")
    private Float public_chambre_taux;

    @Column(name = "public_chambre_frais")
    private String public_chambre_frais;

    @Column(name = "public_chambre_limite")
    private String public_chambre_limite;

    @Column(name = "public_chambre_jour")
    private Float public_chambre_jour;

    @Column(name = "public_chambre_an")
    private Float public_chambre_an;

    @Column(name = "public_chambre_deux_an")
    private Float public_chambre_deux_an;

    @Column(name = "public_frais_taux")
    private Float public_frais_taux;

    @Column(name = "public_frais_frais")
    private String public_frais_frais;

    @Column(name = "public_frais_limite")
    private String public_frais_limite;

    @Column(name = "public_frais_jour")
    private Float public_frais_jour;

    @Column(name = "public_frais_an")
    private Float public_frais_an;

    @Column(name = "public_frais_deux_an")
    private Float public_frais_deux_an;

    @Column(name = "soins_taux")
    private Float soins_taux;

    @Column(name = "soins_frais")
    private String soins_frais;

    @Column(name = "soins_limite")
    private String soins_limite;

    @Column(name = "soins_jour")
    private Float soins_jour;

    @Column(name = "soins_an")
    private Float soins_an;

    @Column(name = "soins_deux_an")
    private Float soins_deux_an;

    @Column(name = "verre_taux")
    private Float verre_taux;

    @Column(name = "verre_frais")
    private String verre_frais;

    @Column(name = "verre_limite")
    private String verre_limite;

    @Column(name = "verre_jour")
    private Float verre_jour;

    @Column(name = "verre_an")
    private Float verre_an;

    @Column(name = "verre_deux_an")
    private Float verre_deux_an;

    @Column(name = "monture_taux")
    private Float monture_taux;

    @Column(name = "monture_frais")
    private String monture_frais;

    @Column(name = "monture_limite")
    private String monture_limite;

    @Column(name = "monture_jour")
    private Float monture_jour;

    @Column(name = "monture_an")
    private Float monture_an;

    @Column(name = "monture_deux_an")
    private Float monture_deux_an;

    @Column(name = "accouchement_taux")
    private Float accouchement_taux;

    @Column(name = "accouchement_frais")
    private String accouchement_frais;

    @Column(name = "accouchement_limite")
    private String accouchement_limite;

    @Column(name = "accouchement_jour")
    private Float accouchement_jour;

    @Column(name = "accouchement_an")
    private String accouchement_an;

    @Column(name = "accouchement_deux_an")
    private Float accouchement_deux_an;

    @Column(name = "education_taux")
    private Float education_taux;

    @Column(name = "education_frais")
    private String education_frais;

    @Column(name = "education_limite")
    private String education_limite;

    @Column(name = "education_jour")
    private Float education_jour;

    @Column(name = "education_an")
    private Float education_an;

    @Column(name = "education_deux_an")
    private Float education_deux_an;

    @Column(name = "sejour_taux")
    private Float sejour_taux;

    @Column(name = "sejour_frais")
    private String sejour_frais;

    @Column(name = "sejour_limite")
    private String sejour_limite;

    @Column(name = "sejour_jour")
    private Float sejour_jour;

    @Column(name = "sejour_an")
    private Float sejour_an;

    @Column(name = "sejour_deux_an")
    private Float sejour_deux_an;

    @Column(name = "nb_collab")
    private Integer nbCollab;

    @Column(name = "nb_conjoint")
    private Integer nbConjoint;

    @Column(name = "nb_enfant_p")
    private Integer nbEnfantP;

    @Column(name = "nb_enfant_g")
    private Integer nbEnfantG;

    @Column(name = "fin")
    private String fin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaison() {
        return raison;
    }

    public Police raison(String raison) {
        this.raison = raison;
        return this;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getAdresse() {
        return adresse;
    }

    public Police adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public Police tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public Police email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterloc() {
        return interloc;
    }

    public Police interloc(String interloc) {
        this.interloc = interloc;
        return this;
    }

    public void setInterloc(String interloc) {
        this.interloc = interloc;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Police dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Police dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getTiers() {
        return tiers;
    }

    public Police tiers(String tiers) {
        this.tiers = tiers;
        return this;
    }

    public void setTiers(String tiers) {
        this.tiers = tiers;
    }

    public Double getTpx() {
        return tpx;
    }

    public Police tpx(Double tpx) {
        this.tpx = tpx;
        return this;
    }

    public void setTpx(Double tpx) {
        this.tpx = tpx;
    }

    public String getActivite() {
        return activite;
    }

    public Police activite(String activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getFormule_soins() {
        return formule_soins;
    }

    public Police formule_soins(String formule_soins) {
        this.formule_soins = formule_soins;
        return this;
    }

    public void setFormule_soins(String formule_soins) {
        this.formule_soins = formule_soins;
    }

    public String getTerritoire() {
        return territoire;
    }

    public Police territoire(String territoire) {
        this.territoire = territoire;
        return this;
    }

    public void setTerritoire(String territoire) {
        this.territoire = territoire;
    }

    public Integer getPlafond() {
        return plafond;
    }

    public Police plafond(Integer plafond) {
        this.plafond = plafond;
        return this;
    }

    public void setPlafond(Integer plafond) {
        this.plafond = plafond;
    }

    public String getComplement() {
        return complement;
    }

    public Police complement(String complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Float getConsultation_taux() {
        return consultation_taux;
    }

    public Police consultation_taux(Float consultation_taux) {
        this.consultation_taux = consultation_taux;
        return this;
    }

    public void setConsultation_taux(Float consultation_taux) {
        this.consultation_taux = consultation_taux;
    }

    public String getConsultation_frais() {
        return consultation_frais;
    }

    public Police consultation_frais(String consultation_frais) {
        this.consultation_frais = consultation_frais;
        return this;
    }

    public void setConsultation_frais(String consultation_frais) {
        this.consultation_frais = consultation_frais;
    }

    public String getConsultation_limite() {
        return consultation_limite;
    }

    public Police consultation_limite(String consultation_limite) {
        this.consultation_limite = consultation_limite;
        return this;
    }

    public void setConsultation_limite(String consultation_limite) {
        this.consultation_limite = consultation_limite;
    }

    public Float getConsultation_jour() {
        return consultation_jour;
    }

    public Police consultation_jour(Float consultation_jour) {
        this.consultation_jour = consultation_jour;
        return this;
    }

    public void setConsultation_jour(Float consultation_jour) {
        this.consultation_jour = consultation_jour;
    }

    public Float getConsultation_an() {
        return consultation_an;
    }

    public Police consultation_an(Float consultation_an) {
        this.consultation_an = consultation_an;
        return this;
    }

    public void setConsultation_an(Float consultation_an) {
        this.consultation_an = consultation_an;
    }

    public Float getConsultation_deux_an() {
        return consultation_deux_an;
    }

    public Police consultation_deux_an(Float consultation_deux_an) {
        this.consultation_deux_an = consultation_deux_an;
        return this;
    }

    public void setConsultation_deux_an(Float consultation_deux_an) {
        this.consultation_deux_an = consultation_deux_an;
    }

    public Float getPharmacie_taux() {
        return pharmacie_taux;
    }

    public Police pharmacie_taux(Float pharmacie_taux) {
        this.pharmacie_taux = pharmacie_taux;
        return this;
    }

    public void setPharmacie_taux(Float pharmacie_taux) {
        this.pharmacie_taux = pharmacie_taux;
    }

    public String getPharmacie_frais() {
        return pharmacie_frais;
    }

    public Police pharmacie_frais(String pharmacie_frais) {
        this.pharmacie_frais = pharmacie_frais;
        return this;
    }

    public void setPharmacie_frais(String pharmacie_frais) {
        this.pharmacie_frais = pharmacie_frais;
    }

    public String getPharmacie_limite() {
        return pharmacie_limite;
    }

    public Police pharmacie_limite(String pharmacie_limite) {
        this.pharmacie_limite = pharmacie_limite;
        return this;
    }

    public void setPharmacie_limite(String pharmacie_limite) {
        this.pharmacie_limite = pharmacie_limite;
    }

    public Float getPharmacie_jour() {
        return pharmacie_jour;
    }

    public Police pharmacie_jour(Float pharmacie_jour) {
        this.pharmacie_jour = pharmacie_jour;
        return this;
    }

    public void setPharmacie_jour(Float pharmacie_jour) {
        this.pharmacie_jour = pharmacie_jour;
    }

    public Float getPharmacie_an() {
        return pharmacie_an;
    }

    public Police pharmacie_an(Float pharmacie_an) {
        this.pharmacie_an = pharmacie_an;
        return this;
    }

    public void setPharmacie_an(Float pharmacie_an) {
        this.pharmacie_an = pharmacie_an;
    }

    public Float getPharmacie_deux_an() {
        return pharmacie_deux_an;
    }

    public Police pharmacie_deux_an(Float pharmacie_deux_an) {
        this.pharmacie_deux_an = pharmacie_deux_an;
        return this;
    }

    public void setPharmacie_deux_an(Float pharmacie_deux_an) {
        this.pharmacie_deux_an = pharmacie_deux_an;
    }

    public Float getAnalyse_taux() {
        return analyse_taux;
    }

    public Police analyse_taux(Float analyse_taux) {
        this.analyse_taux = analyse_taux;
        return this;
    }

    public void setAnalyse_taux(Float analyse_taux) {
        this.analyse_taux = analyse_taux;
    }

    public String getAnalyse_frais() {
        return analyse_frais;
    }

    public Police analyse_frais(String analyse_frais) {
        this.analyse_frais = analyse_frais;
        return this;
    }

    public void setAnalyse_frais(String analyse_frais) {
        this.analyse_frais = analyse_frais;
    }

    public String getAnalyse_limite() {
        return analyse_limite;
    }

    public Police analyse_limite(String analyse_limite) {
        this.analyse_limite = analyse_limite;
        return this;
    }

    public void setAnalyse_limite(String analyse_limite) {
        this.analyse_limite = analyse_limite;
    }

    public Float getAnalyse_jour() {
        return analyse_jour;
    }

    public Police analyse_jour(Float analyse_jour) {
        this.analyse_jour = analyse_jour;
        return this;
    }

    public void setAnalyse_jour(Float analyse_jour) {
        this.analyse_jour = analyse_jour;
    }

    public Float getAnalyse_an() {
        return analyse_an;
    }

    public Police analyse_an(Float analyse_an) {
        this.analyse_an = analyse_an;
        return this;
    }

    public void setAnalyse_an(Float analyse_an) {
        this.analyse_an = analyse_an;
    }

    public Float getAnalyse_deux_an() {
        return analyse_deux_an;
    }

    public Police analyse_deux_an(Float analyse_deux_an) {
        this.analyse_deux_an = analyse_deux_an;
        return this;
    }

    public void setAnalyse_deux_an(Float analyse_deux_an) {
        this.analyse_deux_an = analyse_deux_an;
    }

    public Float getActe_taux() {
        return acte_taux;
    }

    public Police acte_taux(Float acte_taux) {
        this.acte_taux = acte_taux;
        return this;
    }

    public void setActe_taux(Float acte_taux) {
        this.acte_taux = acte_taux;
    }

    public String getActe_frais() {
        return acte_frais;
    }

    public Police acte_frais(String acte_frais) {
        this.acte_frais = acte_frais;
        return this;
    }

    public void setActe_frais(String acte_frais) {
        this.acte_frais = acte_frais;
    }

    public String getActe_limite() {
        return acte_limite;
    }

    public Police acte_limite(String acte_limite) {
        this.acte_limite = acte_limite;
        return this;
    }

    public void setActe_limite(String acte_limite) {
        this.acte_limite = acte_limite;
    }

    public Float getActe_jour() {
        return acte_jour;
    }

    public Police acte_jour(Float acte_jour) {
        this.acte_jour = acte_jour;
        return this;
    }

    public void setActe_jour(Float acte_jour) {
        this.acte_jour = acte_jour;
    }

    public Float getActe_an() {
        return acte_an;
    }

    public Police acte_an(Float acte_an) {
        this.acte_an = acte_an;
        return this;
    }

    public void setActe_an(Float acte_an) {
        this.acte_an = acte_an;
    }

    public Float getActe_deux_an() {
        return acte_deux_an;
    }

    public Police acte_deux_an(Float acte_deux_an) {
        this.acte_deux_an = acte_deux_an;
        return this;
    }

    public void setActe_deux_an(Float acte_deux_an) {
        this.acte_deux_an = acte_deux_an;
    }

    public Float getPrincipal_chambre_taux() {
        return principal_chambre_taux;
    }

    public Police principal_chambre_taux(Float principal_chambre_taux) {
        this.principal_chambre_taux = principal_chambre_taux;
        return this;
    }

    public void setPrincipal_chambre_taux(Float principal_chambre_taux) {
        this.principal_chambre_taux = principal_chambre_taux;
    }

    public String getPrincipal_chambre_frais() {
        return principal_chambre_frais;
    }

    public Police principal_chambre_frais(String principal_chambre_frais) {
        this.principal_chambre_frais = principal_chambre_frais;
        return this;
    }

    public void setPrincipal_chambre_frais(String principal_chambre_frais) {
        this.principal_chambre_frais = principal_chambre_frais;
    }

    public String getPrincipal_chambre_limite() {
        return principal_chambre_limite;
    }

    public Police principal_chambre_limite(String principal_chambre_limite) {
        this.principal_chambre_limite = principal_chambre_limite;
        return this;
    }

    public void setPrincipal_chambre_limite(String principal_chambre_limite) {
        this.principal_chambre_limite = principal_chambre_limite;
    }

    public Float getPrincipal_chambre_jour() {
        return principal_chambre_jour;
    }

    public Police principal_chambre_jour(Float principal_chambre_jour) {
        this.principal_chambre_jour = principal_chambre_jour;
        return this;
    }

    public void setPrincipal_chambre_jour(Float principal_chambre_jour) {
        this.principal_chambre_jour = principal_chambre_jour;
    }

    public Float getPrincipal_chambre_an() {
        return principal_chambre_an;
    }

    public Police principal_chambre_an(Float principal_chambre_an) {
        this.principal_chambre_an = principal_chambre_an;
        return this;
    }

    public void setPrincipal_chambre_an(Float principal_chambre_an) {
        this.principal_chambre_an = principal_chambre_an;
    }

    public Float getPrincipal_chambre_deux_an() {
        return principal_chambre_deux_an;
    }

    public Police principal_chambre_deux_an(Float principal_chambre_deux_an) {
        this.principal_chambre_deux_an = principal_chambre_deux_an;
        return this;
    }

    public void setPrincipal_chambre_deux_an(Float principal_chambre_deux_an) {
        this.principal_chambre_deux_an = principal_chambre_deux_an;
    }

    public Float getPrincipal_frais_taux() {
        return principal_frais_taux;
    }

    public Police principal_frais_taux(Float principal_frais_taux) {
        this.principal_frais_taux = principal_frais_taux;
        return this;
    }

    public void setPrincipal_frais_taux(Float principal_frais_taux) {
        this.principal_frais_taux = principal_frais_taux;
    }

    public String getPrincipal_frais_limite() {
        return principal_frais_limite;
    }

    public Police principal_frais_limite(String principal_frais_limite) {
        this.principal_frais_limite = principal_frais_limite;
        return this;
    }

    public void setPrincipal_frais_limite(String principal_frais_limite) {
        this.principal_frais_limite = principal_frais_limite;
    }

    public Float getPrincipal_frais_jour() {
        return principal_frais_jour;
    }

    public Police principal_frais_jour(Float principal_frais_jour) {
        this.principal_frais_jour = principal_frais_jour;
        return this;
    }

    public void setPrincipal_frais_jour(Float principal_frais_jour) {
        this.principal_frais_jour = principal_frais_jour;
    }

    public Float getPrincipal_frais_an() {
        return principal_frais_an;
    }

    public Police principal_frais_an(Float principal_frais_an) {
        this.principal_frais_an = principal_frais_an;
        return this;
    }

    public void setPrincipal_frais_an(Float principal_frais_an) {
        this.principal_frais_an = principal_frais_an;
    }

    public Float getPrincipal_frais_deux_an() {
        return principal_frais_deux_an;
    }

    public Police principal_frais_deux_an(Float principal_frais_deux_an) {
        this.principal_frais_deux_an = principal_frais_deux_an;
        return this;
    }

    public void setPrincipal_frais_deux_an(Float principal_frais_deux_an) {
        this.principal_frais_deux_an = principal_frais_deux_an;
    }

    public Float getPrive_chambre_taux() {
        return prive_chambre_taux;
    }

    public Police prive_chambre_taux(Float prive_chambre_taux) {
        this.prive_chambre_taux = prive_chambre_taux;
        return this;
    }

    public void setPrive_chambre_taux(Float prive_chambre_taux) {
        this.prive_chambre_taux = prive_chambre_taux;
    }

    public String getPrive_chambre_frais() {
        return prive_chambre_frais;
    }

    public Police prive_chambre_frais(String prive_chambre_frais) {
        this.prive_chambre_frais = prive_chambre_frais;
        return this;
    }

    public void setPrive_chambre_frais(String prive_chambre_frais) {
        this.prive_chambre_frais = prive_chambre_frais;
    }

    public String getPrive_chambre_limite() {
        return prive_chambre_limite;
    }

    public Police prive_chambre_limite(String prive_chambre_limite) {
        this.prive_chambre_limite = prive_chambre_limite;
        return this;
    }

    public void setPrive_chambre_limite(String prive_chambre_limite) {
        this.prive_chambre_limite = prive_chambre_limite;
    }

    public Float getPrive_chambre_jour() {
        return prive_chambre_jour;
    }

    public Police prive_chambre_jour(Float prive_chambre_jour) {
        this.prive_chambre_jour = prive_chambre_jour;
        return this;
    }

    public void setPrive_chambre_jour(Float prive_chambre_jour) {
        this.prive_chambre_jour = prive_chambre_jour;
    }

    public Float getPrive_chambre_an() {
        return prive_chambre_an;
    }

    public Police prive_chambre_an(Float prive_chambre_an) {
        this.prive_chambre_an = prive_chambre_an;
        return this;
    }

    public void setPrive_chambre_an(Float prive_chambre_an) {
        this.prive_chambre_an = prive_chambre_an;
    }

    public Float getPrive_chambre_deux_an() {
        return prive_chambre_deux_an;
    }

    public Police prive_chambre_deux_an(Float prive_chambre_deux_an) {
        this.prive_chambre_deux_an = prive_chambre_deux_an;
        return this;
    }

    public void setPrive_chambre_deux_an(Float prive_chambre_deux_an) {
        this.prive_chambre_deux_an = prive_chambre_deux_an;
    }

    public Float getPrive_frais_taux() {
        return prive_frais_taux;
    }

    public Police prive_frais_taux(Float prive_frais_taux) {
        this.prive_frais_taux = prive_frais_taux;
        return this;
    }

    public void setPrive_frais_taux(Float prive_frais_taux) {
        this.prive_frais_taux = prive_frais_taux;
    }

    public String getPrive_frais_limite() {
        return prive_frais_limite;
    }

    public Police prive_frais_limite(String prive_frais_limite) {
        this.prive_frais_limite = prive_frais_limite;
        return this;
    }

    public void setPrive_frais_limite(String prive_frais_limite) {
        this.prive_frais_limite = prive_frais_limite;
    }

    public Float getPrive_frais_jour() {
        return prive_frais_jour;
    }

    public Police prive_frais_jour(Float prive_frais_jour) {
        this.prive_frais_jour = prive_frais_jour;
        return this;
    }

    public void setPrive_frais_jour(Float prive_frais_jour) {
        this.prive_frais_jour = prive_frais_jour;
    }

    public Float getPrive_frais_an() {
        return prive_frais_an;
    }

    public Police prive_frais_an(Float prive_frais_an) {
        this.prive_frais_an = prive_frais_an;
        return this;
    }

    public void setPrive_frais_an(Float prive_frais_an) {
        this.prive_frais_an = prive_frais_an;
    }

    public Float getPrive_frais_deux_an() {
        return prive_frais_deux_an;
    }

    public Police prive_frais_deux_an(Float prive_frais_deux_an) {
        this.prive_frais_deux_an = prive_frais_deux_an;
        return this;
    }

    public void setPrive_frais_deux_an(Float prive_frais_deux_an) {
        this.prive_frais_deux_an = prive_frais_deux_an;
    }

    public Float getPublic_chambre_taux() {
        return public_chambre_taux;
    }

    public Police public_chambre_taux(Float public_chambre_taux) {
        this.public_chambre_taux = public_chambre_taux;
        return this;
    }

    public void setPublic_chambre_taux(Float public_chambre_taux) {
        this.public_chambre_taux = public_chambre_taux;
    }

    public String getPublic_chambre_frais() {
        return public_chambre_frais;
    }

    public Police public_chambre_frais(String public_chambre_frais) {
        this.public_chambre_frais = public_chambre_frais;
        return this;
    }

    public void setPublic_chambre_frais(String public_chambre_frais) {
        this.public_chambre_frais = public_chambre_frais;
    }

    public String getPublic_chambre_limite() {
        return public_chambre_limite;
    }

    public Police public_chambre_limite(String public_chambre_limite) {
        this.public_chambre_limite = public_chambre_limite;
        return this;
    }

    public void setPublic_chambre_limite(String public_chambre_limite) {
        this.public_chambre_limite = public_chambre_limite;
    }

    public Float getPublic_chambre_jour() {
        return public_chambre_jour;
    }

    public Police public_chambre_jour(Float public_chambre_jour) {
        this.public_chambre_jour = public_chambre_jour;
        return this;
    }

    public void setPublic_chambre_jour(Float public_chambre_jour) {
        this.public_chambre_jour = public_chambre_jour;
    }

    public Float getPublic_chambre_an() {
        return public_chambre_an;
    }

    public Police public_chambre_an(Float public_chambre_an) {
        this.public_chambre_an = public_chambre_an;
        return this;
    }

    public void setPublic_chambre_an(Float public_chambre_an) {
        this.public_chambre_an = public_chambre_an;
    }

    public Float getPublic_chambre_deux_an() {
        return public_chambre_deux_an;
    }

    public Police public_chambre_deux_an(Float public_chambre_deux_an) {
        this.public_chambre_deux_an = public_chambre_deux_an;
        return this;
    }

    public void setPublic_chambre_deux_an(Float public_chambre_deux_an) {
        this.public_chambre_deux_an = public_chambre_deux_an;
    }

    public Float getPublic_frais_taux() {
        return public_frais_taux;
    }

    public Police public_frais_taux(Float public_frais_taux) {
        this.public_frais_taux = public_frais_taux;
        return this;
    }

    public void setPublic_frais_taux(Float public_frais_taux) {
        this.public_frais_taux = public_frais_taux;
    }

    public String getPublic_frais_frais() {
        return public_frais_frais;
    }

    public Police public_frais_frais(String public_frais_frais) {
        this.public_frais_frais = public_frais_frais;
        return this;
    }

    public void setPublic_frais_frais(String public_frais_frais) {
        this.public_frais_frais = public_frais_frais;
    }

    public String getPublic_frais_limite() {
        return public_frais_limite;
    }

    public Police public_frais_limite(String public_frais_limite) {
        this.public_frais_limite = public_frais_limite;
        return this;
    }

    public void setPublic_frais_limite(String public_frais_limite) {
        this.public_frais_limite = public_frais_limite;
    }

    public Float getPublic_frais_jour() {
        return public_frais_jour;
    }

    public Police public_frais_jour(Float public_frais_jour) {
        this.public_frais_jour = public_frais_jour;
        return this;
    }

    public void setPublic_frais_jour(Float public_frais_jour) {
        this.public_frais_jour = public_frais_jour;
    }

    public Float getPublic_frais_an() {
        return public_frais_an;
    }

    public Police public_frais_an(Float public_frais_an) {
        this.public_frais_an = public_frais_an;
        return this;
    }

    public void setPublic_frais_an(Float public_frais_an) {
        this.public_frais_an = public_frais_an;
    }

    public Float getPublic_frais_deux_an() {
        return public_frais_deux_an;
    }

    public Police public_frais_deux_an(Float public_frais_deux_an) {
        this.public_frais_deux_an = public_frais_deux_an;
        return this;
    }

    public void setPublic_frais_deux_an(Float public_frais_deux_an) {
        this.public_frais_deux_an = public_frais_deux_an;
    }

    public Float getSoins_taux() {
        return soins_taux;
    }

    public Police soins_taux(Float soins_taux) {
        this.soins_taux = soins_taux;
        return this;
    }

    public void setSoins_taux(Float soins_taux) {
        this.soins_taux = soins_taux;
    }

    public String getSoins_frais() {
        return soins_frais;
    }

    public Police soins_frais(String soins_frais) {
        this.soins_frais = soins_frais;
        return this;
    }

    public void setSoins_frais(String soins_frais) {
        this.soins_frais = soins_frais;
    }

    public String getSoins_limite() {
        return soins_limite;
    }

    public Police soins_limite(String soins_limite) {
        this.soins_limite = soins_limite;
        return this;
    }

    public void setSoins_limite(String soins_limite) {
        this.soins_limite = soins_limite;
    }

    public Float getSoins_jour() {
        return soins_jour;
    }

    public Police soins_jour(Float soins_jour) {
        this.soins_jour = soins_jour;
        return this;
    }

    public void setSoins_jour(Float soins_jour) {
        this.soins_jour = soins_jour;
    }

    public Float getSoins_an() {
        return soins_an;
    }

    public Police soins_an(Float soins_an) {
        this.soins_an = soins_an;
        return this;
    }

    public void setSoins_an(Float soins_an) {
        this.soins_an = soins_an;
    }

    public Float getSoins_deux_an() {
        return soins_deux_an;
    }

    public Police soins_deux_an(Float soins_deux_an) {
        this.soins_deux_an = soins_deux_an;
        return this;
    }

    public void setSoins_deux_an(Float soins_deux_an) {
        this.soins_deux_an = soins_deux_an;
    }

    public Float getVerre_taux() {
        return verre_taux;
    }

    public Police verre_taux(Float verre_taux) {
        this.verre_taux = verre_taux;
        return this;
    }

    public void setVerre_taux(Float verre_taux) {
        this.verre_taux = verre_taux;
    }

    public String getVerre_frais() {
        return verre_frais;
    }

    public Police verre_frais(String verre_frais) {
        this.verre_frais = verre_frais;
        return this;
    }

    public void setVerre_frais(String verre_frais) {
        this.verre_frais = verre_frais;
    }

    public String getVerre_limite() {
        return verre_limite;
    }

    public Police verre_limite(String verre_limite) {
        this.verre_limite = verre_limite;
        return this;
    }

    public void setVerre_limite(String verre_limite) {
        this.verre_limite = verre_limite;
    }

    public Float getVerre_jour() {
        return verre_jour;
    }

    public Police verre_jour(Float verre_jour) {
        this.verre_jour = verre_jour;
        return this;
    }

    public void setVerre_jour(Float verre_jour) {
        this.verre_jour = verre_jour;
    }

    public Float getVerre_an() {
        return verre_an;
    }

    public Police verre_an(Float verre_an) {
        this.verre_an = verre_an;
        return this;
    }

    public void setVerre_an(Float verre_an) {
        this.verre_an = verre_an;
    }

    public Float getVerre_deux_an() {
        return verre_deux_an;
    }

    public Police verre_deux_an(Float verre_deux_an) {
        this.verre_deux_an = verre_deux_an;
        return this;
    }

    public void setVerre_deux_an(Float verre_deux_an) {
        this.verre_deux_an = verre_deux_an;
    }

    public Float getMonture_taux() {
        return monture_taux;
    }

    public Police monture_taux(Float monture_taux) {
        this.monture_taux = monture_taux;
        return this;
    }

    public void setMonture_taux(Float monture_taux) {
        this.monture_taux = monture_taux;
    }

    public String getMonture_frais() {
        return monture_frais;
    }

    public Police monture_frais(String monture_frais) {
        this.monture_frais = monture_frais;
        return this;
    }

    public void setMonture_frais(String monture_frais) {
        this.monture_frais = monture_frais;
    }

    public String getMonture_limite() {
        return monture_limite;
    }

    public Police monture_limite(String monture_limite) {
        this.monture_limite = monture_limite;
        return this;
    }

    public void setMonture_limite(String monture_limite) {
        this.monture_limite = monture_limite;
    }

    public Float getMonture_jour() {
        return monture_jour;
    }

    public Police monture_jour(Float monture_jour) {
        this.monture_jour = monture_jour;
        return this;
    }

    public void setMonture_jour(Float monture_jour) {
        this.monture_jour = monture_jour;
    }

    public Float getMonture_an() {
        return monture_an;
    }

    public Police monture_an(Float monture_an) {
        this.monture_an = monture_an;
        return this;
    }

    public void setMonture_an(Float monture_an) {
        this.monture_an = monture_an;
    }

    public Float getMonture_deux_an() {
        return monture_deux_an;
    }

    public Police monture_deux_an(Float monture_deux_an) {
        this.monture_deux_an = monture_deux_an;
        return this;
    }

    public void setMonture_deux_an(Float monture_deux_an) {
        this.monture_deux_an = monture_deux_an;
    }

    public Float getAccouchement_taux() {
        return accouchement_taux;
    }

    public Police accouchement_taux(Float accouchement_taux) {
        this.accouchement_taux = accouchement_taux;
        return this;
    }

    public void setAccouchement_taux(Float accouchement_taux) {
        this.accouchement_taux = accouchement_taux;
    }

    public String getAccouchement_frais() {
        return accouchement_frais;
    }

    public Police accouchement_frais(String accouchement_frais) {
        this.accouchement_frais = accouchement_frais;
        return this;
    }

    public void setAccouchement_frais(String accouchement_frais) {
        this.accouchement_frais = accouchement_frais;
    }

    public String getAccouchement_limite() {
        return accouchement_limite;
    }

    public Police accouchement_limite(String accouchement_limite) {
        this.accouchement_limite = accouchement_limite;
        return this;
    }

    public void setAccouchement_limite(String accouchement_limite) {
        this.accouchement_limite = accouchement_limite;
    }

    public Float getAccouchement_jour() {
        return accouchement_jour;
    }

    public Police accouchement_jour(Float accouchement_jour) {
        this.accouchement_jour = accouchement_jour;
        return this;
    }

    public void setAccouchement_jour(Float accouchement_jour) {
        this.accouchement_jour = accouchement_jour;
    }

    public String getAccouchement_an() {
        return accouchement_an;
    }

    public Police accouchement_an(String accouchement_an) {
        this.accouchement_an = accouchement_an;
        return this;
    }

    public void setAccouchement_an(String accouchement_an) {
        this.accouchement_an = accouchement_an;
    }

    public Float getAccouchement_deux_an() {
        return accouchement_deux_an;
    }

    public Police accouchement_deux_an(Float accouchement_deux_an) {
        this.accouchement_deux_an = accouchement_deux_an;
        return this;
    }

    public void setAccouchement_deux_an(Float accouchement_deux_an) {
        this.accouchement_deux_an = accouchement_deux_an;
    }

    public Float getEducation_taux() {
        return education_taux;
    }

    public Police education_taux(Float education_taux) {
        this.education_taux = education_taux;
        return this;
    }

    public void setEducation_taux(Float education_taux) {
        this.education_taux = education_taux;
    }

    public String getEducation_frais() {
        return education_frais;
    }

    public Police education_frais(String education_frais) {
        this.education_frais = education_frais;
        return this;
    }

    public void setEducation_frais(String education_frais) {
        this.education_frais = education_frais;
    }

    public String getEducation_limite() {
        return education_limite;
    }

    public Police education_limite(String education_limite) {
        this.education_limite = education_limite;
        return this;
    }

    public void setEducation_limite(String education_limite) {
        this.education_limite = education_limite;
    }

    public Float getEducation_jour() {
        return education_jour;
    }

    public Police education_jour(Float education_jour) {
        this.education_jour = education_jour;
        return this;
    }

    public void setEducation_jour(Float education_jour) {
        this.education_jour = education_jour;
    }

    public Float getEducation_an() {
        return education_an;
    }

    public Police education_an(Float education_an) {
        this.education_an = education_an;
        return this;
    }

    public void setEducation_an(Float education_an) {
        this.education_an = education_an;
    }

    public Float getEducation_deux_an() {
        return education_deux_an;
    }

    public Police education_deux_an(Float education_deux_an) {
        this.education_deux_an = education_deux_an;
        return this;
    }

    public void setEducation_deux_an(Float education_deux_an) {
        this.education_deux_an = education_deux_an;
    }

    public Float getSejour_taux() {
        return sejour_taux;
    }

    public Police sejour_taux(Float sejour_taux) {
        this.sejour_taux = sejour_taux;
        return this;
    }

    public void setSejour_taux(Float sejour_taux) {
        this.sejour_taux = sejour_taux;
    }

    public String getSejour_frais() {
        return sejour_frais;
    }

    public Police sejour_frais(String sejour_frais) {
        this.sejour_frais = sejour_frais;
        return this;
    }

    public void setSejour_frais(String sejour_frais) {
        this.sejour_frais = sejour_frais;
    }

    public String getSejour_limite() {
        return sejour_limite;
    }

    public Police sejour_limite(String sejour_limite) {
        this.sejour_limite = sejour_limite;
        return this;
    }

    public void setSejour_limite(String sejour_limite) {
        this.sejour_limite = sejour_limite;
    }

    public Float getSejour_jour() {
        return sejour_jour;
    }

    public Police sejour_jour(Float sejour_jour) {
        this.sejour_jour = sejour_jour;
        return this;
    }

    public void setSejour_jour(Float sejour_jour) {
        this.sejour_jour = sejour_jour;
    }

    public Float getSejour_an() {
        return sejour_an;
    }

    public Police sejour_an(Float sejour_an) {
        this.sejour_an = sejour_an;
        return this;
    }

    public void setSejour_an(Float sejour_an) {
        this.sejour_an = sejour_an;
    }

    public Float getSejour_deux_an() {
        return sejour_deux_an;
    }

    public Police sejour_deux_an(Float sejour_deux_an) {
        this.sejour_deux_an = sejour_deux_an;
        return this;
    }

    public void setSejour_deux_an(Float sejour_deux_an) {
        this.sejour_deux_an = sejour_deux_an;
    }

    public Integer getNbCollab() {
        return nbCollab;
    }

    public Police nbCollab(Integer nbCollab) {
        this.nbCollab = nbCollab;
        return this;
    }

    public void setNbCollab(Integer nbCollab) {
        this.nbCollab = nbCollab;
    }

    public Integer getNbConjoint() {
        return nbConjoint;
    }

    public Police nbConjoint(Integer nbConjoint) {
        this.nbConjoint = nbConjoint;
        return this;
    }

    public void setNbConjoint(Integer nbConjoint) {
        this.nbConjoint = nbConjoint;
    }

    public Integer getNbEnfantP() {
        return nbEnfantP;
    }

    public Police nbEnfantP(Integer nbEnfantP) {
        this.nbEnfantP = nbEnfantP;
        return this;
    }

    public void setNbEnfantP(Integer nbEnfantP) {
        this.nbEnfantP = nbEnfantP;
    }

    public Integer getNbEnfantG() {
        return nbEnfantG;
    }

    public Police nbEnfantG(Integer nbEnfantG) {
        this.nbEnfantG = nbEnfantG;
        return this;
    }

    public void setNbEnfantG(Integer nbEnfantG) {
        this.nbEnfantG = nbEnfantG;
    }

    public String getFin() {
        return fin;
    }

    public Police fin(String fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Police police = (Police) o;
        if(police.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, police.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Police{" +
            "id=" + id +
            ", raison='" + raison + "'" +
            ", adresse='" + adresse + "'" +
            ", tel='" + tel + "'" +
            ", email='" + email + "'" +
            ", interloc='" + interloc + "'" +
            ", dateDebut='" + dateDebut + "'" +
            ", dateFin='" + dateFin + "'" +
            ", tiers='" + tiers + "'" +
            ", tpx='" + tpx + "'" +
            ", activite='" + activite + "'" +
            ", formule_soins='" + formule_soins + "'" +
            ", territoire='" + territoire + "'" +
            ", plafond='" + plafond + "'" +
            ", complement='" + complement + "'" +
            ", consultation_taux='" + consultation_taux + "'" +
            ", consultation_frais='" + consultation_frais + "'" +
            ", consultation_limite='" + consultation_limite + "'" +
            ", consultation_jour='" + consultation_jour + "'" +
            ", consultation_an='" + consultation_an + "'" +
            ", consultation_deux_an='" + consultation_deux_an + "'" +
            ", pharmacie_taux='" + pharmacie_taux + "'" +
            ", pharmacie_frais='" + pharmacie_frais + "'" +
            ", pharmacie_limite='" + pharmacie_limite + "'" +
            ", pharmacie_jour='" + pharmacie_jour + "'" +
            ", pharmacie_an='" + pharmacie_an + "'" +
            ", pharmacie_deux_an='" + pharmacie_deux_an + "'" +
            ", analyse_taux='" + analyse_taux + "'" +
            ", analyse_frais='" + analyse_frais + "'" +
            ", analyse_limite='" + analyse_limite + "'" +
            ", analyse_jour='" + analyse_jour + "'" +
            ", analyse_an='" + analyse_an + "'" +
            ", analyse_deux_an='" + analyse_deux_an + "'" +
            ", acte_taux='" + acte_taux + "'" +
            ", acte_frais='" + acte_frais + "'" +
            ", acte_limite='" + acte_limite + "'" +
            ", acte_jour='" + acte_jour + "'" +
            ", acte_an='" + acte_an + "'" +
            ", acte_deux_an='" + acte_deux_an + "'" +
            ", principal_chambre_taux='" + principal_chambre_taux + "'" +
            ", principal_chambre_frais='" + principal_chambre_frais + "'" +
            ", principal_chambre_limite='" + principal_chambre_limite + "'" +
            ", principal_chambre_jour='" + principal_chambre_jour + "'" +
            ", principal_chambre_an='" + principal_chambre_an + "'" +
            ", principal_chambre_deux_an='" + principal_chambre_deux_an + "'" +
            ", principal_frais_taux='" + principal_frais_taux + "'" +
            ", principal_frais_limite='" + principal_frais_limite + "'" +
            ", principal_frais_jour='" + principal_frais_jour + "'" +
            ", principal_frais_an='" + principal_frais_an + "'" +
            ", principal_frais_deux_an='" + principal_frais_deux_an + "'" +
            ", prive_chambre_taux='" + prive_chambre_taux + "'" +
            ", prive_chambre_frais='" + prive_chambre_frais + "'" +
            ", prive_chambre_limite='" + prive_chambre_limite + "'" +
            ", prive_chambre_jour='" + prive_chambre_jour + "'" +
            ", prive_chambre_an='" + prive_chambre_an + "'" +
            ", prive_chambre_deux_an='" + prive_chambre_deux_an + "'" +
            ", prive_frais_taux='" + prive_frais_taux + "'" +
            ", prive_frais_limite='" + prive_frais_limite + "'" +
            ", prive_frais_jour='" + prive_frais_jour + "'" +
            ", prive_frais_an='" + prive_frais_an + "'" +
            ", prive_frais_deux_an='" + prive_frais_deux_an + "'" +
            ", public_chambre_taux='" + public_chambre_taux + "'" +
            ", public_chambre_frais='" + public_chambre_frais + "'" +
            ", public_chambre_limite='" + public_chambre_limite + "'" +
            ", public_chambre_jour='" + public_chambre_jour + "'" +
            ", public_chambre_an='" + public_chambre_an + "'" +
            ", public_chambre_deux_an='" + public_chambre_deux_an + "'" +
            ", public_frais_taux='" + public_frais_taux + "'" +
            ", public_frais_frais='" + public_frais_frais + "'" +
            ", public_frais_limite='" + public_frais_limite + "'" +
            ", public_frais_jour='" + public_frais_jour + "'" +
            ", public_frais_an='" + public_frais_an + "'" +
            ", public_frais_deux_an='" + public_frais_deux_an + "'" +
            ", soins_taux='" + soins_taux + "'" +
            ", soins_frais='" + soins_frais + "'" +
            ", soins_limite='" + soins_limite + "'" +
            ", soins_jour='" + soins_jour + "'" +
            ", soins_an='" + soins_an + "'" +
            ", soins_deux_an='" + soins_deux_an + "'" +
            ", verre_taux='" + verre_taux + "'" +
            ", verre_frais='" + verre_frais + "'" +
            ", verre_limite='" + verre_limite + "'" +
            ", verre_jour='" + verre_jour + "'" +
            ", verre_an='" + verre_an + "'" +
            ", verre_deux_an='" + verre_deux_an + "'" +
            ", monture_taux='" + monture_taux + "'" +
            ", monture_frais='" + monture_frais + "'" +
            ", monture_limite='" + monture_limite + "'" +
            ", monture_jour='" + monture_jour + "'" +
            ", monture_an='" + monture_an + "'" +
            ", monture_deux_an='" + monture_deux_an + "'" +
            ", accouchement_taux='" + accouchement_taux + "'" +
            ", accouchement_frais='" + accouchement_frais + "'" +
            ", accouchement_limite='" + accouchement_limite + "'" +
            ", accouchement_jour='" + accouchement_jour + "'" +
            ", accouchement_an='" + accouchement_an + "'" +
            ", accouchement_deux_an='" + accouchement_deux_an + "'" +
            ", education_taux='" + education_taux + "'" +
            ", education_frais='" + education_frais + "'" +
            ", education_limite='" + education_limite + "'" +
            ", education_jour='" + education_jour + "'" +
            ", education_an='" + education_an + "'" +
            ", education_deux_an='" + education_deux_an + "'" +
            ", sejour_taux='" + sejour_taux + "'" +
            ", sejour_frais='" + sejour_frais + "'" +
            ", sejour_limite='" + sejour_limite + "'" +
            ", sejour_jour='" + sejour_jour + "'" +
            ", sejour_an='" + sejour_an + "'" +
            ", sejour_deux_an='" + sejour_deux_an + "'" +
            ", nbCollab='" + nbCollab + "'" +
            ", nbConjoint='" + nbConjoint + "'" +
            ", nbEnfantP='" + nbEnfantP + "'" +
            ", nbEnfantG='" + nbEnfantG + "'" +
            ", fin='" + fin + "'" +
            '}';
    }
}
