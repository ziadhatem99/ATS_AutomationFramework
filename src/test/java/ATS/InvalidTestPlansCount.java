package ATS;

import ATS_DTO.GenericMethods;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static Assets.ATS_URLS.ATS_BaseURL;
import static Assets.ATS_URLS.ATS_InvalidData;
import static Assets.GlobalConstans.ATS_Token;
import static Assets.GlobalConstans.projectName;
import static io.restassured.RestAssured.given;

public class InvalidTestPlansCount {
    public List<JSONObject> fetchInvalidDataFromATS() {
        Response response = given()
                .baseUri(ATS_BaseURL)
                .when().log().all()
                .relaxedHTTPSValidation()
                .param("errorType", "WrongCount")
                .param("projectName", projectName)
                .header("Authorization" , ATS_Token)
                .get(ATS_InvalidData);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        JSONArray valueArray = jsonObject.getJSONArray("result");

        List<JSONObject> ATSmodifiedObjects = new ArrayList<>();

        for (int i = 0; i < valueArray.length(); i++) {
            JSONObject originalObject = valueArray.getJSONObject(i);

            JSONObject modifiedObject = new JSONObject();
            modifiedObject.put("name", originalObject.getString("name"));
            modifiedObject.put("id", originalObject.getInt("id"));
            ATSmodifiedObjects.add(modifiedObject);
        }

        return ATSmodifiedObjects;
    }

    @Test
    public void validateCount() {
        int TFS_TestPlans = GenericMethods.fetchAndModifyTestPlansFromTFS().size();
        int ATS_InvalidCount = fetchInvalidDataFromATS().size();
        System.out.println("===============================================");
        System.out.println("Expected Count From TFS: " + TFS_TestPlans);
        System.out.println("Actual Count From ATS: " + ATS_InvalidCount);
        Assert.assertNotEquals(ATS_InvalidCount, TFS_TestPlans);
    }
}
