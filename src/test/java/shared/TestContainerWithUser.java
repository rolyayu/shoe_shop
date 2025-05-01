package shared;

import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestContainerWithUser extends BaseTestContainer {
    protected static User user;

    @BeforeAll
    protected static void setUpUser() {
        user = new User();
        user.setUsername("ShoeShop");
        user.setEncodedPassword("password");
        user.setRole(UserRole.ORGANIZATION_OWNER);
    }

    @BeforeEach
    protected void resetUser() {
        user.setId(null);
        setUserRole(UserRole.ORGANIZATION_OWNER);
    }

    protected static void setUserRole(final UserRole newRole) {
        user.setRole(newRole);
    }
}
