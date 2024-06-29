package com.magnesify;

public class ResponseStatus {

    public static boolean isLicenceValid(String response) {
        String ip = "ip" + ":" + "port";
        if(ip.equalsIgnoreCase(response)) {
            return true;
        } else {
            return false;
        }
    }

}
