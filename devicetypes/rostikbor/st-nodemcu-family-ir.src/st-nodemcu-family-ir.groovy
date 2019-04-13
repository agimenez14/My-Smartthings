metadata {
	definition (name: "ST_NodeMCU Family IR", namespace: "Rostikbor", author: "Rostikbor") {
		capability "Configuration"
		capability "Switch"
		capability "Polling"
        capability "Refresh"
        capability "Actuator"
        capability "Music Player"
		capability "Motion Sensor"
		capability "Sensor"
        
		command "switch1on"
		command "switch2on"
		command "switch3on"
		command "switch4on"
		command "switch5on"
		command "switch6on"
		command "switch7on"
		command "switch8on"
		command "switch9on"
		command "switch10on"
		command "switch11on"
		command "switch12on"
		command "switch13on"
		command "switch14on"
		command "switch15on"
		command "switch16on"
		command "switch17on"
		command "switch18on"
		command "switch19on"
		command "switch20on"
		command "switch21on"
		command "switch22on"
		command "switch23on"
		command "switch24on"
		command "switch25on"
		command "switch26on"
		command "switch27on"
		command "switch28on"
		command "switch29on"
		command "switch30on"
		command "switch31on"
		command "switch32on"
		command "switch33on"
		command "switch34on"
		command "switch35on"
		command "switch36on"
        
		command "switch50on"
        command "switch51on"
        command "switch51off"
        command "switch52on"
        command "switch52off"
        
        command "sendEthernet", ["string"]
	}

    simulator {
 
    }

    // Preferences
	preferences {
		input "ip", "text", title: "Arduino IP Address", description: "ip", required: true, displayDuringSetup: true
		input "port", "text", title: "Arduino Port", description: "port", required: true, displayDuringSetup: true
		input "mac", "text", title: "Arduino MAC Addr", description: "mac", required: true, displayDuringSetup: true
		input "numButtons", "number", title: "Number of Buttons", description: "Number of Buttons to be implemented", defaultValue: 0, required: true, displayDuringSetup: true
		input "ismotionReset", "bool", title: "Offline check?", required: true, displayDuringSetup: false, defaultValue: false
		input "motionReset", "number", title: "Number of seconds before offline.", description: "", value:120, displayDuringSetup: false
}

	// Tile Definitions
	tiles(scale: 2) {
		standardTile("label1", "device.label1", decoration: "flat", width: 4, height: 1, canChangeIcon: true) {
			state "default", label: 'Receiver', icon: "st.Electronics.electronics16", backgroundColor: "#ffffff"
		}
        standardTile("switch1", "device.switch1", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'on', action: "switch1on", backgroundColor: "#79b821"
		}
        standardTile("switch2", "device.switch2", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'off', action: "switch2on", backgroundColor: "#ff0000"
		}
        standardTile("switch3", "device.switch3", decoration: "flat", width: 1, height: 2, canChangeIcon: true) {
			state "default", label: '', action: "switch3on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-up"
		}
        standardTile("switch4", "device.switch4", decoration: "flat", width: 1, height: 2, canChangeIcon: true) {
			state "default", label: '', action: "switch4on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-down"
		}
        standardTile("switch5", "device.switch5", decoration: "flat", width: 1, height: 2, canChangeIcon: true) {
			state "default", label: 'mute', action: "switch5on", backgroundColor: "#ffffff", icon:"st.custom.sonos.muted"
		}
        standardTile("switch6", "device.switch6", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'Cable/Sat', action: "switch6on", backgroundColor: "#ffffff"
		}
        standardTile("switch7", "device.switch7", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'DVD', action: "switch7on", backgroundColor: "#ffffff"
		}
        standardTile("switch8", "device.switch8", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'TV', action: "switch8on", backgroundColor: "#ffffff"
		}
        standardTile("switch9", "device.switch9", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Game', action: "switch9on", backgroundColor: "#ffffff"
		}
        standardTile("switch10", "device.switch10", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'Media', action: "switch10on", backgroundColor: "#ffffff"
		}
        standardTile("switch11", "device.switch11", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'AUX', action: "switch11on", backgroundColor: "#ffffff"
		}
		standardTile("connected", "device.connected", decoration: "flat") {
			state "on", icon:"st.harmony.harmony-hub-icon"
			state "offline", label: 'offline', action: "refresh.refresh", backgroundColor: "#ff0000"
		}
        standardTile("label2", "device.label2", decoration: "flat", width: 4, height: 1, canChangeIcon: true) {
			state "default", label: 'Apple TV', icon: "st.Electronics.electronics9", backgroundColor: "#ffffff"
		}
        standardTile("switch12", "device.switch12", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Menu', action: "switch12on", backgroundColor: "#ffffff", icon:"st.vents.vent"
		}
        standardTile("switch13", "device.switch13", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Pause', action: "switch13on", backgroundColor: "#ffffff", icon:"st.sonos.pause-icon"
		}
        standardTile("switch14", "device.switch14", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: '', action: "switch14on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-up"
		}
        standardTile("switch15", "device.switch15", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: '', action: "switch15on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-down"
		}
        standardTile("switch16", "device.switch16", decoration: "flat", width: 1, height: 2, canChangeIcon: true) {
			state "default", label: '', action: "switch16on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-left"
		}
        standardTile("switch17", "device.switch17", decoration: "flat", width: 1, height: 2, canChangeIcon: true) {
			state "default", label: '', action: "switch17on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-right"
		}
        standardTile("switch18", "device.switch18", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Reboot', action: "switch18on", backgroundColor: "#ffffff", icon: "st.secondary.refresh-icon"
		}
        standardTile("label3", "device.label3", decoration: "flat", width: 4, height: 1, canChangeIcon: true) {
			state "default", label: 'Samsung TV', icon: "st.Electronics.electronics3", backgroundColor: "#ffffff"
		}
        standardTile("switch19", "device.switch19", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'on', action: "switch19on", backgroundColor: "#79b821"
		}
        standardTile("switch36", "device.switch36", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'off', action: "switch36on", backgroundColor: "#ff0000"
		}
        standardTile("switch20", "device.switch20", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'HDMI1', action: "switch20on", backgroundColor: "#ffffff"
		}
        standardTile("switch21", "device.switch21", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'HDMI2', action: "switch21on", backgroundColor: "#ffffff"
		}
        standardTile("switch22", "device.switch22", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'HDMI3', action: "switch22on", backgroundColor: "#ffffff"
		}
        standardTile("switch23", "device.switch23", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'TV', action: "switch2on", backgroundColor: "#ffffff"
		}
        standardTile("switch24", "device.switch24", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: '', action: "switch24on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-up"
		}
        standardTile("switch25", "device.switch25", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: '', action: "switch25on", backgroundColor: "#ffffff", icon:"st.thermostat.thermostat-down"
		}
        standardTile("switch26", "device.switch26", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'mute', action: "switch26on", backgroundColor: "#ffffff", icon:"st.custom.sonos.muted"
		}
        standardTile("switch35", "device.switch35", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: '3D', action: "switch35on", backgroundColor: "#ffffff"
		}
        standardTile("switch27", "device.switch27", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Clean', action: "switch27on", backgroundColor: "#ffffff"
		}
        standardTile("switch28", "device.switch28", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Dock', action: "switch28on", backgroundColor: "#ffffff"
		}
        standardTile("switch29", "device.switch29", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Spot', action: "switch29on", backgroundColor: "#ffffff"
		}
		standardTile("label4", "device.label1", decoration: "flat", width: 4, height: 1, canChangeIcon: true) {
			state "default", label: 'Sony DVD', icon: "st.Electronics.electronics1", backgroundColor: "#ffffff"
		}
        standardTile("switch30", "device.switch30", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'on', action: "switch30on", backgroundColor: "#79b821"
		}
        standardTile("switch31", "device.switch31", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'off', action: "switch31on", backgroundColor: "#ff0000"
		}
		standardTile("label5", "device.label1", decoration: "flat", width: 4, height: 1, canChangeIcon: true) {
			state "default", label: 'Sony BluRay', icon: "st.Electronics.electronics1", backgroundColor: "#ffffff"
		}
        standardTile("switch32", "device.switch32", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'on', action: "switch32on", backgroundColor: "#79b821"
		}
        standardTile("switch33", "device.switch33", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
			state "default", label: 'off', action: "switch33on", backgroundColor: "#ff0000"
		}
        standardTile("label6", "device.label3", decoration: "flat", width: 4, height: 1, canChangeIcon: true) {
			state "default", label: 'LG DVD/VCR', icon: "st.Electronics.electronics4", backgroundColor: "#ffffff"
		}
        standardTile("switch34", "device.switch34", decoration: "flat", width: 2, height: 1, canChangeIcon: true) {
			state "default", label: 'Power', action: "switch34on", backgroundColor: "#79b821"
		}
        valueTile("rssi", "device.rssi", width: 2, height: 2) {
			state("rssi", label:'RSSI ${currentValue}', unit:"",
				backgroundColors:[
                	[value: 0, color: "#000000"],
					[value: -30, color: "#006600"],
					[value: -45, color: "#009900"],
					[value: -60, color: "#99cc00"],
					[value: -70, color: "#ff9900"],
					[value: -90, color: "#ff0000"]
				]
			)
		}
        valueTile("lastCheckin", "device.lastCheckin", decoration: "flat", inactiveLabel: false, width: 4, height: 1) {
			state "default", label:'Last Check-in: ${currentValue}'
		}
		standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
			state "default", label:'Refresh', action: "refresh.refresh", icon: "st.secondary.refresh-icon"
		}
		standardTile("switch50", "device.switch50", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
			state "default", label:'Restart', action: "switch50on", icon: "st.samsung.da.RC_ic_power"
		}
        standardTile("switch51", "device.switch51", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
            state "off", label: 'off', action: "switch51on", backgroundColor: "#ffffff", icon: "st.switches.switch.off"
			state "on", label: 'on', action: "switch51off", backgroundColor: "#00A0DC", icon: "st.switches.switch.on"
		}
        standardTile("switch52", "device.switch52", decoration: "flat", width: 1, height: 1, canChangeIcon: true) {
            state "off", label: 'off', action: "switch52on", backgroundColor: "#ffffff", icon: "st.switches.switch.off"
			state "on", label: 'on', action: "switch52off", backgroundColor: "#00A0DC", icon: "st.switches.switch.on"
		}

        main(["connected"])
        details(["label1","switch5","switch3","switch1","switch2","switch7","switch8","switch6","switch4","switch11","switch9","switch10",
        		 "label2","switch18","switch16","switch14","switch17","switch12","switch15","switch13",
                 "label3","switch19","switch36","switch20","switch21","switch26","switch24","switch22","switch23","switch35","switch25",
                 "label4","switch30","switch31",
                 "label5","switch32","switch33",
                 "label6","switch34",
                 
                 "switch27","switch28","switch29",
                 "rssi","lastCheckin","refresh","switch50","switch51","switch52"])
	}
}

// parse events into attributes
def parse(String description) {
	//log.debug "Parsing '${description}'"
	def msg = parseLanMessage(description)
	def headerString = msg.header
	def bodyString = msg.body
	if (bodyString) {
        log.debug "BodyString: $bodyString"
        
    	def parts = bodyString.split(" ")
    	def name  = parts.length>0?parts[0].trim():null
    	def value = parts.length>1?parts[1].trim():null
		def results = []
		if (name.startsWith("button")) {
        	def pieces = name.split(":")
            def btnName = pieces.length>0?pieces[0].trim():null
            def btnNum = pieces.length>1?pieces[1].trim():null
			//log.debug "In parse:  name = ${name}, value = ${value}, btnName = ${btnName}, btnNum = ${btnNum}"
        	results = createEvent([name: btnName, value: value, data: [buttonNumber: btnNum], descriptionText: "${btnName} ${btnNum} was ${value} ", isStateChange: true, displayed: true])
        }
		else if (name.startsWith("rssi")) {
    		results = createEvent(name: name, value: value, displayed: false)
        }
        else if (name.startsWith("switch50") && value == "on") {
    		results = createEvent(name: name, value: value, descriptionText: "Restart sent")
        }
        else if (name.startsWith("switch50") && value == "off") {
    		results = createEvent(name: name, value: value, descriptionText: "Successfully Restarted")
        }
        else {
    		results = createEvent(name: name, value: value)
		}
		
    	def nowDay = new Date().format("MMM d h:mm a", location.timeZone)
    	sendEvent(name: "lastCheckin", value: nowDay, displayed: false)
        
		log.debug results
        sendEvent(name: "connected", value: "on")
        if (ismotionReset == true) {
            if (settings.motionReset == null || settings.motionReset == "" ) settings.motionReset = 120
            runIn(settings.motionReset, offline)
        }
        return results

	}
}

private getHostAddress() {
    def ip = settings.ip
    def port = settings.port
	log.debug "Using ip: ${ip} and port: ${port} for device: ${device.id}"
    return ip + ":" + port
}

def offline() {
	sendEvent(name: "connected", value: "offline")
}

def sendEthernet(message) {
	log.debug "Executing 'sendEthernet' ${message}"
	new physicalgraph.device.HubAction(
    	method: "POST",
    	path: "/${message}?",
    	headers: [ HOST: "${getHostAddress()}" ]
	)
}

// handle commands

def switch1on() {
	sendEthernet("switch1 on")
}
def switch2on() {
	sendEthernet("switch2 on")
}
def switch3on() {
	sendEthernet("switch3 on")
}
def switch4on() {
	sendEthernet("switch4 on")
}
def switch5on() {
	sendEthernet("switch5 on")
}
def switch6on() {
	sendEthernet("switch6 on")
}
def switch7on() {
	sendEthernet("switch7 on")
}
def switch8on() {
	sendEthernet("switch8 on")
}
def switch9on() {
	sendEthernet("switch9 on")
}
def switch10on() {
	sendEthernet("switch10 on")
}
def switch11on() {
	sendEthernet("switch11 on")
}
def switch12on() {
	sendEthernet("switch12 on")
}
def switch13on() {
	sendEthernet("switch13 on")
}
def switch14on() {
	sendEthernet("switch14 on")
}
def switch15on() {
	sendEthernet("switch15 on")
}
def switch16on() {
	sendEthernet("switch16 on")
}
def switch17on() {
	sendEthernet("switch17 on")
}
def switch18on() {
	sendEthernet("switch18 on")
}
def switch19on() {
	sendEthernet("switch19 on")
}
def switch20on() {
	sendEthernet("switch20 on")
}
def switch21on() {
	sendEthernet("switch21 on")
}
def switch22on() {
	sendEthernet("switch22 on")
}
def switch23on() {
	sendEthernet("switch23 on")
}
def switch24on() {
	sendEthernet("switch24 on")
}
def switch25on() {
	sendEthernet("switch25 on")
}
def switch26on() {
	sendEthernet("switch26 on")
}
def switch27on() {
	sendEthernet("switch27 on")
}
def switch28on() {
	sendEthernet("switch28 on")
}
def switch29on() {
	sendEthernet("switch29 on")
}
def switch30on() {
	sendEthernet("switch30 on")
}
def switch31on() {
	sendEthernet("switch31 on")
}
def switch32on() {
	sendEthernet("switch32 on")
}
def switch33on() {
	sendEthernet("switch33 on")
}
def switch34on() {
	sendEthernet("switch34 on")
}
def switch35on() {
	sendEthernet("switch35 on")
}
def switch36on() {
	sendEthernet("switch36 on")
}

def switch50on() {
    sendEvent(name: "rssi", value: "0", displayed: false)
	sendEthernet("switch50 on")
}

def switch51on() {
	sendEthernet("switch51 on")
}

def switch51off() {
	sendEthernet("switch51 off")
}

def switch52on() {
	sendEthernet("switch52 on")
}

def switch52off() {
	sendEthernet("switch52 off")
}

def play() {
	sendEthernet("switch13 on")
}

def pause() {
    sendEthernet("switch13 on")
}

def nextTrack() {
    sendEthernet("switch3 on")
}

def previousTrack() {
    sendEthernet("switch4 on")
}

def setLevel(number) {
    sendEthernet("switch5 on")
}

def poll() {
	configure()
}

def configure() {
	log.debug "Executing 'configure'"
    updateDeviceNetworkID()
    sendEvent(name: "numberOfButtons", value: numButtons)
    //log.debug "illuminance " + illuminanceSampleRate + "|temphumid " + temphumidSampleRate + "|water " + waterSampleRate
	[
		sendEthernet("water " + waterSampleRate),
        "delay 1000",
        sendEthernet("illuminance " + illuminanceSampleRate),
        "delay 1000",
        sendEthernet("temphumid " + temphumidSampleRate)
    ]
}

def updateDeviceNetworkID() {
	log.debug "Executing 'updateDeviceNetworkID'"
    if(device.deviceNetworkId!=mac) {
    	log.debug "setting deviceNetworkID = ${mac}"
        device.setDeviceNetworkId("${mac}")
	}
}

def updated() {
	if (!state.updatedLastRanAt || now() >= state.updatedLastRanAt + 5000) {
		state.updatedLastRanAt = now()
		log.debug "Executing 'updated'"
    	runIn(3, updateDeviceNetworkID)
        sendEvent(name: "numberOfButtons", value: numButtons)
	}
	else {
		log.trace "updated(): Ran within last 5 seconds so aborting."
	}
}

def initialize() {
	sendEvent(name: "numberOfButtons", value: numButtons)
}

def refresh() {
	sendEthernet("refresh")
}