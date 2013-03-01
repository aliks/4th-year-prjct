package com.android.alex.services.domain;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class DemandSpace implements KvmSerializable{
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
	@Override
	public Object getProperty(int arg0) {
        switch(arg0)
        {
        case 0:
            return x1;
        case 1:
            return y1;
        case 2:
            return x2;
        case 3:
            return y2;
        }
        return null;
	}
	@Override
	public int getPropertyCount() {
		return 4;
	}
	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch(index)
        {
        case 0:
            info.type = Double.class;
            info.name = "x1";
            break;
        case 1:
            info.type = Double.class;
            info.name = "y1";
            break;
        case 2:
            info.type = Double.class;
            info.name = "x2";
            break;
        case 3:
            info.type = Double.class;  
            info.name = "y2";
            break;
        default:break;
        }		
	}
	@Override
	public void setProperty(int index, Object value) {
        switch(index)
        {
        case 0:
        	x1 = (Double) value;
            break;
        case 1:
        	y1 = (Double) value;
            break;
        case 2:
            x2 = (Double) value;
            break;
        case 3:
            y2 = (Double) value;
            break;    
        default:break;
        }		
	}
}
