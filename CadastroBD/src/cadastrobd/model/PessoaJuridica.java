package cadastrobd.model;


public class PessoaJuridica extends Pessoa {

    private String cnpj;

    public PessoaJuridica() {
        super();
    }

    public PessoaJuridica(String cnpj, String _nome, String _logradouro, String _cidade, String _estado, String _telefone, String _email) {
        super(_nome, _logradouro, _cidade, _estado, _telefone, _email);
        this.cnpj = cnpj;
    }

    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CNPJ: " + this.getCnpj().toString());
    }


    public String getCnpj() {
        return cnpj;
    }


    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

}