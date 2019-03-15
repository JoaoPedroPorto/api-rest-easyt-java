package com.easyt.repository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easyt.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	@Query(value = " SELECT * " + 
			 " FROM TOKEN " + 
		    " INNER JOIN USER ON TOKEN.TOKEN_FK_USER = USER.USER_ID " + 
		    " WHERE USER.USER_STATUS <> :status " + 
		      " AND TOKEN.TOKEN_DATE_OF_EXPIRATION >= :dateCurrent " + 
		      " AND TOKEN.TOKEN_VALUE = :value " + 
		      " AND (TOKEN.TOKEN_TYPE = :type1 " + 
		       " OR TOKEN.TOKEN_TYPE = :type2) ", nativeQuery = true)
	Optional<Token> findOneByUser_StatusNotAndDateOfExpirationAfterDateNowAndValueAndTypeOrType(@Param("value") String value,
																								@Param("type1") String type1,
																								@Param("type2") String type2,
																								@Param("status") String status,
																								@Param("dateCurrent") Date dateCurrent);

	@Query(value = " SELECT * " + 
			         " FROM TOKEN " + 
		            " INNER JOIN USER ON TOKEN.TOKEN_FK_USER = USER.USER_ID " + 
		            " WHERE USER.USER_STATUS <> :status " + 
		              " AND TOKEN.TOKEN_DATE_OF_EXPIRATION >= :dateCurrent " + 
		              " AND TOKEN.TOKEN_VALUE = :value " + 
		              " AND TOKEN.TOKEN_TYPE = :type ", nativeQuery = true)
	Optional<Token> findOneByUser_StatusNotAndDateOfExpirationAfterDateNowAndValueAndType(	@Param("value") String value,
																							@Param("type") String type,
																							@Param("status") String status,
																							@Param("dateCurrent") Date dateCurrent);
	
	Stream<Token> findByDateOfExpirationBefore(Date currentDate);
}
