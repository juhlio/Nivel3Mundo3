package cadastro.model.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {

    ConectorBD cnx = new ConectorBD();

    public int getValue(String sequenceName) throws SQLException {
        String sql = "SELECT NEXT VALUE FOR " + sequenceName;
        ResultSet rs = cnx.getSelect(sql);
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Não foi possível obter o próximo valor da sequência: " + sequenceName);
        }
    }
}