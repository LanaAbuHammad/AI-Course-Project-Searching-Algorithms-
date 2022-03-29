

public class Edge{
	public double edge_cost;
	public double sld;
	public double t;
	public double twt;
	public Node goal_n;

	public Edge(Node goal2, double edge_cost2,double sld2,double t2, double twt2){
		edge_cost = edge_cost2;
		goal_n = goal2;
		sld=sld2;
		t=t2;
		twt=twt2;

	}
}