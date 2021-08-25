package ru.gsa.biointerfaceController_standalone.connection;

import com.fazecast.jSerialComm.SerialPort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectionFactory {
    private static Set<ConnectionToDevice> connectionToDevice = new LinkedHashSet<>();

    private static Stream<SerialPort> getSerialPortsWishDevises() {
        return Arrays.stream(SerialPort.getCommPorts())
                .filter(o -> "BiointerfaceController".equals(o.getPortDescription()));
    }

    public static void scanningSerialPort() {
        connectionToDevice.forEach(ConnectionToDevice::disconnect);
        connectionToDevice.clear();
        getSerialPortsWishDevises()
                .forEach(o -> connectionToDevice.add(new ConnectionToDevice(o)));
    }

    public static List<String> getSerialPortNames() {
        connectionToDevice = connectionToDevice.stream()
                .filter(ConnectionToDevice::isAvailable)
                .collect(Collectors.toSet());

        return connectionToDevice.stream()
                .map(ConnectionToDevice::getSerialPort)
                .map(SerialPort::getSystemPortName)
                .sorted()
                .collect(Collectors.toList());
    }

    public static Connection getInstance(String serialPortName) {
        return connectionToDevice.stream()
                .peek(o -> {
                    if (!o.getSerialPort().getSystemPortName().equals(serialPortName))
                        o.disconnect();
                })
                .filter(o -> o.getSerialPort().getSystemPortName().equals(serialPortName))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
