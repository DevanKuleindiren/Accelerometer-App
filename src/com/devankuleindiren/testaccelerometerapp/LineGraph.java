package com.devankuleindiren.testaccelerometerapp;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class LineGraph {
	
	private GraphicalView view;
	
	private TimeSeries datasetX = new TimeSeries("XAcceleration");
	private TimeSeries datasetY = new TimeSeries("YAcceleration");
	private TimeSeries datasetZ = new TimeSeries("ZAcceleration");
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	
	private XYSeriesRenderer rendererX = new XYSeriesRenderer();
	private XYSeriesRenderer rendererY = new XYSeriesRenderer();
	private XYSeriesRenderer rendererZ = new XYSeriesRenderer();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	
	
	public LineGraph() {
		mDataset.addSeries(datasetX);
		mDataset.addSeries(datasetY);
		mDataset.addSeries(datasetZ);
		
		rendererX.setColor(Color.RED);
		rendererX.setHighlighted(true);
		rendererX.setPointStyle(PointStyle.SQUARE);
		rendererX.setFillPoints(true);
		
		rendererY.setColor(Color.BLUE);
		rendererY.setHighlighted(true);
		rendererY.setPointStyle(PointStyle.SQUARE);
		rendererY.setFillPoints(true);
		
		rendererZ.setColor(Color.GREEN);
		rendererZ.setHighlighted(true);
		rendererZ.setPointStyle(PointStyle.SQUARE);
		rendererZ.setFillPoints(true);
		
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setXTitle("Time");
		mRenderer.setYTitle("Acceleration");
		mRenderer.addSeriesRenderer(rendererX);
		mRenderer.addSeriesRenderer(rendererY);
		mRenderer.addSeriesRenderer(rendererZ);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		
	}
	
	public GraphicalView getView(Context context) {
		view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}
	
	public void addNewXPoint(Point p) {
		datasetX.add(p.getX(), p.getY());
	}
	
	public void addNewYPoint(Point p) {
		datasetY.add(p.getX(), p.getY());
	}
	
	public void addNewZPoint(Point p) {
		datasetZ.add(p.getX(), p.getY());
	}
}
