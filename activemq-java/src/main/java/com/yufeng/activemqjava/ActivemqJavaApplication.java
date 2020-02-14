package com.yufeng.activemqjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class ActivemqJavaApplication {

	public static void main(String[] args) {
//		SpringApplication.run(ActivemqJavaApplication.class, args);

		PriorityQueue<Node> priorityQueue = new PriorityQueue<>((o1, o2) -> Double.valueOf(o1.x).compareTo(Double.valueOf(o2.cost)));
	}


	class Node {

		int x;
		int y;
		double cost;
		Node parent;

		public Node(int x, int y, double cost, Node parent) {
			this.x = x;
			this.y = y;
			this.cost = cost;
			this.parent = parent;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			return sb.toString();
		}


		@Override
		public boolean equals(Object o) {

			if (o == this) {
				return true;
			}

			if (!(o instanceof Node)) {
				return false;
			}

			Node c = (Node) o;
			return c.x == this.x && c.y == this.y;
		}
	}
}
