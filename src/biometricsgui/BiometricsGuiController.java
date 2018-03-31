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

import chatservice.MqttChatService;
import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

public class BiometricsGuiController implements Initializable, MqttChatService.IMqttMessageHandler {
    
    @FXML private LineChart temperatureChart;
    @FXML private LineChart heartRateChart;
    @FXML private LineChart accelerationChart;
    @FXML private PieChart mqttChart;
    
    Series temperature;
    Series heartRate;
    Series xAcceleration;
    Series yAcceleration;
    Series zAcceleration;
    
    int index = 0;
    
    private MqttChatService chatService;
    Gson gson = new Gson();
    SeriesManager manager = new SeriesManager();
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatService = new MqttChatService();
        chatService.setMessageHandler(this);
        
        temperature = manager.createTemperatureSeries("Station 1", temperatureChart);
        heartRate = manager.createTemperatureSeries("Station 1", heartRateChart);
        xAcceleration = manager.createTemperatureSeries("Station 1 X", accelerationChart);
        yAcceleration = manager.createTemperatureSeries("Station 1 Y", accelerationChart);
        zAcceleration = manager.createTemperatureSeries("Station 1 Z", accelerationChart);
    }
    
    @Override
    public void messageArrived(String channel, String message) {
        BiometricData biometricData = gson.fromJson(message, BiometricData.class);
        temperature.getData().add(new Data(index, biometricData.getTemperature()));
        heartRate.getData().add(new Data(index, biometricData.getHeartRate()));
        xAcceleration.getData().add(new Data(index, biometricData.getAcceleration().getXAcceleration()));
        yAcceleration.getData().add(new Data(index, biometricData.getAcceleration().getYAcceleration()));
        zAcceleration.getData().add(new Data(index, biometricData.getAcceleration().getZAcceleration()));
        index++;
    }
}