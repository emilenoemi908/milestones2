import java.io.Serializable;

public class Atributo implements Serializable{

    private static final long serialVersionUID = 1L;
    private String nome;
    private String valor;

    public Atributo(String nome,String valor){
        this.nome=nome;
        this.valor=valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}