package by.tms.bookpoint.configuration;


import java.util.Arrays;

public class SecurityEndpoints {

    public static String[] publicALL() {
        return new String[]{
                "/db/**", //db H2 for dev
//                "/account/**", // delete after test
                "/room/**",// delete after test
                "/booking/**"// delete after test
        };
    }
    public static String[] publicGET() {
        return new String[]{
                "/",
                "/api/health",
                "/account/**"
        };
    }

    public static String[] publicPOST() {
        return new String[]{
                "/account/**",
                "/room/1/point/**",
                "/auth/**"
        };
    }

    public static String[] adminDELETE() {
        return new String[]{
                "/account/**"
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
