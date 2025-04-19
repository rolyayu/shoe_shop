package shared;

import com.github.shoe_shop.organization.organization.Organization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestContainerOrganizationAndOwner extends TestContainerWithUserAndInfo {
    protected static Organization organization;

    @BeforeAll
    public static void setupOrganization() {
        organization = new Organization();
        organization.setUnp("11111112222222");
        organization.setOrganizationName("ГОМСЕЛЬМАШ");
        organization.setAddress("г. Минск");
        organization.setOrganizationHead(userInfo);
    }

    @BeforeEach
    public void resetOrganization() {
        organization.setOrganizationHead(null);
    }
}
