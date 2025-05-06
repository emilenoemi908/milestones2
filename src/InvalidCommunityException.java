/**
 * Exceção lançada quando uma comunidade solicitada não é encontrada no repositório.
 */

public class InvalidCommunityException extends RuntimeException {

    /**
     * Construtor que permite definir uma mensagem personalizada para a exceção.
     * @param mensagem Mensagem detalhada sobre a exceção
     */
    public InvalidCommunityException(String mensagem) {
        super(mensagem);
    }
}