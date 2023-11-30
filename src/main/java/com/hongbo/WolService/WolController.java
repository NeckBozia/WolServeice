package com.hongbo.WolService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wol")
public class WolController {

    private final WolService wolService = new WolService();

    @GetMapping("/send")
    public String sendWolPacket(@RequestParam(required = false) String mac, @RequestParam(required = false) String ip) {
        try {
            wolService.sendWakeOnLanPacket(mac, ip);
            return "WOL Packet sent successfully!";
        } catch (Exception e) {
            return "Error sending WOL Packet: " + e.getMessage();
        }
    }
}

