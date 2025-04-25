/**
 * Exce��o lan�ada quando um usu�rio inv�lido � encontrado no sistema.
 */
public class InvalidUserException extends RuntimeException {

    /**
     * Constr�i uma nova InvalidUserException com a mensagem especificada.
     * @param message A mensagem detalhando a causa do erro.
     */
    public InvalidUserException(String message) {
        super(message);
    }
}