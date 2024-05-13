
import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridicaDAO;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastro.model.util.ConectorBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CadastroBDTeste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();

        int escolha;

        do {
            System.out.println("=================================");
            System.out.println("1 - Incluir pessoa");
            System.out.println("2 - Alterar pessoa");
            System.out.println("3 - Excluir pessoa");
            System.out.println("4 - Buscar pelo Id");
            System.out.println("5 - Exibir todos");
            System.out.println("0 - Finalizar Programa");
            System.out.println("=================================");
            escolha = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (escolha) {
                    case 1:
                        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
                        char tipoInclusao = scanner.next().charAt(0);
                        scanner.nextLine();

                        if (tipoInclusao == 'F' || tipoInclusao == 'f') {
                            cadastrarPessoaFisica(pessoaFisicaDAO, scanner);
                        } else if (tipoInclusao == 'J' || tipoInclusao == 'j') {
                            cadastrarPessoaJuridica(pessoaJuridicaDAO, scanner);
                        } else {
                            System.out.println("Tipo inválido!");
                        }
                        break;

                    case 2:
                        alterarPessoa(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;

                    case 3:
                        excluirPessoa(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;

                    case 4:
                        buscarPessoaPeloId(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;

                    case 5:
                        exibirTodasPessoas(pessoaFisicaDAO, pessoaJuridicaDAO, scanner);
                        break;
                    case 0:
                        System.out.println("Finalizando o programa...");
                        break;
                    default:
                        System.out.println("Operação inválida! Verifique e tente novamente.");
                }
            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        } while (escolha != 0);
    }
    private static void cadastrarPessoaFisica(PessoaFisicaDAO pessoaFisicaDAO, Scanner scanner) throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade:");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Telefone:");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        PessoaFisica novaPessoaFisica = new PessoaFisica(cpf, nome, logradouro, cidade, estado, telefone, email);
        pessoaFisicaDAO.incluir(novaPessoaFisica);
        System.out.println("Pessoa Física cadastrada com sucesso!");
    }

    private static void cadastrarPessoaJuridica(PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();

        PessoaJuridica novaPessoaJuridica = new PessoaJuridica(cnpj, nome, logradouro, cidade, estado, telefone, email);
        pessoaJuridicaDAO.incluir(novaPessoaJuridica);
        System.out.println("Pessoa Jurídica cadastrada com sucesso!");
    }

    private static void alterarPessoa(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Informe o ID da pessoa física a ser alterada:");
            int id = scanner.nextInt();
            scanner.nextLine();

            PessoaFisica pessoaExistente = pessoaFisicaDAO.getPessoa(id);

            if (pessoaExistente != null) {
                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("Novo logradouro: ");
                String novoLogradouro = scanner.nextLine();
                System.out.print("Nova cidade: ");
                String novaCidade = scanner.nextLine();
                System.out.print("Novo estado: ");
                String novoEstado = scanner.nextLine();
                System.out.print("Novo telefone: ");
                String novoTelefone = scanner.nextLine();
                System.out.print("Novo e-mail: ");
                String novoEmail = scanner.nextLine();
                System.out.print("Novo CPF: ");
                String novoCpf = scanner.nextLine();

                PessoaFisica novaPessoa = new PessoaFisica(novoCpf, novoNome, novoLogradouro, novaCidade, novoEstado, novoTelefone, novoEmail);
                novaPessoa.setId(pessoaExistente.getId());
                pessoaFisicaDAO.alterar(novaPessoa);
                System.out.println("Pessoa física atualizada com sucesso!");
            } else {
                System.out.println("Pessoa física não encontrada.");
            }
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Informe o ID da pessoa jurídica a ser alterada:");
            int id = scanner.nextInt();
            scanner.nextLine();

            PessoaJuridica pessoaExistente = pessoaJuridicaDAO.getPessoa(id);

            if (pessoaExistente != null) {
                System.out.print("Novo nome: ");
                String novoNome = scanner.nextLine();
                System.out.print("Novo logradouro: ");
                String novoLogradouro = scanner.nextLine();
                System.out.print("Nova cidade: ");
                String novaCidade = scanner.nextLine();
                System.out.print("Novo estado: ");
                String novoEstado = scanner.nextLine();
                System.out.print("Novo telefone: ");
                String novoTelefone = scanner.nextLine();
                System.out.print("Novo e-mail: ");
                String novoEmail = scanner.nextLine();
                System.out.print("Novo CNPJ: ");
                String novoCnpj = scanner.nextLine();

                PessoaJuridica novaPessoa = new PessoaJuridica(novoCnpj, novoNome, novoLogradouro, novaCidade, novoEstado, novoTelefone, novoEmail);
                novaPessoa.setId(pessoaExistente.getId());
                pessoaJuridicaDAO.alterar(novaPessoa);
                System.out.println("Pessoa jurídica atualizada com sucesso!");
            } else {
                System.out.println("Pessoa jurídica não encontrada.");
            }
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    private static void excluirPessoa(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Informe o ID da pessoa física a ser excluída:");
            int id = scanner.nextInt();
            pessoaFisicaDAO.excluir(id);
            System.out.println("Pessoa física excluída com sucesso!");
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Informe o ID da pessoa jurídica a ser excluída:");
            int id = scanner.nextInt();
            pessoaJuridicaDAO.excluir(id);
            System.out.println("Pessoa jurídica excluída com sucesso!");
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    private static void buscarPessoaPeloId(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Informe o ID da pessoa física:");
            int id = scanner.nextInt();
            scanner.nextLine();

            PessoaFisica pessoaFisica = pessoaFisicaDAO.getPessoa(id);

            if (pessoaFisica != null) {
                System.out.println("Pessoa física localizada!");
                System.out.println("ID: " + pessoaFisica.getId());
                System.out.println("Nome: " + pessoaFisica.getNome());
                System.out.println("Logradouro: " + pessoaFisica.getlogradouro());
                System.out.println("Cidade: " + pessoaFisica.getCidade());
                System.out.println("Estado: " + pessoaFisica.getEstado());
                System.out.println("Telefone: " + pessoaFisica.getTelefone());
                System.out.println("E-mail: " + pessoaFisica.getEmail());
                System.out.println("CPF: " + pessoaFisica.getCpf());
            } else {
                System.out.println("Não foi possível localizar esta pessoa física");
            }
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Informe o ID da pessoa jurídica:");
            int id = scanner.nextInt();
            scanner.nextLine();

            PessoaJuridica pessoaJuridica = pessoaJuridicaDAO.getPessoa(id);

            if (pessoaJuridica != null) {
                System.out.println("Pessoa jurídica localizada!");
                System.out.println("ID: " + pessoaJuridica.getId());
                System.out.println("Nome: " + pessoaJuridica.getNome());
                System.out.println("Logradouro: " + pessoaJuridica.getlogradouro());
                System.out.println("Cidade: " + pessoaJuridica.getCidade());
                System.out.println("Estado: " + pessoaJuridica.getEstado());
                System.out.println("Telefone: " + pessoaJuridica.getTelefone());
                System.out.println("E-mail: " + pessoaJuridica.getEmail());
                System.out.println("CNPJ: " + pessoaJuridica.getCnpj());
            } else {
                System.out.println("Não foi possível localizar esta pessoa jurídica.");
            }
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    private static void exibirTodasPessoas(PessoaFisicaDAO pessoaFisicaDAO, PessoaJuridicaDAO pessoaJuridicaDAO, Scanner scanner) throws SQLException {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        char tipoPessoa = scanner.next().charAt(0);
        scanner.nextLine();

        if (tipoPessoa == 'F' || tipoPessoa == 'f') {
            System.out.println("Todas as pessoas físicas cadastradas");
            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            if (pessoasFisicas.isEmpty()) {
                System.out.println("Nenhum cadastro de pessoa física.");
            } else {
                for (PessoaFisica pf : pessoasFisicas) {
                    System.out.println("ID: " + pf.getId());
                    System.out.println("Nome: " + pf.getNome());
                    System.out.println("Logradouro: " + pf.getlogradouro());
                    System.out.println("Cidade: " + pf.getCidade());
                    System.out.println("Estado: " + pf.getEstado());
                    System.out.println("Telefone: " + pf.getTelefone());
                    System.out.println("E-mail: " + pf.getEmail());
                    System.out.println("CPF: " + pf.getCpf());
                    System.out.println();
                }
            }
        } else if (tipoPessoa == 'J' || tipoPessoa == 'j') {
            System.out.println("Todas as pessoas jurídicas cadastradas");
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            if (pessoasJuridicas.isEmpty()) {
                System.out.println("Nenhuma pessoa jurídica cadastrada.");
            } else {
                for (PessoaJuridica pj : pessoasJuridicas) {
                    System.out.println("ID: " + pj.getId());
                    System.out.println("Nome: " + pj.getNome());
                    System.out.println("Logradouro: " + pj.getlogradouro());
                    System.out.println("Cidade: " + pj.getCidade());
                    System.out.println("Estado: " + pj.getEstado());
                    System.out.println("Telefone: " + pj.getTelefone());
                    System.out.println("E-mail: " + pj.getEmail());
                    System.out.println("CNPJ: " + pj.getCnpj());
                    System.out.println();
                }
            }
        } else {
            System.out.println("Tipo inválido.");
        }
    }

    private static void exibirPessoasFisicas(PessoaFisicaDAO pessoaFisicaDAO) throws SQLException {
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        if (pessoasFisicas.isEmpty()) {
            System.out.println("Nenhuma pessoa física cadastrada.");
        } else {
            for (PessoaFisica pf : pessoasFisicas) {
                System.out.println("ID: " + pf.getId());
                System.out.println("Nome: " + pf.getNome());
                System.out.println("Logradouro: " + pf.getlogradouro());
                System.out.println("Cidade: " + pf.getCidade());
                System.out.println("Estado: " + pf.getEstado());
                System.out.println("Telefone: " + pf.getTelefone());
                System.out.println("E-mail: " + pf.getEmail());
                System.out.println("CPF: " + pf.getCpf());
                System.out.println();
            }
        }
    }

    private static void exibirPessoasJuridicas(PessoaJuridicaDAO pessoaJuridicaDAO) throws SQLException {
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        if (pessoasJuridicas.isEmpty()) {
            System.out.println("Nenhuma pessoa jurídica cadastrada.");
        } else {
            for (PessoaJuridica pj : pessoasJuridicas) {
                System.out.println("ID: " + pj.getId());
                System.out.println("Nome: " + pj.getNome());
                System.out.println("Logradouro: " + pj.getlogradouro());
                System.out.println("Cidade: " + pj.getCidade());
                System.out.println("Estado: " + pj.getEstado());
                System.out.println("Telefone: " + pj.getTelefone());
                System.out.println("E-mail: " + pj.getEmail());
                System.out.println("CNPJ: " + pj.getCnpj());
                System.out.println();
            }
        }
    }
}