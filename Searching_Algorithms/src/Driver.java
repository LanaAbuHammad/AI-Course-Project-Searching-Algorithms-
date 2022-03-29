import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Driver {

	public static Node nodes[];
	public static int flag = 0;
	public static int depth_2 = 0;
	public static double path_c = 0;
	private static boolean goalFound = false;
	public static ArrayList<Node> goals = new ArrayList<>();
	public static ArrayList<Node> temp = new ArrayList<>();
	public static ArrayList<Node> temp2 = new ArrayList<>();
	public static ArrayList<Node> GoalPath = new ArrayList<>();
	public static ArrayList<Node> visitedN = new ArrayList<>();
	public static ArrayList<ArrayList<Node>> p_detailed = new ArrayList<>();

	public static void main(String[] args) {

		nodes = new Node[20];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node();
			nodes[i].setId(i);
		}
		try {
			readFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			readCityFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			readCityInfoFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(int i=0; i<nodes.length; i++) {
//			for(int j=0;j<nodes[i].successors.size();j++){
//			  System.out.print(nodes[i].successors.get(j).goal_n.getId()+" ");	
//			}
//		
//			 System.out.print("\n");
//		}

		// UCS(nodes[0], nodes[8]);
//
//		List<Node> path = printPath(nodes[4]);
//		for (int i = 0; i < path.size(); i++)
//			System.out.println("Path = " + path.get(i).getId());
//
//		System.out.println("cost = " + nodes[4].getPathCost());
//
		goals.add(nodes[11]);
//		goals.add(nodes[9]);
//		goals.add(nodes[4]);
//		goals.add(nodes[2]);
//		goals.add(nodes[8]);
//		goals.add(nodes[7]);
//		goals.add(nodes[3]);
		SetGoalFlag();
		//BFS(nodes[0], goals);
		
	DFS2(nodes[0], goals);
	//printt();
		// System.out.println("path cost : "+path_c+"\n");

		// IDS(goals);
		// printFinalPathForIDS();
		// printVisitedNodeForIDS();
		int vertexCount = p_detailed.size();
		for (int i = 0; i < vertexCount; i++) {
			int edgeCount = p_detailed.get(i).size();
			System.out.println("At depth : " + i + "\n");
			for (int j = 0; j < edgeCount; j++) {
				Node n = p_detailed.get(i).get(j);
				System.out.print(n.getId() + " ");
			}
			System.out.print("\n\n");
		}

		List<Node> path= printPath(nodes[11]);
		for (int i = 0; i < path.size(); i++)
			System.out.println("Path = " + path.get(i).getId());

	}

	public static void readFile() throws Exception {
		Scanner scane = new Scanner(new BufferedReader(new FileReader("graph.txt")));
		int rows = 20;
		int columns = 20;
		int[][] city_graph = new int[rows][columns];
		while (scane.hasNextLine()) {
			for (int i = 0; i < city_graph.length; i++) {
				String[] line = scane.nextLine().trim().split(" ");
				for (int j = 0; j < line.length; j++) {

					city_graph[i][j] = Integer.parseInt(line[j]);
				}
			}
		}
		scane.close();
	}

	public static void readCityFile() throws Exception {
		Scanner scane = new Scanner(new BufferedReader(new FileReader("City_name.txt")));

		while (scane.hasNextLine()) {
			for (int i = 0; i < 20; i++) {
				String line = scane.nextLine().trim();
				nodes[i].setCity(line);
			}
		}

		scane.close();
	}

	public static void readCityInfoFile() throws Exception {
		Scanner scane = new Scanner(new BufferedReader(new FileReader("City_info.txt")));
		int rows = 85;
		int columns = 6;
		double info[] = new double[6];
		while (scane.hasNextLine()) {
			for (int i = 0; i < rows; i++) {
				String[] line = scane.nextLine().trim().split(" ");
				for (int j = 0; j < columns; j++) {
					info[j] = Double.parseDouble(line[j]);
				}
				nodes[(int) info[0]].successors.add(new Edge(nodes[(int) info[1]], info[2], info[3], info[4], info[5]));
			}
		}
		scane.close();
	}

	public static void setedges(int Parent_id, int successor_id) {

		nodes[Parent_id].successors.add(new Edge(nodes[successor_id], 0, 0, 0, 0));// 0 is the distance *(needs to be
																					// read from another file)

	}

	public static void IDS(ArrayList<Node> goals) {

		int depth = 0;
		depth_2 = 0;
		for (int i = 0; i < goals.size(); i++) {
			temp.add(goals.get(i));
		}
		while (!goals.isEmpty()) {
			goalFound = false;
			if (!GoalPath.isEmpty())
				p_detailed.add(new ArrayList<Node>((ArrayList<Node>) GoalPath.clone()));
			// System.out.println("-At Depth " + "[" + depth + "]");
			clearVisitedFlags();
			GoalPath.clear();
			System.out.println("At depth " + (depth_2 + 1) + "\n");
			DFS(nodes[0], goals, temp, depth_2++); // nodes[0] is the start node (you can change it)
			System.out.println("\n");

		}
		p_detailed.add(new ArrayList<Node>((ArrayList<Node>) GoalPath.clone()));
		clearVisitedFlags();
	}

	public static void clearVisitedFlags() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setVisited(false);
		}
	}

	public static void SetGoalFlag() {
		for (int i = 0; i < nodes.length; i++) {
			if (goals.contains(nodes[i])) {

				nodes[i].setIsGoal(true);

			}
		}
	}

	public static void clearGoalFlags() {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setIsGoal(false);
		}
	}

	public static Node DFS2(Node start, ArrayList<Node> goals) {

		System.out.print(start.getId() + " ");
		GoalPath.add(start);
		visitedN.add(start);
		if (goals.contains(start)) {

			// System.out.println( start.getId());
			nodes[start.getId()].setPathFromStart(GoalPath);
			nodes[start.getId()].setVisitedNodes(visitedN);
			// System.out.println("**Goal " + start.getId() + " was found");
			goalFound = true;
			goals.remove(start);
			// GoalPath.clear();

		}

		start.setVisited(true);

		for (int i = 0; i < start.successors.size(); i++) {
			Node n = start.successors.get(i).goal_n;
			// System.out.println(n.getId() + "+" +start.getId()+" d= "+ depth);
			if (n != null && !n.isVisited() && !goals.isEmpty()) {

				n.setParent(start);
				DFS2(n, goals);
			}

		}

		return null;

	}

	public static Node DFS(Node start, ArrayList<Node> goals, ArrayList<Node> temp, int depth) {

		System.out.print(start.getId() + " ");
		GoalPath.add(start);
		visitedN.add(start);

		if (goals.contains(start)) {
			// System.out.println( start.getId());
			nodes[start.getId()].setPathFromStart(GoalPath);
			nodes[start.getId()].setVisitedNodes(visitedN);
			// System.out.println("**Goal " + start.getId() + " was found");
			goalFound = true;
			goals.remove(start);
			// GoalPath.clear();

		}

		if (depth >= 0) {

			start.setVisited(true);
			if (goals.contains(start)) {
				goalFound = true;
				goals.remove(start);
				// System.out.println("**Goal " + start.getId() + " was found");
			}
			for (int i = 0; i < start.successors.size(); i++) {
				Node n = start.successors.get(i).goal_n;
				// System.out.println(n.getId() + "+" +start.getId()+" d= "+ depth);
				if (n != null && !n.isVisited() && !goals.isEmpty()) {
					path_c = +start.successors.get(i).edge_cost;
					n.setParent(start);
					DFS(n, goals, temp, depth - 1);
				}

			}

		}

		return null;

	}

	public static void UCS(Node start, Node goal) {

		start.setPathCost(0);
		PriorityQueue<Node> fringe = new PriorityQueue<Node>(20, new Comparator<Node>() {

			public int compare(Node n1, Node n2) {
				if (n1.getPathCost() > n2.getPathCost()) {
					return 1;
				}

				else if (n1.getPathCost() < n2.getPathCost()) {
					return -1;
				}

				else {
					return 0;
				}
			}
		}

		);

		fringe.add(start);
		// System.out.println(nodes[0].successors.get(1).edge_cost);
		Set<Node> expand = new HashSet<Node>();
		boolean goalFound = false;

		do {

			Node inCity = fringe.poll();
			expand.add(inCity);

			if (inCity.getId() == goal.getId()) {
				goalFound = true;

			}

			for (Edge n : inCity.successors) {
				Node successor = n.goal_n;
				double e_cost = n.edge_cost;

				if (!expand.contains(successor) && !fringe.contains(successor)) {
					successor.setPathCost(inCity.getPathCost() + e_cost);
					successor.setParent(inCity);
					fringe.add(successor);
					System.out.println(fringe);
					p_detailed.add(new ArrayList(fringe));
					System.out.println();

				}

				else if ((fringe.contains(successor)) && (successor.getPathCost() > (inCity.getPathCost() + e_cost))) {
					successor.setParent(inCity);
					successor.setPathCost(inCity.getPathCost() + e_cost);
					fringe.remove(successor);
					fringe.add(successor);

				}

			}

		} while ((goalFound == false));

	}

	public static void BFS(Node start, ArrayList<Node> goals) {

		LinkedList<Node> queue = new LinkedList<Node>();
		start.setVisited(true);
		queue.add(start);

		while (queue.size() != 0) {

			if (goals.contains(start)) {
				goals.remove(start);

			}
			if (goals.isEmpty()) {
				nodes[start.getId()].setVisitedNodes(visitedN);
				break;
			}
			start = queue.poll();
			System.out.print(start.getId() + " ");
			visitedN.add(start);
			for (int i = 0; i < start.successors.size(); i++) {
				Node n = start.successors.get(i).goal_n;
				if (n != null && !n.isVisited() && !goals.isEmpty()) {
					
					n.setParent(start);
					n.setVisited(true);
					queue.add(n);
				}

			}
		}

	}

	public static List<Node> printPath(Node goal) {
		List<Node> path = new ArrayList<Node>();
		for (Node Node2 = goal; Node2 != null; Node2 = Node2.getParent()) {
			path.add(Node2);
		}

		Collections.reverse(path);

		return path;

	}

	public static void printFinalPathForIDS() {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].PathFromStart != null) {

				System.out.println("Visited nodes to node " + "[ " + nodes[i].getId() + " ]");
				for (int j = 0; j < nodes[i].getPathFromStart().size(); j++) {
					System.out.println("Node " + nodes[i].getPathFromStart().get(j).getId());
					if (j + 1 < nodes[i].getPathFromStart().size()) {
						for (int k = 0; k < nodes[i].getPathFromStart().get(j).successors.size(); k++) {
							if (nodes[i].getPathFromStart().get(j + 1)
									.getId() == nodes[i].getPathFromStart().get(j).successors.get(k).goal_n.getId()) {
								System.out.println(nodes[i].getPathFromStart().get(j).getId() + " "
										+ nodes[i].getPathFromStart().get(j + 1).getId());
								path_c += nodes[i].getPathFromStart().get(j).successors.get(k).edge_cost;

							}
						}

					}
				}
				System.out.println("--------------------\n");
			}
			// nodes[i].PathFromStart = null;
		}

	}

	public static void printt() {
		
		for (int j = 0; j < visitedN.size(); j++) {
			if(j+1<visitedN.size()) {
			for (int k = 0; k < visitedN.get(j).successors.size(); k++) {

				if (visitedN.get(j + 1).getId() == visitedN.get(j).successors.get(k).goal_n.getId()) {
					System.out.println(visitedN.get(j).getId() + " " + visitedN.get(j + 1).getId());
					path_c += visitedN.get(j).successors.get(k).edge_cost;

				}
			}
		}
		
		}

	}

	public static void printVisitedNodeForIDS() {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].visitedNodes != null) {
				System.out.println("Visited nodes to node " + "[ " + nodes[i].getId() + " ]");
				for (int j = 0; j < nodes[i].getVisitedNodes().size(); j++) {

					System.out.println("Node " + nodes[i].getVisitedNodes().get(j).getId());

				}
				System.out.println("--------------------\n");
			}
			nodes[i].visitedNodes = null;
		}

	}
}
