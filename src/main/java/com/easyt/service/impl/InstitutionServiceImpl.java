package com.easyt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.constant.PeriodEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.converter.ConverterHelper;
import com.easyt.entity.Institution;
import com.easyt.exception.ApplicationException;
import com.easyt.repository.InstitutionRepository;
import com.easyt.request.InstitutionRequest;
import com.easyt.response.InstitutionResponse;
import com.easyt.service.InstitutionService;
import com.easyt.util.ApplicationUtil;

@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {
	
	@Autowired
	private InstitutionRepository institutionRepository;
	
	public void validateParametersCreationOrEditionOfInstitution(InstitutionRequest institution) throws ApplicationException {
		if (institution == null ||
				institution.getPeriod() == null ||
				(institution.getName() == null || institution.getName().trim().isEmpty()) ||
				(institution.getAddress() == null || institution.getAddress().trim().isEmpty()) ||
				(institution.getState() == null || institution.getState().trim().isEmpty()) ||
				(institution.getCity() == null || institution.getCity().trim().isEmpty()) ||
				(institution.getLatitude() == null || institution.getLongitude() == null))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
	}
	
	public PeriodEnum selectPeriod(String period) throws ApplicationException {
		if (PeriodEnum.AFTERNOON.toString().equals(period))
			return PeriodEnum.AFTERNOON;
		if (PeriodEnum.EVENING.toString().equals(period))
			return PeriodEnum.EVENING;
		if (PeriodEnum.FULLTIME.toString().equals(period))
			return PeriodEnum.FULLTIME;
		if (PeriodEnum.MORNING.toString().equals(period))
			return PeriodEnum.MORNING;
		throw new ApplicationException(MessagesErroEnum.PERIOD_NOT_FOUND.getMessage());
	}
	
	public void checkChangeInAddressOrRemove(Long institutionId) throws ApplicationException {
		Integer count = institutionRepository.countByUsersWhichRelatesToAnInstitution(institutionId);
		if (count != null && count > 0)
			throw new ApplicationException(MessagesErroEnum.CONTAINS_USERS_LINKED_TO_AN_INSTITUTION.getMessage());
	}
	
	@Override
	public void deleteInstitution(Long institutionId) throws ApplicationException {
		if (institutionId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		checkChangeInAddressOrRemove(institutionId);
		Optional<Institution> institutionDB = institutionRepository
				.findOneByIdAndStatusNot(institutionId, StatusEnum.INACTIVE);
		if (institutionDB == null || !institutionDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.UPDATE_INSTITUTION_ERRO.getMessage());
		institutionDB.get().setEditionDate(Calendar.getInstance());
		institutionDB.get().setStatus(StatusEnum.INACTIVE);
		institutionRepository.save(institutionDB.get());
	}
	
	@Override
	public List<InstitutionResponse> listAllInstitution() throws ApplicationException {
		List<InstitutionResponse> institutions = new ArrayList<>();
		institutionRepository
		.findAllByStatusNotOrderByNameAsc(StatusEnum.INACTIVE)
		.forEach(institution->{
			InstitutionResponse objectInstitution = new InstitutionResponse();
			objectInstitution = ConverterHelper.convertInstitutionToResponse(institution);
			objectInstitution.setAssociated(institutionRepository.countByUsersWhichRelatesToAnInstitution(institution.getId()));
			institutions.add(objectInstitution);
		});
		return institutions;
	}
	
	@Override
	public void createInstitution(InstitutionRequest institution) throws ApplicationException {
		validateParametersCreationOrEditionOfInstitution(institution);
		Institution newInstitution = new Institution();
		newInstitution.setCreationDate(Calendar.getInstance());
		newInstitution.setName(institution.getName().trim());
		newInstitution.setAddress(institution.getAddress().trim());
		newInstitution.setState(institution.getState().trim());
		newInstitution.setCity(institution.getCity().trim());
		newInstitution.setPeriod(selectPeriod(institution.getPeriod()));
		if (institution.getTelephoneNumber() != null && !institution.getTelephoneNumber().trim().isEmpty())
			newInstitution.setTelephoneNumber(institution.getTelephoneNumber().trim());
		if (institution.getEmail() != null && !institution.getEmail().trim().isEmpty()) {
			if(!ApplicationUtil.isValidEmail(institution.getEmail().trim()))
				throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());
			newInstitution.setEmail(institution.getEmail().trim());
		}
		institutionRepository.save(newInstitution);
	}
	
	@Override
	public void updateInstitution(Long id, InstitutionRequest institution) throws ApplicationException {
		if (id == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		validateParametersCreationOrEditionOfInstitution(institution);
		Optional<Institution> institutionDB = institutionRepository
				.findOneByIdAndStatusNot(id, StatusEnum.INACTIVE);
		if (institutionDB == null || !institutionDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.UPDATE_INSTITUTION_ERRO.getMessage());
		// VERIFICA SE HÁ MUDANÇA DE ENDEREÇO E CASO HAJA VERIFICA SE HÁ USUÁRIOS VINCULADOS
		if ((institution.getAddress() != null && !institution.getAddress().trim().isEmpty()) &&
				(!institution.getAddress().trim().equals(institutionDB.get().getAddress()))) {
			checkChangeInAddressOrRemove(institutionDB.get().getId());
		} else if ((institution.getCity() != null && !institution.getCity().trim().isEmpty()) &&
				(!institution.getCity().trim().equals(institutionDB.get().getCity()))) {
			checkChangeInAddressOrRemove(institutionDB.get().getId());
		} else if ((institution.getState() != null && !institution.getState().trim().isEmpty()) &&
				(!institution.getState().trim().equals(institutionDB.get().getState()))) {
			checkChangeInAddressOrRemove(institutionDB.get().getId());
		} else if ((institution.getPeriod() != null && !institution.getPeriod().trim().isEmpty()) &&
				(!institution.getPeriod().trim().equals(institutionDB.get().getPeriod().toString()))) {
			checkChangeInAddressOrRemove(institutionDB.get().getId());
		}
		institutionDB.get().setEditionDate(Calendar.getInstance());
		institutionDB.get().setName(institution.getName());
		institutionDB.get().setAddress(institution.getAddress());
		institutionDB.get().setState(institution.getState());
		institutionDB.get().setCity(institution.getCity());
		institutionDB.get().setLatitude(institution.getLatitude());
		institutionDB.get().setLongitude(institution.getLongitude());
		institutionDB.get().setPeriod(selectPeriod(institution.getPeriod()));
		if (institution.getTelephoneNumber() != null && !institution.getTelephoneNumber().trim().isEmpty())
			institutionDB.get().setTelephoneNumber(institution.getTelephoneNumber().trim());
		if (institution.getEmail() != null && !institution.getEmail().trim().isEmpty()) {
			if(!ApplicationUtil.isValidEmail(institution.getEmail().trim()))
				throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());
			institutionDB.get().setEmail(institution.getEmail().trim());
		}
		institutionRepository.save(institutionDB.get());
	}

}
