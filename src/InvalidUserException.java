/**
 * Exceção lançada quando um usuário inválido é encontrado no sistema.
 */
public class InvalidUserException extends RuntimeException {

    /**
     * Constrói uma nova InvalidUserException com a mensagem especificada.
     * @param message A mensagem detalhando a causa do erro.
     */
    public InvalidUserException(String message) {
        super(message);
    }
}