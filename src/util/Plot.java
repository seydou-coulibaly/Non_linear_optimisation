package util;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Plot {

	/**
	 * 
	 * @arg title - Title
	 * @arg xasis - xasis label
	 * @arg yaxis - y-axis Label
	 */
	public Plot(String name, String xaxis, String yaxis, String filename, double[] x, double[] y) {
		XYSeries series=new XYSeries("XYGraph");
		assert(x.length==y.length);
		for (int i=0; i<x.length; i++)
			series.add(x[i],y[i]);
		// Add the series to your data set
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		// Generate the graph
		JFreeChart chart = ChartFactory.createXYLineChart(
				name, xaxis, yaxis, dataset,
				// Dataset
				PlotOrientation.VERTICAL, // Plot Orientation
				true,
				// Show Legend
				true,
				// Use tooltips
				false
				// Configure chart to generate URLs?
				);
		try {
			ChartUtilities.saveChartAsJPEG(new File(filename), chart, 500, 300);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}

	}
}
