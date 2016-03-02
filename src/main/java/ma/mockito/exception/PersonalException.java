package ma.mockito.exception;

public class PersonalException extends Exception {

	private static final long serialVersionUID = -5577105799646969374L;

	public PersonalException() {
	}

	@Override
	public String getMessage() {
		return "Exception levée ...";
	}

}
