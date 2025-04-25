/**
 * Exceção lançada quando um atributo inválido é acessado ou modificado no sistema.
 */
public class InvalidAtributeException extends RuntimeException {

    /**
     * Constrói uma nova InvalidAtributeException com a mensagem especificada.
     * @param mensagem A mensagem detalhando a causa do erro.
     */
    public InvalidAtributeException(String mensagem) {
        super(mensagem);
    }
}
