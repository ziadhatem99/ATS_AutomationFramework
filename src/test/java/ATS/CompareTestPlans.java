package ATS;

import ATS_DTO.GenericMethods;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static Assets.TFS_URLS.*;
import static Assets.GlobalConstans.*;
import static Assets.ATS_URLS.*;
import static io.restassured.RestAssured.given;

public class CompareTestPlans {
    @Test
    public void validateOnTheTwoLists() {
        List<JSONObject> TFSmodifiedObjects = GenericMethods.fetchAndModifyTestPlansFromTFS();
        List<JSONObject> ATSmodifiedObjects = GenericMethods.fetchAndModifyTestPlansFromATS();

        Assert.assertEquals(ATSmodifiedObjects.toString(),TFSmodifiedObjects.toString());
        Assert.assertEquals(ATSmodifiedObjects.size(), TFSmodifiedObjects.size());

        System.out.println(TFSmodifiedObjects);
        System.out.println(ATSmodifiedObjects);

    }
}
