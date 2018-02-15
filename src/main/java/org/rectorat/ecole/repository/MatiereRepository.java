package org.rectorat.ecole.repository;

import org.rectorat.ecole.domain.Matiere;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Matiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    @Query("select distinct matiere from Matiere matiere left join fetch matiere.matiereProfs")
    List<Matiere> findAllWithEagerRelationships();

    @Query("select matiere from Matiere matiere left join fetch matiere.matiereProfs where matiere.id =:id")
    Matiere findOneWithEagerRelationships(@Param("id") Long id);

}
