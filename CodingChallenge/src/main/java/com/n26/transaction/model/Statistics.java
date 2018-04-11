package com.n26.transaction.model;

import org.springframework.stereotype.Repository;

@Repository
public class Statistics {

	private double sum;
	private double average;
	private double max;
	private double min;
	private long count;	
	
	public Statistics(double sum, double average, double max, double min, long count) {
		this.sum = sum;
		this.average = average;
		this.max = max;
		this.min = min;
		this.count = count;
	}
	
	public Statistics() {
		super();
	}	

	public double getSum() {
		return sum;
	}
	
	public void setSum(double sum) {
		this.sum = sum;
	}
	
	public double getAverage() {
		return average;
	}
	
	public void setAverage(double average) {
		this.average = average;
	}
	
	public double getMax() {
		return max;
	}
	
	public void setMax(double max) {
		this.max = max;
	}
	
	public double getMin() {
		return min;
	}
	
	public void setMin(double min) {
		this.min = min;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}
	
}
