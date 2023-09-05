package ATS;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static Assets.ATS_URLS.ATS_BaseURL;
import static Assets.ATS_URLS.ATS_getPlans;
import static Assets.GlobalConstans.*;
import static Assets.TFS_URLS.TFS_BaseUrl;
import static Assets.TFS_URLS.TFS_getPlans;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CheckGetTestPlansFromAtsWithInvalidToken {
    @Test
    public void checkPlansWithFakeToken() {

        Response response = given()
                .baseUri(ATS_BaseURL)
                .when().log().all()
                .header("Authorization", FakeATS_Token)
                .relaxedHTTPSValidation()
                .param("projectName", projectName)
                .get(ATS_getPlans);


        response.then().log().all()
                .assertThat().body("isSuccess" , equalTo(false))
                .assertThat().statusCode(400)
                .time(Matchers.lessThan(2000L));
    }
}

