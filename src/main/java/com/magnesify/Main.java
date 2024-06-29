package com.magnesify;

import java.awt.*;

import static com.magnesify.Licences.getResponse;
import static com.magnesify.ResponseStatus.isLicenceValid;

public class Main {

    public static void main(String[] args) {
        // 0 ip
        // 1 ürün
        String[] a = getResponse().split(",");
        for(int i = 0; i < a.length; i++) {
            System.out.println(a[i].replace("{", "").replaceAll("\"", "").replace("server:", "").replace("product:", "").replace("}", ""));
        }
    }
}