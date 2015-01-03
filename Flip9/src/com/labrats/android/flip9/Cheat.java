package com.labrats.android.flip9;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;


public class Cheat {
	
	private static final int[] masks = { 11, 23, 38, 89, 186, 308, 200, 464,
			416 };

	private static boolean[] visited = new boolean[512];
	private static int[] parent =new int[512], button= new int[512];
	private static ArrayList<Integer> ans= new ArrayList<Integer>();


	/**
	 * returns the button presses
	 */
	public static ArrayList<Integer> getCheat(int c) {
		Arrays.fill(visited, false);
		Arrays.fill(parent, -1);
		Arrays.fill(button, -1);
		ans.clear();
		bfs(c);
		int t=0;
		while(t!=-1){
			ans.add(button[t] -1);
			t=parent[t];
		}
		
		ans.remove(ans.size() -1);
		
		return ans;
	}

	private static void bfs(int c) {
		ArrayDeque<Integer> q=new ArrayDeque<Integer>(10);
		visited[c] = true;
		q.add(c);
		while (!q.isEmpty()) {
			int t = q.poll();
			if (t == 0) {
				return;
			}
			int n=9;
			for(int i:masks){
				if(!visited[t^i]){
					visited[t^i]=true;
					parent[t^i]=t;
					button[t^i]=n;
					q.add(t^i);
				}
				n--;
			}
		}
	}
}