package sn.seysoo.multiplex.repository;

import sn.seysoo.multiplex.domain.Groupe;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Groupe entity.
 */
@SuppressWarnings("unused")
public interface GroupeRepository extends JpaRepository<Groupe,Long> {

}
