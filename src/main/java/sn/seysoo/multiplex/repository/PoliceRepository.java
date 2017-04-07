package sn.seysoo.multiplex.repository;

import sn.seysoo.multiplex.domain.Police;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * Spring Data JPA repository for the Police entity.
 */
public interface PoliceRepository extends JpaRepository<Police,Long>, DataTablesRepository<Police,Long> {
	
	@Query("SELECT p from Police p WHERE concat(lower(p.raison), lower(p.adresse), lower(p.tel), lower(p.email),lower(p.interloc),lower(p.dateDebut),lower(p.dateFin)) like %?1% ORDER BY p.id DESC")
	Page<Police> search(Pageable page, String search, Specifications<Police> specifications);

}
