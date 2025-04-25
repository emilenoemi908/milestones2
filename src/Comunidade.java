import java.util.ArrayList;
import java.io.Serializable;

public class Comunidade implements Serializable{

    private static final long serialVersionUID = 1L;

    private String nomeComunidade;
    private String loginCriador;
    private String descricao;
    private ArrayList<String> membros;

    public Comunidade(String nomeComunidade, String loginCriador, String descricao){

        this.nomeComunidade=nomeComunidade;
        this.loginCriador=loginCriador;
        this.descricao=descricao;
        this.membros= new ArrayList<>();
    }

    public String getNomeComunidade(){
        return nomeComunidade;
    }

    public void setNomeComunidade(String nomeComunidade) {
        this.nomeComunidade = nomeComunidade;
    }

    public String getLoginCriador(){
        return loginCriador;
    }

    public void setLoginCriador(String loginCriador){
        this.loginCriador=loginCriador;
    }

    public ArrayList<String> getMembros(){
        return membros;
    }
    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String id){
        this.descricao=descricao;
    }

}

