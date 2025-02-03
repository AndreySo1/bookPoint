package by.tms.bookpoint.configuration;


import java.util.Arrays;

public class SecurityEndpoints {

    public static String[] publicALL() {
        return new String[]{
                "/db/**", //db H2 for dev
                "/api/**",// access to Sagger
                "/doc/**",// access to Sagger
                "/swagger-ui/**",// access to Sagger
                "/v3/api-docs/**",    // Документация OpenAPI
                "/swagger-ui/**",     // Swagger UI HTML и JS файлы
                "/swagger-ui.html",
                "/auth/login",
        };
    }
    public static String[] publicGET() {
        return new String[]{
                "/",
                "/api/health",
                "/account/all",
                "/room/all",
                "/room/*/point/**",
        };
    }

    public static String[] publicPOST() {
        return new String[]{
                "/account/create",
                "/room/*/point/*/available",

        };
    }

    public static String[] userAccess() {
        return new String[]{
                "/account/all",
                "/room/all",
                "/room/*/point/**",
                "/booking/create",
        };
    }

    public static String[] userAccessGet() {
        return new String[]{
                "/account/**",
                "/**", //delete after
        };
    }

    public static String[] userAccessPost() {
        return new String[]{
                "/account/create",
                "/room/*/point/*/available",
                "/**", //delete after
        };
    }

    public static String[] userAccessPut() {
        return new String[]{
                "/account/**",
                "/booking/**",
                "/**", //delete after
        };
    }

    public static String[] adminDELETE() {
        return new String[]{
                "/account/**",
                "/room/**",
                "/booking/**"
        };
    }

    //UPDATE ACCOUNT_AUTHORITIES SET AUTHORITIES='1' WHERE ACCOUNT_ID=2;

//    public static String[] allPublicEndpoints() {
//        return Arrays.stream(new String[][]{
//                publicGET(),
//                publicPOST()
//        }).flatMap(Arrays::stream).toArray(String[]::new);
//    }
}
