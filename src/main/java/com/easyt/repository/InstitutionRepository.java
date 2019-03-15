package com.easyt.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easyt.constant.StatusEnum;
import com.easyt.entity.Institution;


public interface InstitutionRepository extends JpaRepository<Institution, Long> {
	
	Optional<Institution> findOneByIdAndStatusNot(Long id, StatusEnum status);
	Stream<Institution> findAllByStatusNotOrderByNameAsc(StatusEnum status);
	
	@Query(value = " SELECT COUNT(*) " + 
					 " FROM USER_INSTITUTION " + 
					" INNER JOIN USER ON USER_INSTITUTION.USER_INSTITUTION_FK_USER = USER.USER_ID " + 
					" INNER JOIN INSTITUTION ON USER_INSTITUTION.USER_INSTITUTION_FK_INSTITUTION = INSTITUTION.INSTITUTION_ID " + 
					" WHERE USER_INSTITUTION.USER_INSTITUTION_FK_INSTITUTION = :institutionId " + 
					  " AND USER.USER_STATUS <> 'INACTIVE' " + 
					  " AND INSTITUTION.INSTITUTION_STATUS <> 'INACTIVE'", nativeQuery = true)
	Integer countByUsersWhichRelatesToAnInstitution(@Param("institutionId") Long institutionId);
	
}
