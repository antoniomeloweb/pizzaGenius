package com.util;

import java.util.Random;

public final class Util {
	public static int randomize(int min, int max){
	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}
