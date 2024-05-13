package cadastro.model;

import cadastrobd.model.Pessoa;
import cadastrobd.model.PessoaJuridica;
import cadastro.model.util.ConectorBD;

import java.sql.*;
import java.util.ArrayList;

public class PessoaJuridicaDAO {

	ConectorBD cnx = new ConectorBD();

	public PessoaJuridica getPessoa(Integer id) throws SQLException {
		ResultSet rs = cnx.getSelect("select\n" + "	PessoaJuridica.idPessoaJuridica as id,\n" + "	PessoaJuridica.cnpj,\n"
				+ "	p.nome,\n" + "	p.logradouro,\n" + "	p.cidade,\n" + "	p.estado,\n" + "	p.telefone,\n"
				+ "	p.email\n" + "	from PessoaJuridica\n"
				+ "INNER JOIN Pessoa as p on PessoaJuridica.idPessoaJuridica = p.idPessoa\n" + "WHERE\n"
				+ "	PessoaJuridica.idPessoaJuridica = " + id.toString());

		rs.next();
		PessoaJuridica p = new PessoaJuridica(rs.getString("cnpj"), rs.getString("nome"), rs.getString("logradouro"),
				rs.getString("cidade"), rs.getString("estado"), rs.getString("telefone"), rs.getString("email"));
		p.setId(rs.getInt("id"));
		p.exibir();
		cnx.close();
		return p;
	}

	public ArrayList<PessoaJuridica> getPessoas() throws SQLException {
		ArrayList<PessoaJuridica> list = new ArrayList<PessoaJuridica>();

		ResultSet rs = cnx.getSelect("select\n" + "	PessoaJuridica.idPessoaJuridica as id,\n" + "	PessoaJuridica.cnpj,\n"
				+ "	p.nome,\n" + "	p.logradouro,\n" + "	p.cidade,\n" + "	p.estado,\n" + "	p.telefone,\n"
				+ "	p.email\n" + "	from PessoaJuridica\n"
				+ "INNER JOIN Pessoa as p on PessoaJuridica.idPessoaJuridica = p.idPessoa;");

		while (rs.next()) {
			PessoaJuridica p = new PessoaJuridica(rs.getString("cnpj"), rs.getString("nome"),
					rs.getString("logradouro"), rs.getString("cidade"), rs.getString("estado"),
					rs.getString("telefone"), rs.getString("email"));
			p.setId(rs.getInt("id"));
			list.add(p);
		}
		cnx.close();
		return list;
	}

	public void incluir(PessoaJuridica p) throws SQLException {
		String sqlInsertPessoa = "INSERT INTO Pessoa (idPessoa, nome, logradouro, cidade, estado, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
		String sqlInsertPessoaJuridica = "INSERT INTO PessoaJuridica (idPessoaJuridica, cnpj) VALUES (?, ?)";

		try (Connection con = cnx.getConnection();
				PreparedStatement stmtPessoa = con.prepareStatement(sqlInsertPessoa, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement stmtPessoaJuridica = con.prepareStatement(sqlInsertPessoaJuridica)) {

			try (Statement stmtSequence = con.createStatement();
					ResultSet rsSequence = stmtSequence
							.executeQuery("SELECT max(p.idPessoa) as proximoValor FROM Pessoa p")) {

				if (rsSequence.next()) {
					Integer idNovaPessoa = rsSequence.getInt("proximoValor");
					p.setId(idNovaPessoa + 1);
				} else {
					throw new SQLException("Failed to obtain next value from sequence.");
				}
			}

			stmtPessoa.setString(1, p.getId().toString());
			stmtPessoa.setString(2, p.getNome());
			stmtPessoa.setString(3, p.getlogradouro());
			stmtPessoa.setString(4, p.getCidade());
			stmtPessoa.setString(5, p.getEstado());
			stmtPessoa.setString(6, p.getTelefone());
			stmtPessoa.setString(7, p.getEmail());
			int affectedRows = stmtPessoa.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}

			stmtPessoaJuridica.setInt(1, p.getId());
			stmtPessoaJuridica.setString(2, p.getCnpj());
			stmtPessoaJuridica.executeUpdate();

		}
	}

	public void alterar(PessoaJuridica novaPessoa) throws SQLException {
		String sqlUpdatePessoaJuridica = String.format(
				"UPDATE PessoaJuridica SET cnpj = '%s' where PessoaJuridica.idPessoaJuridica  = %s;",
				novaPessoa.getCnpj(), novaPessoa.getId());
		System.out.println(sqlUpdatePessoaJuridica);
		cnx.update(sqlUpdatePessoaJuridica);

		ResultSet rs = cnx.getSelect("SELECT pj.idPessoaJuridica FROM PessoaJuridica pj WHERE idPessoaJuridica = "
				+ novaPessoa.getId() + ";");
		if (rs.next()) {
			int id_pessoaAssociadaA_PessoaJuridica = rs.getInt(1);

			String sqlUpdatePessoa = String.format(
					"UPDATE Pessoa SET nome = '%s', logradouro = '%s', cidade = '%s', estado='%s', telefone = '%s',  email = '%s'  WHERE idPessoa = %s;",
					novaPessoa.getNome(), novaPessoa.getlogradouro(), novaPessoa.getCidade(), novaPessoa.getEstado(),
					novaPessoa.getTelefone(), novaPessoa.getEmail(), id_pessoaAssociadaA_PessoaJuridica);
			System.out.println(sqlUpdatePessoa);

			cnx.update(sqlUpdatePessoa);
		} else {
			System.out.println("Nenhum registro encontrado para o id = " + novaPessoa.getId());
		}
	}

	public void excluir(Integer id) throws SQLException {
		ResultSet rs = cnx
				.getSelect("SELECT idPessoaJuridica FROM PessoaJuridica pj WHERE pj.idPessoaJuridica = " + id + ";");
		if (rs.next()) {
			int id_pessoaAssociadaA_PessoaJuridica = rs.getInt(1);
			String sqlDeletePessoaJuridica = "DELETE FROM PessoaJuridica where idPessoaJuridica = " + id + ";";
			cnx.update(sqlDeletePessoaJuridica);
			String sqlDeletePessoa = "DELETE FROM Pessoa WHERE idPessoa = " + id_pessoaAssociadaA_PessoaJuridica + ";";
			cnx.update(sqlDeletePessoa);
		} else {
			System.out.println("Nenhum registro encontrado para excluir do id = " + id);
		}
	}

	public void close() throws SQLException {
		cnx.close();
	}
}