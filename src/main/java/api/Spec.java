package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.PropertiesUtil;

public class Spec {

    private final String baseUri;
    private final String basePath;
    private final boolean isDebug;
    private final String login;
    private final String password;


    public Spec() {
        var prop = new PropertiesUtil();
        baseUri = prop.getProperty("api.baseUri");
        basePath = prop.getProperty("api.basePath");
        login = prop.getProperty("api.login");
        password = prop.getProperty("api.password");
        isDebug = Boolean.parseBoolean(prop.getProperty("api.debug"));
    }

    public RequestSpecification getSpecAuth() {
        return getBaseRequestSpecBuilder().build().auth().preemptive().basic(login, password);
    }

    public RequestSpecification getSpecNotAuth() {
        return getBaseRequestSpecBuilder().build();
    }

    public ResponseSpecification getRespSpec() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

        if (isDebug) responseSpecBuilder.log(LogDetail.ALL);

        return responseSpecBuilder.build();
    }

    private RequestSpecBuilder getBaseRequestSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);

        if (isDebug) requestSpecBuilder.log(LogDetail.ALL);
        return requestSpecBuilder;
    }
}
