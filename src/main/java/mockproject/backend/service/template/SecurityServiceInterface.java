package mockproject.backend.service.template;

public interface SecurityServiceInterface {
	String findLoggedInUsername();

	void autoLogin(String username, String password);
}