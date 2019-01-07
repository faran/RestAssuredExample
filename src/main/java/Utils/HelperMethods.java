package Utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class HelperMethods {

    public static void checkStatusIs200(Response res) {
        assertEquals("Status check Failed!", 200, res.getStatusCode());
    }
    //Post request takes in base path and json string as body
    public static Response post(String url, String body) {
        return given().
                body(body).
                when().
                contentType(ContentType.JSON).
                post(url);
    }

    public static String getBody(Response res) {
        String body = res.getBody().asString();
        System.out.println("Returned body: " + body + "\n");
        return body;
    }
    //Takes in String and converts this to date format so we can verify the date.
    public static Date convertDate(String sDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(sDate);
        return date;
    }
    //Takes in date and return num of day in week
    public static Integer getDayOfTheWeek(Date date) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }
    //Takes in day of week and return the price, so we can verify the price
    public static Integer getPriceDayOfWeek(int day) {

        switch (day) {
            case 1:
                return 100 + 5 * 10;
            case 2:
                return 100 + 10 * 6;
            case 3:
                return 100;
            case 4:
                return 100 + 10;
            case 5:
                return 100 + 2 * 10;
            case 6:
                return 100 + 3 * 10;
            case 7:
                return 100 + 4 * 10;
            default:
                return 0;
        }

    }

    //Get request for checking rooms available
    public static Integer getRoomsAvailable(String bookingDate){

        Integer r = given().
                when().
                get("/checkAvailability/" + bookingDate).
                then().
                assertThat().
                statusCode(200).
                extract().path("rooms_available");
        return r;

    }

    public static String getCheckOutDate(String bookingDate){

        String d = given().
                when().
                get("/checkAvailability/" + bookingDate).
                then().
                assertThat().
                statusCode(200).
                extract().path("date");
        return d;

    }

}