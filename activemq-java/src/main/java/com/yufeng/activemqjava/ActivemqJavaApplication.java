package com.yufeng.activemqjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class ActivemqJavaApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ActivemqJavaApplication.class, args);

		int[] numbers = new int[3];
		Arrays.stream(numbers).mapToObj(i -> String.valueOf(i)).collect(Collectors.joining(", "));
//		IntStream.range(0, numbers.length).forEach(i -> numbers[i] = random.nextInt(10000));

		int a = 10000;
		int b = 9000;
		long c = a * b;
		System.out.println(c);
	}

}
