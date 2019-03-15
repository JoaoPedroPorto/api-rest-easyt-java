package com.easyt.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyt.constant.ProfileEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByEmailAndStatusNotAndProfileOrProfile(String email, StatusEnum status, ProfileEnum profile1,
			ProfileEnum profile2);

	Optional<User> findOneByFacebookIdAndStatusNotAndProfileOrProfile(String facebookId, StatusEnum status,
			ProfileEnum profile1, ProfileEnum profile2);

	Optional<User> findOneByGoogleIdAndStatusNotAndProfileOrProfile(String googleId, StatusEnum status,
			ProfileEnum profile1, ProfileEnum profile2);

	Optional<User> findOneByEmail(String email);

	Optional<User> findOneByCpfAndIdNot(String cpf, Long id);

	Optional<User> findOneByTelephoneNumberAndIdNot(String telephone, Long id);

	Optional<User> findOneByStatusNotAndEmailOrTelephoneNumber(StatusEnum status, String reqA, String reqB);

	Optional<User> findOneByIdAndStatusNot(Long id, StatusEnum status);

	Optional<User> findOneByIdAndProfileAndStatusNot(Long id, ProfileEnum profile, StatusEnum status);

	Stream<User> findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum status, ProfileEnum profile);

	Stream<User> findAllByStatusNotAndProfileOrProfileOrderByNameAsc(StatusEnum status, ProfileEnum profile1,
			ProfileEnum profile2);

}
