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

import chatservice.MqttService;
import com.google.gson.Gson;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;

public class BiometricsGuiController implements Initializable, MqttService.IMqttMessageHandler {
    
    @FXML private LineChart temperatureChart;
    @FXML private LineChart heartRateChart;
    @FXML private LineChart accelerationChart;
    @FXML private PieChart mqttChart;
    
    private MqttService chatService;
    private Gson gson = new Gson();
    private SeriesManager manager;
    
    private ArrayList<Station> stations;
    private int index = 0;
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chatService = new MqttService();
        chatService.setMessageHandler(this);
        manager = new SeriesManager(temperatureChart, heartRateChart, accelerationChart, mqttChart);
        stations = new ArrayList<>();
    }
    
    @Override
    public void messageArrived(String channel, String message) {
        BiometricData data = gson.fromJson(message, BiometricData.class);
        if (getStation(data.getName()) == null) {
            System.out.println("New station detected!");
            stations.add(new Station(data.getName(), manager));
        }
        Station station = getStation(data.getName());
        station.addData(data, index);
        index++;
    }
    
    private Station getStation(String name) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(name)) {
                return stations.get(i);
            }
        }
        return null;
    }
}