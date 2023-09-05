package ATS_DTO;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static Assets.ATS_URLS.*;
import static Assets.GlobalConstans.*;
import static Assets.TFS_URLS.*;
import static io.restassured.RestAssured.given;

public class GenericMethods {
    public static List<JSONObject> fetchAndModifyTestPlansFromATS() {

        Response response = given()
                .baseUri(ATS_BaseURL)
                .when().log().all()
                .header("Authorization", ATS_Token)
                .relaxedHTTPSValidation()
                .param("projectName", projectName)
                .get(ATS_getPlans);

        response.then().assertThat().statusCode(200)
//                .log().all()
                .time(Matchers.lessThan(2000L));
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        JSONArray valueArray = jsonObject.getJSONArray("result");

        List<JSONObject> TFSmodifiedObjects = new ArrayList<>();

        for (int i = 0; i < valueArray.length(); i++) {
            JSONObject originalObject = valueArray.getJSONObject(i);

            JSONObject modifiedObject = new JSONObject();
            modifiedObject.put("name", originalObject.getString("name"));
            modifiedObject.put("id", originalObject.getInt("id"));
            TFSmodifiedObjects.add(modifiedObject);
        }
        return TFSmodifiedObjects;
    }
    public static List<JSONObject> fetchAndModifyTestPlansFromTFS() {
        Response response = given().
                baseUri(TFS_BaseUrl).
                when().log().all().
                param("api-version", "5.0")
                .header("Authorization", TFS_Token)
                .get(TFS_getPlans);
        response.then().assertThat().statusCode(200)
//                .log().all()
                .time(Matchers.lessThan(2000L));

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        JSONArray valueArray = jsonObject.getJSONArray("value");

        List<JSONObject> modifiedObjectsList = new ArrayList<>();

        for (int i = 0; i < valueArray.length(); i++) {
            JSONObject originalObject = valueArray.getJSONObject(i);

            JSONObject modifiedObject = new JSONObject();
            modifiedObject.put("name", originalObject.getString("name"));
            modifiedObject.put("id", originalObject.getInt("id"));
            modifiedObjectsList.add(modifiedObject);
        }

        return modifiedObjectsList;
    }
    public static List<JSONObject> fetchAndModifyTestSuitsFromTFS() {
        Response response = given().
                baseUri(TFS_BaseUrl)
                .when().log().all()
                .param("api-version", "5.0")
                .header("Authorization", TFS_Token)
                .get(TFS_getSuits);

        response.then().assertThat().statusCode(200);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        JSONArray valueArray = jsonObject.getJSONArray("value");

        List<JSONObject> modifiedObjectsList = new ArrayList<>();

        for (int i = 0; i < valueArray.length(); i++) {
            JSONObject originalObject = valueArray.getJSONObject(i);

            JSONObject modifiedObject = new JSONObject();
            modifiedObject.put("name", originalObject.getString("name"));
            modifiedObject.put("id", originalObject.getInt("id"));
            modifiedObjectsList.add(modifiedObject);
        }

        return modifiedObjectsList;
    }
    public static List<JSONObject> fetchAndModifyTestSuitsFromATS() {
        Response response = given()
                .baseUri(ATS_BaseURL)
                .when().log().all()
                .header("Authorization", ATS_Token)
                .relaxedHTTPSValidation()
                .param("projectName", projectName).
                param("planID", testPlanID)
                .get(ATS_getSuits);
        response.then().assertThat().statusCode(200);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        JSONArray valueArray = jsonObject.getJSONArray("result");

        List<JSONObject> TFSmodifiedObjects = new ArrayList<>();

        for (int i = 0; i < valueArray.length(); i++) {
            JSONObject originalObject = valueArray.getJSONObject(i);

            JSONObject modifiedObject = new JSONObject();
            modifiedObject.put("name", originalObject.getString("name"));
            modifiedObject.put("id", originalObject.getInt("id"));
            TFSmodifiedObjects.add(modifiedObject);
        }
        return TFSmodifiedObjects;

    }
}
