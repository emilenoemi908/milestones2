/**
 * Exce��o lan�ada quando ocorre um erro relacionado ao envio ou leitura de recados.
 */
public class InvalidRecadoException extends RuntimeException {

    /**
     * Constr�i uma nova RecadoException com a mensagem especificada.
     * @param message A mensagem detalhando a causa do erro.
     */
    public InvalidRecadoException(String message) {
        super(message);
    }
}