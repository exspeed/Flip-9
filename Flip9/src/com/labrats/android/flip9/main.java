package com.labrats.android.flip9;

import java.util.ArrayList;
import java.util.Queue;

public class main {

	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("Please provide an argument");
			System.exit(0);
		}
		
		int start = Integer.parseInt(args[1]);
		//Why is it >= 512 if the range is between 1 and 512? 
		if(start >= 512 || start < 1){
			System.out.println("Argument must be between 1 - 512");
			System.exit(0);
		}
		
		Queue<Node> gamestate = null;
		Node first = new Node(start);
		gamestate.add(first);

		ArrayList<Integer> shortest = findBFS(gamestate);
		
		System.out.println("Quickest Solution");
		for(int move: shortest){
			System.out.println(move + " ");
		}
		//Didn't do the part you commented out in the cheat.txt file 
	}
	
	//Copied form FlipNineFragment.java
	public int getBitmask(int num) {
		switch (num) {
		case 0:
			return 11;
		case 1:
			return 23;
		case 2:
			return 38;
		case 3:
			return 89;
		case 4:
			return 186;
		case 5:
			return 308;
		case 6:
			return 200;
		case 7:
			return 464;
		case 8:
			return 416;
		default:
			return 0; // error
		}
	}

	
	public ArrayList<Integer> findBFS(Queue<Node> gamestate){
		boolean[] possibleState = new boolean[512];
		for(int i =0; i < 512; i++){
			possibleState[i] = false;
		}
		if(gamestate.isEmpty()){
			System.out.println("Queue is empty ");
		}
		boolean solution = false;
		while(!solution){
			Node removedNode = gamestate.peek();
			int val = removedNode.position;
			
			possibleState[val] = true;
			if(val == 0){
				return removedNode.history;
			}
			
			gamestate.remove();
			for(int i = 1; i <= 9; i++){
				Node node1 = new Node(getBitmask(i) ^ val);
				if(possibleState[node1.position] == false){
					node1.history = removedNode.history;
					node1.history.add(i);
					gamestate.add(node1);
				}
			}
		}
		return null;

}

}
