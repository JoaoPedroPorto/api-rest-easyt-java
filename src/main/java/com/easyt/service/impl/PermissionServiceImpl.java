package com.easyt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.constant.ProfileEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.converter.ConverterHelper;
import com.easyt.entity.Permission;
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.repository.PermissionRepository;
import com.easyt.repository.UserRepository;
import com.easyt.request.PermissionRequest;
import com.easyt.request.UserRequest;
import com.easyt.response.PermissionResponse;
import com.easyt.service.PermissionService;
import com.easyt.util.ApplicationUtil;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	public void deletePermission(Long permissionId) throws ApplicationException {
		if (permissionId == null)
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<Permission> permissionDB = permissionRepository
				.findOneByIdAndStatusNot(permissionId, StatusEnum.INACTIVE);
		if (permissionDB == null || !permissionDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.PERMISSION_NOT_FOUND_OR_INACTIVE.getMessage());
		if (permissionDB.get().getDescription().equals("TELA_PERMISSOES"))
			throw new ApplicationException(MessagesErroEnum.PERMISSION_CAN_NOT_BE_EXCLUDED.getMessage());
		permissionDB.get().setEditionDate(Calendar.getInstance());
		permissionDB.get().setStatus(StatusEnum.INACTIVE);
		permissionRepository.save(permissionDB.get());
	}
	
	@Override
	public List<String> listAllPermissionsByUser(String profile) throws ApplicationException {
		if (profile == null || profile.trim().isEmpty())
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		List<String> permissions = new ArrayList<>();
		if (profile.equals(ProfileEnum.GENERALMANAGER.toString())) {
			permissionRepository.findAllByManagerAndStatusNot(true, StatusEnum.INACTIVE)
			.forEach(permission->{
				permissions.add(permission.getDescription());
			});
		} else if (profile.equals(ProfileEnum.MODERATOR.toString())) {
			permissionRepository.findAllByModeratorAndStatusNot(true, StatusEnum.INACTIVE)
			.forEach(permission->{
				permissions.add(permission.getDescription());
			});			
		} else if (profile.equals(ProfileEnum.DRIVER.toString())) {
			permissionRepository.findAllByDriverAndStatusNot(true, StatusEnum.INACTIVE)
			.forEach(permission->{
				permissions.add(permission.getDescription());
			});			
		} else if (profile.equals(ProfileEnum.STUDENT.toString())) {
			permissionRepository.findAllByStudentAndStatusNot(true, StatusEnum.INACTIVE)
			.forEach(permission->{
				permissions.add(permission.getDescription());
			});			
		}
		return permissions;
	}
	
	@Override
	public List<PermissionResponse> listAllPermissions() throws ApplicationException {
		List<PermissionResponse> permissions = new ArrayList<>();
		permissionRepository
		.findAllByStatusNot(StatusEnum.INACTIVE)
		.forEach(permission->{
			permissions.add(ConverterHelper.convertPermissionToResponse(permission));
		});
		return permissions;
	}
	
	public String mountMessageErrorGeneralManager(PermissionRequest permission) {
		if (permission.getModerator()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Moderador.";		
		} else if (permission.getDriver()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Motorista.";
		} else if (permission.getStudent()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Aluno.";
		} else if (permission.getDescription().equals("TELA_PERMISSOES") && !permission.getManager()) {
			return "Não é possível remover a permissão: " + permission.getDescription() + " para o perfil: " + "Administrador.";
		}
		return null;
	}
	
	public String mountMessageErrorModerator(PermissionRequest permission) {
		if (permission.getManager()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Administrador.";		
		} else if (permission.getDriver()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Motorista.";
		} else if (permission.getStudent()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Aluno.";
		}
		return null;		
	}
	
	public String mountMessageErrorDriver(PermissionRequest permission) {
		if (permission.getManager()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Administrador.";		
		} else if (permission.getModerator()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Moderador.";
		} else if (permission.getStudent()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Aluno.";
		}
		return null;		
	}
	
	public String mountMessageErrorStudent(PermissionRequest permission) {
		if (permission.getManager()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Administrador.";		
		} else if (permission.getModerator()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Moderador.";
		} else if (permission.getDriver()) {
			return "Não é possível atribuir a permissão: " + permission.getDescription() + " para o perfil: " + "Motorista.";
		}
		return null;		
	}
	
	public void hasPermissionGeneralManagerToFurther(PermissionRequest permission) throws ApplicationException {
		List<String> list = new ArrayList<>();
		list.add("TELA_PERMISSOES");
		list.add("TELA_ADMINISTRADOR");
		list.add("TELA_MODERADOR");
		if ((list.contains(permission.getDescription())) &&
				(permission.getModerator() || permission.getDriver() || permission.getStudent()))
			throw new ApplicationException(mountMessageErrorGeneralManager(permission));
		if (permission.getDescription().equals("TELA_PERMISSOES") && !permission.getManager())
			throw new ApplicationException(mountMessageErrorGeneralManager(permission));
	}
	
	public void hasPermissionModeratorToFurther(PermissionRequest permission) throws ApplicationException {
		List<String> list = new ArrayList<>();
		list.add("TELA_INSTITUICAO");
		list.add("TELA_MOTORISTA");
		list.add("TELA_ALUNO");
		list.add("TELA_NOTIFICACAO");
		list.add("TELA_CHAT_MOTORISTA");
		list.add("TELA_CHAT_ALUNO");
		list.add("TELA_CHAT_MOTORISTA");
		list.add("TELA_SOLICITACOES");
		if ((list.contains(permission.getDescription())) &&
				(permission.getManager() || permission.getDriver() || permission.getStudent()))
			throw new ApplicationException(mountMessageErrorModerator(permission));
	}
	
	public void hasPermissionDriverToFurther(PermissionRequest permission) throws ApplicationException {
		List<String> list = new ArrayList<>();
		list.add("TELA_INICIAR_VIAGEM");
		list.add("TELA_PERIODOS_DE_VIAGENS");
		list.add("TELA_ATUALIZAR_VAGAS");
		list.add("TELA_ESCOLAS_MOTORISTA");
		list.add("TELA_REPUTAR_ALUNO");
		list.add("TELA_CALENDARIO_VIAGENS");
		list.add("TELA_NOTIFICACAO_MOTORISTA");
		list.add("TELA_DISPENSAR_ALUNO");
		list.add("TELA_ACEITAR_ALUNO");
		list.add("TELA_CHAT_MOTORISTA_APP");
		list.add("TELA_ACOMPANHAR_ROTA_TEMPO_REAL_MOTORISTA");
		list.add("TELA_ROTA");
		list.add("TELA_MOTORISTA_APP");
		list.add("TELA_VEICULO");
		if ((list.contains(permission.getDescription())) &&
				(permission.getManager() || permission.getModerator() || permission.getStudent()))
			throw new ApplicationException(mountMessageErrorDriver(permission));		
	}
	
	public void hasPermissionStudentToFurther(PermissionRequest permission) throws ApplicationException {
		List<String> list = new ArrayList<>();
		list.add("TELA_REPUTAR_MOTORISTA");
		list.add("TELA_AVISAR_AUSENCIA_PRESENCA");
		list.add("TELA_NOTIFICACAO_ALUNO");
		list.add("TELA_DISPENSAR_MOTORISTA");
		list.add("TELA_CHAT_ALUNO_APP");
		list.add("TELA_ACOMPANHAR_ROTA_TEMPO_REAL_ALUNO");
		list.add("TELA_ALUNO");
		list.add("TELA_PROCURAR_VEICULO");
		if ((list.contains(permission.getDescription())) &&
				(permission.getManager() || permission.getModerator() || permission.getStudent()))
			throw new ApplicationException(mountMessageErrorStudent(permission));
		
	}
	
	@Override
	public void updateAllPermissions(Long id, UserRequest request) throws ApplicationException {
		if (id == null ||
				(request.getPermissions() == null || request.getPermissions().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<User> managerDB = userRepository
				.findOneByIdAndProfileAndStatusNot(	id,
													ProfileEnum.GENERALMANAGER, 
													StatusEnum.INACTIVE);
		if (managerDB == null || !managerDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.USER_NOT_FOUND.getMessage());
		for (PermissionRequest permission : request.getPermissions()) {
			hasPermissionGeneralManagerToFurther(permission);
			hasPermissionModeratorToFurther(permission);
			hasPermissionDriverToFurther(permission);
			hasPermissionStudentToFurther(permission);
			Optional<Permission> permissionDB = permissionRepository
					.findOneByIdAndStatusNot(permission.getId(), StatusEnum.INACTIVE);
			if (permissionDB == null || !permissionDB.isPresent())
				throw new ApplicationException(MessagesErroEnum.PERMISSION_NOT_FOUND_OR_INACTIVE.getMessage());
			permissionDB.get().setEditionDate(Calendar.getInstance());
			permissionDB.get().setManager(permission.getManager());
			permissionDB.get().setModerator(permission.getModerator());
			permissionDB.get().setDriver(permission.getDriver());
			permissionDB.get().setStudent(permission.getStudent());
			permissionRepository.save(permissionDB.get());
		}
		Optional<Permission> keepPermission = permissionRepository
				.findOneByDescription("TELA_PERMISSOES");
		if (keepPermission == null || !keepPermission.isPresent()) {
			Permission newPermission = new Permission();
			newPermission.setCreationDate(Calendar.getInstance());
			newPermission.setStatus(StatusEnum.ACTIVE);
			newPermission.setDescription("TELA_PERMISSOES");
			newPermission.setManager(true);
			permissionRepository.save(newPermission);
			return;
		}
		keepPermission.get().setEditionDate(Calendar.getInstance());
		keepPermission.get().setManager(true);
		permissionRepository.save(keepPermission.get());
	}
	
	@Override
	public void createPermissions(List<PermissionRequest> permissions) throws ApplicationException {
		for (PermissionRequest permission : permissions) {
			if (permission.getDescription() == null || permission.getDescription().trim().isEmpty())
				continue;
			String text = ApplicationUtil.removeCaracterSpecial(permission.getDescription()).toUpperCase();
			text = text.replaceAll(" ", "_");
			Optional<Permission> permissionDB = permissionRepository
					.findOneByDescription(text);
			// EDITA UMA PERMISSÃO INATIVA NO SISTEMA
			if (permissionDB != null && 
					permissionDB.isPresent() && 
					permissionDB.get().getStatus().equals(StatusEnum.INACTIVE)) {
				permissionDB.get().setStatus(StatusEnum.ACTIVE);
				permissionDB.get().setEditionDate(Calendar.getInstance());
				permissionDB.get().setManager(false);
				permissionDB.get().setModerator(false);
				permissionDB.get().setDriver(false);
				permissionDB.get().setStudent(false);
				permissionRepository.save(permissionDB.get());
				continue;
			}
			// NÃO PERMITE EDITAR E MUITO MENOS CRIAR UMA PERMISSÃO JÁ EXISTENTE NO SISTEMA
			if (permissionDB != null && 
					permissionDB.isPresent() && 
					permissionDB.get().getStatus().equals(StatusEnum.ACTIVE))
				continue;
			// CRIA PERMISSÃO
			Permission newPermission = new Permission();
			newPermission.setCreationDate(Calendar.getInstance());
			newPermission.setStatus(StatusEnum.ACTIVE);
			newPermission.setDescription(text);
			permissionRepository.save(newPermission);		
		}
	}

}
