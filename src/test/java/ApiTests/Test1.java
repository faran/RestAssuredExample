package ApiTests;

import Utils.HelperMethods;
import Utils.RestUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Date;


public class Test1 {

    private final String bookingDate = "2017-08-16";
    private final Integer numOfDaysBooked = 1;
    private final String json = "{\"numOfDays\":" + numOfDaysBooked + ", \"checkInDate\":\"" + bookingDate + "\"}";

    @BeforeTest
    public void setup() {
        RestUtil.setBaseURI("http://localhost");
        RestUtil.setPort(9090);
    }

    @AfterMethod
    public void reset() {
        RestUtil.resetBasePath();
    }

    //This test case does some pre and post processing.

    @Test
    public void T01_bookRoom() throws ParseException{
        //I am reading # of rooms available so i can compare later with rooms available after i did booking.
        int roomsAvailableBeforeBooking = HelperMethods.getRoomsAvailable(bookingDate);
        //int totalPrice=0;
        //String checkInDate="";
        //Only booking rooms if they are available more than 0
        if (HelperMethods.getRoomsAvailable(bookingDate) > 0) {
            //Setting base path to /
            RestUtil.setBasePath("/");
            //Post json to end point /bookRoom
            Response res = HelperMethods.post("/bookRoom", json);
            //Make sure i am getting back http 200
            HelperMethods.checkStatusIs200(res);
            HelperMethods.getBody(res);
            //Taking out total price and check in date to compare later
            int totalPrice = res.path("totalPrice");
            String checkInDate = res.path("checkInDate");

        } else {
            System.out.println("All rooms booked");
        }

        int roomsAvailableAfterBooking = HelperMethods.getRoomsAvailable(bookingDate);
        //Post processing for my asserts.
        if (roomsAvailableAfterBooking > 0) {
            int roomsLeft = roomsAvailableBeforeBooking - 2;
            System.out.println("Actual: " + roomsAvailableAfterBooking + " Rooms still available");
            System.out.println("Expected: " + roomsLeft + " Rooms left for booking");
            //Comparing value to make sure my expectation of rooms left after i made booking.
            Assert.assertEquals(roomsAvailableAfterBooking, roomsLeft);
        }

        Date checkIn = HelperMethods.convertDate(bookingDate);
        HelperMethods.getDayOfTheWeek(checkIn);
        int getDayOfTheWeek = HelperMethods.getDayOfTheWeek(checkIn);
        int price = HelperMethods.getPriceDayOfWeek(getDayOfTheWeek);

        //Assert.assertEquals(totalPrice, price);
        //Assert.assertEquals(checkInDate,bookingDate);

    }

}