package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FicheGeolocalisatio.
 */

@Document(collection = "fiche_geolocalisatio")
public class FicheGeolocalisatio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("latitude")
    private Float latitude;

    @NotNull
    @Field("longitude")
    private Float longitude;

    @Field("precision")
    private Float precision;

    @Field("altitude")
    private Float altitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public FicheGeolocalisatio latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public FicheGeolocalisatio longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getPrecision() {
        return precision;
    }

    public FicheGeolocalisatio precision(Float precision) {
        this.precision = precision;
        return this;
    }

    public void setPrecision(Float precision) {
        this.precision = precision;
    }

    public Float getAltitude() {
        return altitude;
    }

    public FicheGeolocalisatio altitude(Float altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FicheGeolocalisatio ficheGeolocalisatio = (FicheGeolocalisatio) o;
        if (ficheGeolocalisatio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ficheGeolocalisatio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FicheGeolocalisatio{" +
            "id=" + id +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", precision='" + precision + "'" +
            ", altitude='" + altitude + "'" +
            '}';
    }
}
