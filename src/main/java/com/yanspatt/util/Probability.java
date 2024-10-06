package com.yanspatt.util;

import java.util.Random;

public class Probability {
    
    public static boolean probability(float value) {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);

        return randomNumber < (value*100);
    }
}
