package com.magnesify;

import static com.magnesify.senders.Licences1.getResponse;
import static com.magnesify.senders.Licences3.getTeamResponse;

public class Main {

    public static void main(String[] args) {
        // 0 ip
        // 1 mail
        // 2 anahtar
        // 3 ürün
        /*
                for(int i = 0; i < a.length; i++) {
            System.out.println(a[i].replace("{", "").replaceAll("\"", "").replace("key:", "").replace("email:", "").replace("server:", "").replace("product:", "").replace("}", ""));
            if(a[0].replace("{", "").replaceAll("\"", "").replace("server:", "").replace("product:", "").replace("}", "").equalsIgnoreCase("localhost:25565")) {
                System.out.println("Lisans geçerli");
            } else {
                System.out.println("Lisans geçersiz");
            }
        }
         */

        // 0 status
        String[] a = getTeamResponse().split(",");
        for(int i = 0; i < a.length; i++) {
            System.out.println(a[0].replace("{", "").replaceAll("\"", "").replace("status:", "").replace("key:", "").replace("email:", "").replace("server:", "").replace("product:", "").replace("}", ""));
        }
    }
}