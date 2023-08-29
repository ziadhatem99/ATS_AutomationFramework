package ATS;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static Assets.TFS_URLS.*;
import static Assets.GlobalConstans.*;
import static Assets.ATS_URLS.*;
import static io.restassured.RestAssured.given;

public class CompareTestPlans {

    public List<JSONObject> fetchAndModifyTestPlansFromTFS() {
        Response response = given().
                baseUri(TFS_BaseUrl).
                when().log().all().
                param("api-version", "5.0")
                .header("Authorization", TFS_Token)
                .get(TFS_getPlans);
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
    } //This was for the TFS collection

    public List<JSONObject> fetchAndModifyTestPlansFromATS() {
        Response response = given()
                .baseUri(ATS_BaseURL)
                .when().log().all()
                .header("Authorization", ATS_Token)
                .relaxedHTTPSValidation()
                .param("projectName", projectName)
                .get(ATS_getPlans);
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

    @Test
    public void validateOnTheTwoLists() {
        List<JSONObject> TFSmodifiedObjects = fetchAndModifyTestPlansFromTFS();
        List<JSONObject> ATSmodifiedObjects = fetchAndModifyTestPlansFromATS();
        boolean listsAreSame = true;

        if (TFSmodifiedObjects.size() != ATSmodifiedObjects.size()) {
            listsAreSame = false;
        } else {
            for (int i = 0; i < TFSmodifiedObjects.size(); i++) {
                JSONObject obj1 = TFSmodifiedObjects.get(i);
                JSONObject obj2 = ATSmodifiedObjects.get(i);

                if (!obj1.getString("name").equals(obj2.getString("name"))
                        || obj1.getInt("id") != obj2.getInt("id")) {
                    listsAreSame = false;
                    break;
                }
            }
        }
        System.out.println(TFSmodifiedObjects);
        System.out.println(ATSmodifiedObjects);

        if (listsAreSame) {
            System.out.println("Lists are the same based on names and ids.");
        } else {
            System.out.println("Lists are different based on names and ids.");
        }
        if (TFSmodifiedObjects.size() == ATSmodifiedObjects.size()) {
            System.out.println("Lists are the same Size");
        } else {
            System.out.println("Lists are not the same Size");
        }
    }
}
