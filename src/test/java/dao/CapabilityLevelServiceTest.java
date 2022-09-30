package dao;

import com.kainos.ea.dao.CapabilityLevel;
import com.kainos.ea.database.DataBaseConnection;
import com.kainos.ea.models.Capability;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.kainos.ea.dao.CapabilityLevel.getCapabilities;
import static com.kainos.ea.database.DataBaseConnection.getConnection;
import static org.junit.jupiter.api.Assertions.*;

class CapabilityLevelServiceTest {
    CapabilityLevel cap = Mockito.mock(CapabilityLevel.class);
    DataBaseConnection databaseConnector = Mockito.mock(DataBaseConnection.class);



    Connection conn;

    @Test
    void getCapabilities_shouldReturnAList() throws SQLException {
        List<Capability> expectedResult = new ArrayList<>();
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(cap.getCapabilities()).thenReturn(expectedResult);

        List<Capability> result = cap.getCapabilities();

        assertEquals(result, expectedResult);
    }
}