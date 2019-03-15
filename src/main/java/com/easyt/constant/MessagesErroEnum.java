package com.easyt.constant;

public enum MessagesErroEnum {
	
	// GENERAL MESSAGES
	PERIOD_NOT_FOUND("Período informado não é válido..."),
	ERROR_SEND_MAIL("Erro ao enviar e-mail..."),
	PARAMETER_EMPTY_OR_NULL("Existem parâmetros que não foram passados no corpo da requisição..."),
	ERRO_SOLICITATION("Ocorreu um erro ao realizar requisição..."),
	EMAIL_INVALID("E-mail inválido..."),
	EMAIL_BELONGS_TO_AN_ACTIVE_USER_IN_THE_SYSTEM("E-mail informado já é utilizado como um usuário do sistema..."),
	USER_NOT_FOUND("Usuário não existe, se encontra sem acesso ao sistema ou não possui o perfil desejado..."),
	EMAIL_HAS_A_DIFFERENT_PROFILE_FROM_THE_REQUESTED_PROFILE("E-mail informado possui um perfil diferente do que está realizando o cadastro..."),
	EMAIL_OR_TELEPHONE_NUMBER_NOT_FOUND_OR_WITHOUT_ACCESS("Não foi possível solicitar a troca de senha, pois não foi encontrado nenhum usuário com as informações fornecidas ou o usuário citado se encontra sem acesso ao sistema..."),
	REMEMBER_PASSWORD_SUCCESS("As intruções para a realização da troca de sua senha foram enviadas por e-mail..."),
	CHANGE_PASSWORD_SUCCESS("Senha alterada com sucesso..."),
	CHANGE_PASSWORD_ERROR("Não foi possível realizar a troca de sua senha, pois provavelmente sua senha se encontra sem estar criptografada. Peço que solicite 'Esqueci minha senha' para sanar o problema..."),
	PASSWORD_OUT_OF_THE_STANDARD("Senha deve conter no mínimo 6 dígitos e no máximo 12 dígitos..."),
	PASSWORDS_ARE_NOT_THE_SAME("Nova senha e confirmação de senha são incompatíveis..."),
	CPF_EXIST("CPF informado já é utilizado por um usuário do sistema..."),
	UPDATE_DATA_USER_SUCCESS("Informações atualizadas com sucesso..."),
	TEST_SEND_MAIL_SUCCESS("E-mail enviado com sucesso..."),
	
	// LOGIN
	USER_WITHOUT_PERMISSIONS_TO_PROFILE("Usuário não possui permissões vinculadas ao seu perfil...."),
	EMAIL_WITHOUT_ACCESS_PANEL("Usuário sem acesso ao sistema ou não possui acesso ao Painel Administrativo..."),
	EMAIL_WITHOUT_ACCESS_MOBILE("Usuário sem acesso ao sistema ou não possui acesso ao Aplicativo..."),
	EMAIL_OR_PASSWORD_INVALIDS("E-mail ou senha inválidos..."),
	PASSWORD_INVALID("Senha atual não consfere..."),
	LOGIN_SOCIAL_NETWORK("Realize o login através da rede social que realizou o seu cadastro ou solicite a troca de senha para cadastrar uma senha ao seu usuário..."),
	
	// DEVICE
	UPDATE_DEVICE_SUCCESS("Dispositivos atualizados com sucesso..."),
	
	// TOKEN
	CREATE_TOKEN_ERROR("Não foi possível autenticar usuário novamente..."),
	TOKEN_EXPIRED("Usuário se encontra sem acesso ao sistema ou token inválido ou se encontra expirado. Solicite novamente a redefinição de senha..."),
	TOKEN_VALID("Token válido, dados do usuário encontrado com sucesso..."),
	
	// MODERATOR
	CREATE_MODERATOR_SUCCESS("Moderador cadastrado com sucesso..."),
	UPDATE_MODERATOR_SUCCESS("Moderador atualizado com sucesso..."),
	DELETE_MODERATOR_SUCCESS("Moderador excluído com sucesso..."),
	
	// GENERAL MANAGER
	CREATE_GENERAL_MANAGER_SUCCESS("Administrador cadastrado com sucesso..."),
	UPDATE_GENERAL_MANAGER_SUCCESS("Administrador atualizado com sucesso..."),
	DELETE_GENERAL_MANAGER_SUCCESS("Administrador excluído com sucesso..."),
	
	// STUDENT
	DELETE_STUDENT_SUCCESS("Estudante excluído com sucesso..."),
	
	// DRIVER
	DELETE_DRIVER_SUCCESS("Motorista excluído com sucesso..."),
	
	// NOTIFICATION
	CREATE_NOTIFICATION_SUCCESS("Notificação cadastrada com sucesso..."),
	
	// INSTITUTION
	CREATE_INSTITUTION_SUCCESS("Instituição cadastrada com sucesso..."),
	UPDATE_INSTITUTION_SUCCESS("Instituição atualizada com sucesso..."),
	UPDATE_INSTITUTION_ERRO("Instituição não existe ou se encontra cancelada no sistema..."),
	DELETE_INSTITUTION_SUCCESS("Instituição excluída com sucesso..."),
	CONTAINS_USERS_LINKED_TO_AN_INSTITUTION("Não é possível atualizar endereço ou excluir Instituição, pois a mesma contém usuarios vinculados..."),
	
	// PERMISSION
	CREATE_PERMISSIONS_SUCCESS("Permissão(ões) cadastrada(s) com sucesso..."),
	UPDATE_PERMISSIONS_SUCCESS("Permissão(ões) atualizada(s) com sucesso..."),
	DELETE_PERMISSION_SUCCESS("Permissão excluída com sucesso..."),
	PERMISSION_CAN_NOT_BE_EXCLUDED("Essa permissão não pode ser removida do sistema, pois afetará a usabilidade do sistema... "),

	PERMISSION_NOT_FOUND_OR_INACTIVE("Não é possível excluir, pois permissão não existe ou se encontra inativa no sistema...");
	
	private String message;
	
	private MessagesErroEnum(String platform) {
		this.message = platform;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}