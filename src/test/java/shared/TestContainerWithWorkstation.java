package shared;

import com.github.shoe_shop.organization.workstations.Workstation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestContainerWithWorkstation extends TestContainerWithBranch {
    protected static Workstation workstation;

    @BeforeAll
    static void setUp() {
        workstation = new Workstation();
    }

    @BeforeEach
    void resetWorkstation() {
        workstation.setBranch(null);
        workstation.setId(null);
        workstation.setId(null);
    }
}
