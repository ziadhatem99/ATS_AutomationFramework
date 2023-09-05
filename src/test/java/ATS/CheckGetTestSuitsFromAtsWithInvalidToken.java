package ATS;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static Assets.ATS_URLS.*;
import static Assets.GlobalConstans.FakeATS_Token;
import static Assets.GlobalConstans.projectName;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckGetTestSuitsFromAtsWithInvalidToken {
    @Test
    public void checkSuitsWithFakeToken() {

        Response response = given()
                .baseUri(ATS_BaseURL)
                .when().log().all()
                .header("Authorization", FakeATS_Token)
                .relaxedHTTPSValidation()
                .param("projectName", projectName)
                .get(ATS_getSuits);


        response.then().log().all()
                .assertThat().body("isSuccess" , equalTo(false))
                .assertThat().statusCode(400)
                .time(Matchers.lessThan(2000L));
    }
}

