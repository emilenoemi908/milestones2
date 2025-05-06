import java.io.Serializable;

/**
 * Representa um par de nome e valor, que pode ser usado
 * para armazenar informações adicionais associadas aos usuários em um sistema.
 */

public class Atributo implements Serializable{

    private static final long serialVersionUID = 1L;

    /** Nome do atributo.*/
    private String nome;

    /** Valor do atributo.*/
    private String valor;

    /**
     * Construtor para criar um novo objeto Atributo com o nome e valor especificados.
     * @param nome  Nome do atributo.
     * @param valor Valor do atributo.
     */
    public Atributo(String nome,String valor){
        this.nome=nome;
        this.valor=valor;
    }

    /**
     * Retorna o nome do atributo.
     * @return o nome do atributo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do atributo.
     * @param nome Novo nome do atributo.
     */
    public void setNome(String nome){
        this.nome=nome;
    }

    /**
     * Retorna o valor do atributo.
     * @return o valor do atributo.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define o valor do atributo.
     * @param valor Novo valor do atributo.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}