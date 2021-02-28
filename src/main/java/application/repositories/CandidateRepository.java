package application.repositories;

import application.models.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, UUID> {
    @Query( "SELECT c " +
            "FROM Candidate c " +
            "WHERE CONCAT( c.name, c.email ) LIKE %?1%" )
    Page<Candidate> findByName(String name, Pageable pageable);
    @Query( "SELECT c " +
            "FROM Candidate c " +
            "JOIN c.skills s " +
            "WHERE s.id IN :skills" )
    Page<Candidate> findBySkills(@Param("skills") List<UUID> skills, Pageable pageable);
}
