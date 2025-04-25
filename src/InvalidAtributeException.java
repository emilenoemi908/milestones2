/**
 * Exce��o lan�ada quando um atributo inv�lido � acessado ou modificado no sistema.
 */
public class InvalidAtributeException extends RuntimeException {

    /**
     * Constr�i uma nova InvalidAtributeException com a mensagem especificada.
     * @param mensagem A mensagem detalhando a causa do erro.
     */
    public InvalidAtributeException(String mensagem) {
        super(mensagem);
    }
}
