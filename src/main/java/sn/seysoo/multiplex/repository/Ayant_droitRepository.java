package sn.seysoo.multiplex.repository;

import sn.seysoo.multiplex.domain.Ayant_droit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ayant_droit entity.
 */
@SuppressWarnings("unused")
public interface Ayant_droitRepository extends JpaRepository<Ayant_droit,Long> {

}
