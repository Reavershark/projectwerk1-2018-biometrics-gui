/*
 * The MIT License
 *
 * Copyright 2018 jonas.
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

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class Station {

    private XYChart.Series temperature;
    private XYChart.Series heartRate;
    private XYChart.Series xAcceleration;
    private XYChart.Series yAcceleration;
    private XYChart.Series zAcceleration;
    private PieChart.Data mqttData;
    
    private String name;
    
    public Station(String name, SeriesManager manager) {
        this.name = name;
        
        temperature = manager.createTemperatureSeries(name);
        heartRate = manager.createHeartRateSeries(name);
        xAcceleration = manager.createAccelerationSeries(name + " x");
        yAcceleration = manager.createAccelerationSeries(name + " y");
        zAcceleration = manager.createAccelerationSeries(name + " z");
        mqttData = manager.createMqttData(name);
    }

    public String getName() {
        return name;
    }
    
    public void addData(BiometricData data, int x) {
        if (!data.getName().equals(name)) {
            return;
        }
        temperature.getData().add(new Data(x, data.getTemperature()));
        xAcceleration.getData().add(new Data(x, data.getAcceleration().getXAcceleration()));
        yAcceleration.getData().add(new Data(x, data.getAcceleration().getYAcceleration()));
        zAcceleration.getData().add(new Data(x, data.getAcceleration().getYAcceleration()));
        heartRate.getData().add(new Data(x, data.getHeartRate()));
        mqttData.setPieValue(mqttData.getPieValue() + 1);
    }
}
