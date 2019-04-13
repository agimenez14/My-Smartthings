metadata {
	definition (name: "Simulated Switch (Presence)", namespace: "RostikBor", author: "RostikBor") {
		capability "Switch"
		capability "Relay Switch"
		capability "Actuator"
		capability "Sensor"
        capability "Presence Sensor"

		attribute "lastUpdated", "String"

		command "generateEvent", ["string", "string"]
	}

	simulator {

	}
    
	preferences {
    	input "ismotionReset", "bool", title: "Reset Presence?", required: true, displayDuringSetup: false, defaultValue: false
		input "motionReset", "number", title: "Number of seconds after the last reported activity to report that presence is away.", description: "", value:120, displayDuringSetup: false
	}

	tiles(scale: 2) {
		multiAttributeTile(name:"presence", type: "generic", width: 3, height: 4, canChangeIcon: true){
			tileAttribute ("device.presence", key: "PRIMARY_CONTROL") {
				attributeState("not present", label:'away', backgroundColor:"#ffffff")
                attributeState("present", label:'present', backgroundColor:"#53a7c0")
				}
                tileAttribute("device.lastUpdated", key: "SECONDARY_CONTROL") {
                        attributeState("default", label:'Last Update: ${currentValue}')
                }
            }
            standardTile("presence1", "device.presence", width: 3, height: 3, canChangeBackground: true) {
                state("not present", label:'away', backgroundColor:"#ffffff")
                state("present", label:'present', backgroundColor:"#53a7c0")
			}
            standardTile("on", "device.switch", decoration: "flat", width: 1, height: 1) {
                    state "default", label: 'on', action: "on", backgroundColor: "#ffffff"
            }
            standardTile("off", "device.switch", decoration: "flat", width: 1, height: 1) {
                    state "default", label: 'off', action: "off", backgroundColor: "#ffffff"
            }
		
        main(["presence1"])
        details(["presence", "lastUpdated","on","off"])
	}
}

def parse(description) {
}

def on() {
	sendEvent(name: "switch", value: "on", displayed: false)
    sendEvent(name: "presence", value: "present")
    def now = new Date().format("MMM-d-yyyy h:mm a", location.timeZone)
    sendEvent(name: "lastMotion", value: now, descriptionText: "", displayed: false)
    if (ismotionReset == true) {
        if (settings.motionReset == null || settings.motionReset == "" ) settings.motionReset = 120
        runIn(settings.motionReset, off)
    }
}

def off() {
	sendEvent(name: "switch", value: "off", displayed: false)
    sendEvent(name: "presence", value: "not present")
}

private getVersion() {
	"PUBLISHED"
}

/*
void on() {
	parent.childOn(device.deviceNetworkId)
}

void off() {
	parent.childOff(device.deviceNetworkId)
}

def generateEvent(String name, String value) {
	sendEvent(name: name, value: value)
    def nowDay = new Date().format("MMM d h:mm a", location.timeZone)
    sendEvent(name: "lastUpdated", value: nowDay, displayed: false)
    
    if (settings.motionReset == null || settings.motionReset == "" ) settings.motionReset = 120
    if (value == "on") {
    	runIn(settings.motionReset, off)
        sendEvent(name: "presence", value: "present")
    }
    else {
    	sendEvent(name: "presence", value: "not present")
    }
} */