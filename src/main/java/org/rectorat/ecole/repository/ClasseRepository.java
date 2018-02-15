package org.rectorat.ecole.repository;

import org.rectorat.ecole.domain.Classe;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Classe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    @Query("select distinct classe from Classe classe left join fetch classe.matiereEtudiers")
    List<Classe> findAllWithEagerRelationships();

    @Query("select classe from Classe classe left join fetch classe.matiereEtudiers where classe.id =:id")
    Classe findOneWithEagerRelationships(@Param("id") Long id);

}
