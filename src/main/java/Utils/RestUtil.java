package Utils;

import io.restassured.RestAssured;

//Setting base URI, port and Path.
public class RestUtil {
    public static void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public static void setPort(int port) {
        RestAssured.port = port;
    }

    public static void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }

    public static void resetBasePath() {
        RestAssured.basePath = null;
    }

}
