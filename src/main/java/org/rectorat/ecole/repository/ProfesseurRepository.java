package org.rectorat.ecole.repository;

import org.rectorat.ecole.domain.Professeur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Professeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    @Query("select distinct professeur from Professeur professeur left join fetch professeur.classeEnseignes")
    List<Professeur> findAllWithEagerRelationships();

    @Query("select professeur from Professeur professeur left join fetch professeur.classeEnseignes where professeur.id =:id")
    Professeur findOneWithEagerRelationships(@Param("id") Long id);

}
