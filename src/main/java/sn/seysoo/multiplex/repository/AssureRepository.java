package sn.seysoo.multiplex.repository;

import sn.seysoo.multiplex.domain.Assure;
import sn.seysoo.multiplex.domain.Police;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Assure entity.
 */
@SuppressWarnings("unused")
public interface AssureRepository extends JpaRepository<Assure,Long>, DataTablesRepository<Assure,Long>  {

}
