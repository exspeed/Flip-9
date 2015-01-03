package com.labrats.android.flip9;

import java.util.ArrayList;
import java.util.LinkedList;

import android.util.Log;

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

		LinkedList<Node> gamestate = new LinkedList<Node>();
		Node first = new Node(currentState);
		gamestate.add(first);

		return findBFS(gamestate);
	}


	private ArrayList<Integer> findBFS(LinkedList<Node> gamestate) {
		boolean[] possibleState = new boolean[512];
		if (gamestate.isEmpty()) {
			Log.e("Cheat", "Empty gamestate");
			return null;
		}
		boolean solution = false;
		while (!solution) {
			Node removedNode = gamestate.remove();
			int val = removedNode.position;
			possibleState[val] = true;
			if (val == 0) {
				return removedNode.history;
			}

			for (int i = 0; i < 9; i++) {
				int newState = FlipData.getBitmask(i) ^ val;
				if (possibleState[newState] == false) {
					Node node1 = new Node(newState);
					node1.history = new ArrayList<Integer>(removedNode.history);
					node1.history.add(i + 1);
					gamestate.addLast(node1);
				}
			}
		}
		return null;

	}
}
