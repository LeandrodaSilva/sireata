import br.edu.utfpr.dv.sireata.dao.ConnectionDAO;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;

public class ConnectionDAOTests {
    private ConnectionDAO conn;

    @Before
    public void setUp() throws Exception {
        this.conn = ConnectionDAO.getInstance();
    }

    @Test
    public void connectionDAOGetInstance1Test() throws CloneNotSupportedException, SQLException {
        ConnectionDAO connClone = this.conn.clone();

        assertEquals(false, connClone.equals(this.conn));
        assertEquals(true, this.conn.equals(ConnectionDAO.getInstance()));
    }
}
