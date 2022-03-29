
import java.util.ArrayList;

public class Node {
	private int id;
	private String City;
	private boolean Visited;
	private Node parent;
	public ArrayList<Edge> successors;
	public ArrayList<Node> PathFromStart;
	public ArrayList<Node> visitedNodes;
	
	public ArrayList<Node> getVisitedNodes() {
		return visitedNodes;
	}

	public void setVisitedNodes(ArrayList<Node> visitedNodes_2) {
		this.visitedNodes = (ArrayList<Node>)visitedNodes_2.clone();
	}
	private boolean IsGoal;
	private double PathCost;

	public Node() {

		this.City = null;
		this.Visited = false;
		this.parent = null;
		this.IsGoal = false;
		this.PathFromStart = null;
		this.visitedNodes = null;
		this.successors = new  ArrayList<Edge>();
		this.PathCost = 0;

	}

	public ArrayList<Node> getPathFromStart() {
		return PathFromStart;
	}

	public void setPathFromStart(ArrayList<Node> pathFromStart2) {
		this.PathFromStart = (ArrayList<Node>)pathFromStart2.clone();
	}

	public boolean isIsGoal() {
		return IsGoal;
	}

	public void setIsGoal(boolean isGoal) {
		IsGoal = isGoal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public boolean isVisited() {
		return Visited;
	}

	public void setVisited(boolean visited) {
		Visited = visited;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node p) {

		this.parent=p;
		
	}

	public double getPathCost() {
		return PathCost;
	}

	public void setPathCost(double pathCost) {
		PathCost = pathCost;
	}
	public String toString(){
		return String.valueOf(this.getId());
	}

}
