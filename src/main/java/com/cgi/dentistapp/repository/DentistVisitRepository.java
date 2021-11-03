package com.cgi.dentistapp.repository;

import com.cgi.dentistapp.entity.DentistVisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DentistVisitRepository extends JpaRepository<DentistVisitEntity, Long> {

    @Query("select dve from DentistVisitEntity dve where dve.visitTime > CURRENT_TIMESTAMP")
    List<DentistVisitEntity> findAllActive();

    @Query("select dve from DentistVisitEntity dve where dve.visitTime < CURRENT_TIMESTAMP")
    List<DentistVisitEntity> findAllPrevious();

    @Query("select dve from DentistVisitEntity dve where dve.visitTime > CURRENT_TIMESTAMP " +
            "and dve.dentist.id = :dentistId")
    List<DentistVisitEntity> findAllActiveByDentistId(@Param("dentistId") Long dentistId);

    @Query("select dve from DentistVisitEntity dve where dve.visitTime < CURRENT_TIMESTAMP " +
            "and dve.dentist.id = :dentistId")
    List<DentistVisitEntity> findAllPreviousByDentistId(@Param("dentistId") Long dentistId);

    @Query("select dve from DentistVisitEntity dve where (dve.visitTime >= :start and dve.visitTime <= :end) " +
            "and dve.visitTime > CURRENT_TIMESTAMP " +
            "and dve.dentist.id = :dentistId")
    List<DentistVisitEntity> findAllActiveBySearchParams(@Param("start") LocalDateTime start,
                                                         @Param("end") LocalDateTime end,
                                                         @Param("dentistId") Long dentistId);

    @Query("select dve from DentistVisitEntity dve where (dve.visitTime >= :start and dve.visitTime <= :end) " +
            "and dve.visitTime < CURRENT_TIMESTAMP " +
            "and dve.dentist.id = :dentistId")
    List<DentistVisitEntity> findAllPreviousBySearchParams(@Param("start") LocalDateTime start,
                                                         @Param("end") LocalDateTime end,
                                                         @Param("dentistId") Long dentistId);
}
