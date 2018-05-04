/*
 * The MIT License
 *
 * Copyright (c) 2018 Jonas Meeuws, Jonas Van Dycke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package biometricsgui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart.Series;

public class SeriesManager {
    
    private LineChart temperatureChart;
    private LineChart heartRateChart;
    private LineChart accelerationChart;
    private PieChart mqttChart;
    
    SeriesManager(LineChart temperatureChart, LineChart heartRateChart, LineChart accelerationChart, PieChart mqttChart) {
        this.temperatureChart = temperatureChart;
        this.heartRateChart = heartRateChart;
        this.accelerationChart = accelerationChart;
        this.mqttChart = mqttChart;
    }
    
    public Series createTemperatureSeries(String name) {
        Series series = new Series();
        series.setName(name);
        temperatureChart.getData().add(series);
        return series;
    }
    public Series createHeartRateSeries(String name) {
        Series series = new Series();
        series.setName(name);
        heartRateChart.getData().add(series);
        return series;
    }
    public Series createAccelerationSeries(String name) {
        Series series = new Series();
        series.setName(name);
        accelerationChart.getData().add(series);
        return series;
    }
    public Data createMqttData(String name) {
        Data data = new Data(name, 0);
        mqttChart.getData().add(data);
        return data;
    }
}