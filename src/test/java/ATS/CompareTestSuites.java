package ATS;

import ATS_DTO.GenericMethods;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static Assets.TFS_URLS.*;
import static Assets.ATS_URLS.*;
import static Assets.GlobalConstans.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CompareTestSuites {
    @Test
    public void validateOnTestSuits() {
        List<JSONObject> TFSmodifiedObjects = GenericMethods.fetchAndModifyTestSuitsFromTFS();
        List<JSONObject> ATSmodifiedObjects = GenericMethods.fetchAndModifyTestSuitsFromATS();

        Assert.assertEquals(TFSmodifiedObjects.toString() , ATSmodifiedObjects.toString());
        Assert.assertEquals(TFSmodifiedObjects.size() , ATSmodifiedObjects.size());

        System.out.println(TFSmodifiedObjects);
        System.out.println(ATSmodifiedObjects);
    }
}
