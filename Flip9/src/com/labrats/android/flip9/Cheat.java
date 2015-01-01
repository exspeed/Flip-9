package com.labrats.android.flip9;
import java.util.ArrayList;
import java.util.LinkedList;

public class Cheat {
	// Node class from cheat.txt
	public class Node {
		public int position;

		ArrayList<Integer> history;

		public Node(int p) {
			this.position = p;
			history = new ArrayList<Integer>();
		}
	}

	public ArrayList<Integer> getCheat(int currentState) {

		// Why is it >= 512 if the range is between 1 and 512?
		if (currentState > 512 || currentState < 1) {
			// Log.e("Cheat", "Must be between 1 - 511");
		}

		LinkedList<Node> gamestate = new LinkedList<Node>();
		Node first = new Node(currentState);
		gamestate.add(first);

		return findBFS(gamestate);
	}

	// Copied form FlipNineFragment.java
	private static int getBitmask(int num) {
		switch (num) {
		case 1:
			return 11;
		case 2:
			return 23;
		case 3:
			return 38;
		case 4:
			return 89;
		case 5:
			return 186;
		case 6:
			return 308;
		case 7:
			return 200;
		case 8:
			return 464;
		case 9:
			return 416;
		default:
			return -1; // error
		}
	}

	private ArrayList<Integer> findBFS(LinkedList<Node> gamestate) {
		boolean[] possibleState = new boolean[512];
		if (gamestate.isEmpty()) {
			System.out.println("Nothing in here");
			return null;
			// Log.e("Cheat", "Empty gamestate");
		}
		boolean solution = false;
		while (!solution) {
			Node removedNode = gamestate.remove();
			int val = removedNode.position;
			// System.out.println(val);
			possibleState[val] = true;
			if (val == 0) {
				return removedNode.history;
			}

			// gamestate.remove();

			for (int i = 1; i <= 9; i++) {
				Node node1 = new Node(getBitmask(i) ^ val);
				if (possibleState[node1.position] == false) {
					node1.history = new ArrayList<Integer>(removedNode.history);
					node1.history.add(i);
					gamestate.addLast(node1);
				}
			}
		}
		return null;

	}

}
