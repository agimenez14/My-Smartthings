metadata {
	definition (name: "ST_NodeMCU Rostik Purifier", namespace: "Rostikbor", author: "Rostikbor") {
		capability "Configuration"
        capability "Switch Level"
		capability "Switch"
		capability "Polling"
        capability "Refresh"
        capability "Actuator"
        attribute "fanspeed", "string"
        attribute "powerstat", "string"
        attribute "dim", "string"
        attribute "auto", "string"
        attribute "timehr", "string"
        attribute "uv", "string"
		command "switch1on" 	//POWER
		command "switch2on" 	//AUTO
		command "switch3on" 	//DIM
		command "switch4on" 	//SPEED
		command "switch5on"		//TIME
		command "switch6on" 	//UV
        
		command "switch50on"
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
        standardTile("switch1", "device.powerstat", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
            state "off", label: 'off', action: "switch1on", backgroundColor: "#ffffff", icon: "st.Appliances.appliances11"
			state "on", label: 'on', action: "switch1on", backgroundColor: "#00A0DC", icon: "st.Appliances.appliances11"
			state "offline", label: 'offline', action: "refresh.refresh", backgroundColor: "#ff0000"
		}
        standardTile("switch2", "device.auto", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: '', action: "switch2on", backgroundColor: "#ffffff", icon: "st.thermostat.fan-auto"
			state "on", label: '', action: "switch2on", backgroundColor: "#00A0DC", icon: "st.thermostat.fan-auto"
		}
        standardTile("switch3", "device.dim", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
            state "off", label: 'Dim Off', action: "switch3on", backgroundColor: "#ffffff", icon:"st.illuminance.illuminance.bright"
			state "on", label: 'Dim On', action: "switch3on", backgroundColor: "#000000", icon:"st.illuminance.illuminance.dark"
		}
        standardTile("switch4", "device.fanspeed", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
			state "0", label: 'Off', action: "switch4on", backgroundColor: "#ffffff"
			state "1", label: 'Low', action: "switch4on", backgroundColor: "#79b821"
			state "2", label: 'Medium', action: "switch4on", backgroundColor: "#ffa81e"
			state "3", label: 'High', action: "switch4on", backgroundColor: "#d04e00" 
		}
        valueTile("switch5", "device.timehr", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
			state "default", label: '${currentValue} Hrs', action: "switch5on", backgroundColor: "#ffffff"
		}
        standardTile("switch6", "device.uv", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
            state "off", label: 'UV', action: "switch6on", backgroundColor: "#ffffff"
			state "on", label: 'UV', action: "switch6on", backgroundColor: "#00A0DC"
		}
        standardTile("switch7", "device.air", decoration: "flat", width: 2, height: 2, canChangeIcon: true) {
			state "default", label: '${name} Air', backgroundColor: "#ffffff"
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

        main(["switch1"])
        details(["switch1","switch2","switch3","switch4","switch5","switch6",//"switch7",
                 "rssi","lastCheckin","refresh","switch50"])
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
		if (name.startsWith("rssi")) {
    		results = createEvent(name: name, value: value, displayed: false)
        }
        else if (name.startsWith("switch50") && value == "on") {
    		results = createEvent(name: name, value: value, descriptionText: "Restart sent")
        }
        else if (name.startsWith("switch50") && value == "off") {
    		results = createEvent(name: name, value: value, descriptionText: "Successfully Restarted")
        }
        else if (name.startsWith("switch")) {
    		results = createEvent(name: name, value: value, displayed: false)
        }
        else {
    		results = createEvent(name: name, value: value)
		}
		
    	def nowDay = new Date().format("MMM d h:mm a", location.timeZone)
    	sendEvent(name: "lastCheckin", value: nowDay, displayed: false)
        
		log.debug results
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

def switch50on() {
    sendEvent(name: "rssi", value: "0", displayed: false)
	sendEthernet("switch50 on")
}
def on() {
	sendEthernet("switch1 on")	
}

def off() {
	sendEthernet("switch1 on")	
}

def offline() {
	sendEvent(name: "powerstat", value: "offline")
}

def setLevel(value) {	
    if (value == 0) {
		sendEthernet("switch3 on")	//dim
    }
    else if (value == 1) {
		sendEthernet("stsendtime 1")	//time
    }
    else if (value == 2) {
		sendEthernet("stsendtime 2")	//time
    }
    else if (value == 3) {
		sendEthernet("stsendtime 3")	//time
    }
    else if (value == 4) {
		sendEthernet("stsendtime 4")	//time
    }
    else if (value == 5) {
		sendEthernet("stsendtime 5")	//time
    }
    else if (value == 6) {
		sendEthernet("stsendtime 6")	//time
    }
    else if (value == 7) {
		sendEthernet("stsendtime 7")	//time
    }
    else if (value == 8) {
		sendEthernet("stsendtime 8")	//time
    }
    else if (value == 9) {
		sendEthernet("stsendtime 9")	//time
    }
    else if (value == 10) {
		sendEthernet("stsendtime 10")	//time
    }
    else if (value == 11) {
		sendEthernet("stsendtime 11")	//time
    }
    else if (value == 12) {
		sendEthernet("stsendtime 12")	//time
    }
    else if (value == 13) {
		sendEthernet("stsendtime 13")	//time
    }
    else if (value == 14) {
		sendEthernet("stsendtime 14")	//time
    }
    else if (value == 15) {
		sendEthernet("stsendtime 15")	//time
    }
    else if (value == 16) {
		sendEthernet("stsendtime 16")	//time
    }
    else if (value == 17) {
		sendEthernet("stsendtime 17")	//time
    }
    else if (value == 18) {
		sendEthernet("stsendtime 18")	//time
    }
    else if (value == 19) {
		sendEthernet("stsendtime 19")	//time
    }
    else if (value == 20) {
		sendEthernet("stsendtime 20")	//time
    }
    else if (value == 21) {
		sendEthernet("stsendtime 21")	//time
    }
    else if (value == 22) {
		sendEthernet("stsendtime 22")	//time
    }
    else if (value == 23) {
		sendEthernet("stsendtime 23")	//time
    }
    else if (value == 24) {
		sendEthernet("stsendtime 24")	//time
    }
    else if (value == 99) {
		sendEthernet("switch6 on")	//uv
    }
    else if (value == 100) {
		sendEthernet("switch4 on")	//speed
    }
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
}

def refresh() {
	sendEthernet("refresh")
}