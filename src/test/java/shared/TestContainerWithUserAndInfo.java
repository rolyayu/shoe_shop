package shared;

import com.github.shoe_shop.user.user_info.Gender;
import com.github.shoe_shop.user.user_info.UserInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

public class TestContainerWithUserAndInfo extends TestContainerWithUser {

    protected static UserInfo userInfo;

    @BeforeAll
    protected static void setUpUserInfo() {
        userInfo = new UserInfo();
        userInfo.setBirthDate(LocalDate.of(2003, 1, 27));
        userInfo.setFullName("Shoe Shop");
        userInfo.setGender(Gender.MALE);
    }

    @BeforeEach
    protected void resetUserInfo() {
        userInfo.setUser(null);
        userInfo.setId(null);
    }
}
