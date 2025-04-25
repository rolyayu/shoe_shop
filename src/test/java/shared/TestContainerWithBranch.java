package shared;

import com.github.shoe_shop.organization.branch.Branch;
import com.github.shoe_shop.organization.branch.BranchType;
import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.Gender;
import com.github.shoe_shop.user.user_info.UserInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.UUID;

public class TestContainerWithBranch extends TestContainerOrganizationAndOwner {
    protected static Branch branch;

    protected static User branchHeadUser;

    protected static UserInfo branchHeadInfo;

    @BeforeAll
    public static void setup() {
        branch = new Branch();
        branch.setType(BranchType.SHOP);
        branch.setBranchAddress("Г. Минск");
        branch.setBranchNo("№1");

        branchHeadUser = new User();
        branchHeadUser.setUsername("BranchHEAD");
        branchHeadUser.setEncodedPassword("BranchHEAD");
        branchHeadUser.setRole(UserRole.HEAD_OF_BRANCH);

        branchHeadInfo = new UserInfo();
        branchHeadInfo.setGender(Gender.MALE);
        branchHeadInfo.setFullName("BranchHead");
        branchHeadInfo.setBirthDate(LocalDate.of(1995,1,25));
    }

    @BeforeEach
    public void cleanup() {
        branch.setBranchId(UUID.randomUUID().toString());
        branch.setOrganization(null);
        branch.setBranchHead(null);

        branchHeadUser.setId(null);

        branchHeadInfo.setId(null);
        branchHeadInfo.setUser(null);
    }
}
