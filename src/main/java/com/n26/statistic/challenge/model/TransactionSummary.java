package com.n26.statistic.challenge.model;

public class TransactionSummary {

	private Double sum;

	private Double avg;

	private Double max;

	private Double min;

	private Long count;

	public TransactionSummary(Double sum, Double avg, Double max, Double min, Long count) {
		super();
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TransactionStatisticInformation [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min
				+ ", count=" + count + "]";
	}

}
