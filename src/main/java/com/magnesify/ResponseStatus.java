package com.magnesify;

import org.bukkit.Bukkit;

public class ResponseStatus {

    public static boolean isLicenceValid(String response) {
        String ip = Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort();
        if(ip.equalsIgnoreCase(response)) {
            return true;
        } else {
            return false;
        }
    }

}
