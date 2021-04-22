package com.jack.test;

import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) throws Exception {
        int i = 10;
        Integer integer1 = new Integer(i);
        Integer integer = new Integer(10);
        System.out.println(integer.equals(integer1));
    }

	/**
	 * @param str
	 * @return
	 */
	public static int getValue(int str) {
		try {
			int n = str * str;
			return n;
		} catch (Exception e) {

		} finally {
			if (str == 2) {
				return 8;
			}
		}
		return 0;
	}
}