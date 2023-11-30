package com.hongbo.WolService;

import java.net.*;

public class WolService {

    private static final String BROADCAST_IP = "10.1.88.255"; // 广播地址
    private static final String DEFAULT_MAC = "e8:9c:25:97:77:64"; // 默认MAC地址

    public void sendWakeOnLanPacket(String macAddress, String ip) throws Exception {
        if (macAddress == null || macAddress.isEmpty()) {
            macAddress = DEFAULT_MAC; // 如果没有提供MAC地址，使用默认地址
        }
        if (ip == null || ip.isEmpty()) {
            ip = BROADCAST_IP; // 如果没有提供MAC地址，使用默认地址
        }

        byte[] macBytes = getMacBytes(macAddress);
        byte[] bytes = new byte[6 + 16 * macBytes.length];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }
        for (int i = 6; i < bytes.length; i += macBytes.length) {
            System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
        }

        InetAddress address = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.send(packet);
        }
    }

    private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
