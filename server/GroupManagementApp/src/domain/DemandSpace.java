package domain;

public class DemandSpace {
	// top right corner
	private Double x1;
	private Double y1;
	// bottom left corner
	private Double x2;
	private Double y2;
	// empty constructor
	public DemandSpace() {
		
	}
	// construct demand space
	public DemandSpace(Double x1, Double y1, Double x2, Double y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Double getX1() {
		return x1;
	}
	public void setX1(Double x1) {
		this.x1 = x1;
	}
	public Double getY1() {
		return y1;
	}
	public void setY1(Double y1) {
		this.y1 = y1;
	}
	public Double getX2() {
		return x2;
	}
	public void setX2(Double x2) {
		this.x2 = x2;
	}
	public Double getY2() {
		return y2;
	}
	public void setY2(Double y2) {
		this.y2 = y2;
	}
}
