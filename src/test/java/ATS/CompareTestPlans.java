package ATS;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CompareTestPlans {
    String baseUrl = "http://sistemisvr3.iskraemeco.si:8080";

    public List<JSONObject> fetchAndModifyTestPlans() {
        Response response = given().baseUri(baseUrl).when().param("api-version", "5.0")
                .header("Authorization", "Basic bW9lc2FtOjNlbnZnZ3V4NW9lYTZvd2ZsdWFkM3Vna2l5aXpkZGc1Njdqbm1idndpeXF1cjNkanFxdGE=")
                .get("/tfs/DefaultCollection/VerificationAndValidation/_apis/test/plans");

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

    @org.testng.annotations.Test
    public void test1() {
        Response response = given()
                .header("Authorization", "Bearer lytr42gc5kp64hlxlstkhosmtzvqrpquhjbqgwk4wrjsiebbp5ya")
                .relaxedHTTPSValidation()
                .param("projectName", "VerificationAndValidation")
                .get("https://ats.iskraemeco.egy/apis/TFS/GetPlans");

        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        JSONArray valueArray = jsonObject.getJSONArray("result");

        List<JSONObject> modifiedObjects = new ArrayList<>();

        for (int i = 0; i < valueArray.length(); i++) {
            JSONObject originalObject = valueArray.getJSONObject(i);

            JSONObject modifiedObject = new JSONObject();
            modifiedObject.put("name", originalObject.getString("name"));
            modifiedObject.put("id", originalObject.getInt("id"));
            modifiedObjects.add(modifiedObject);
        }

        List<JSONObject> testModifiedObjects = fetchAndModifyTestPlans();

        boolean listsAreSame = true;

        if (modifiedObjects.size() != testModifiedObjects.size()) {
            listsAreSame = false;
        } else {
            for (int i = 0; i < modifiedObjects.size(); i++) {
                JSONObject obj1 = modifiedObjects.get(i);
                JSONObject obj2 = testModifiedObjects.get(i);

                if (!obj1.getString("name").equals(obj2.getString("name"))
                        || obj1.getInt("id") != obj2.getInt("id")) {
                    listsAreSame = false;
                    break;
                }
            }
        }

        if (listsAreSame) {
            System.out.println("Lists are the same based on names and ids.");
        } else {
            System.out.println("Lists are different based on names and ids.");
        }
        if (modifiedObjects.size() == testModifiedObjects.size()){
            System.out.println("Lists are the same Size");
        }else {
            System.out.println("Lists are not the same Size");
        }
        System.out.println(modifiedObjects);
        System.out.println(testModifiedObjects);
    }
}
