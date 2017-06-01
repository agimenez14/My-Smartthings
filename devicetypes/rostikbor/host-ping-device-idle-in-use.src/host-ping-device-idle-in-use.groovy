/**
 *  Host Ping Device
 *
 *  Copyright 2016 Jake Tebbett
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
	definition (name: "Host Ping Device (idle/in use)", namespace: "RostikBor", author: "RostikBor") {
		capability "switch"
        capability "Motion Sensor"
        capability "Actuator"
        capability "Button"
        capability "Sensor"
        
        command "push1"
        command "hold1"
	}

	tiles(scale: 2) {
        standardTile("switch", "device.switch", width: 6, height: 6, canChangeIcon: true) {
    		state "idle", label: 'Idle', icon: "st.Electronics.electronics18", backgroundColor: "#79b821"
    		state "in use", label: 'In use', icon: "st.Electronics.electronics18", backgroundColor: "#ff0000"
		}
        valueTile("lastMotion", "device.lastMotion", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Use: ${currentValue}'
		}
        standardTile("push1", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Lock", icon:"st.locks.lock.locked", backgroundColor: "#ffffff", action: "push1"
		} 
 		standardTile("hold1", "device.button", width: 1, height: 1, decoration: "flat") {
			state "default", label: "Hold", icon:"st.locks.lock.locked", backgroundColor: "#ffffff", action: "hold1"
		}    
    	main("switch")
        details(["switch","lastMotion","push1"])
    }
}
def parse(String description) {
	
}

def on() {
    sendEvent(name: "switch", value: "in use", descriptionText: "Is in Use");
    sendEvent(name:"motion", value:"active")    
    def now = new Date().format("MMM-d-yyyy h:mm a", location.timeZone)
    sendEvent(name: "lastMotion", value: now, descriptionText: "")
}

def off() {
    sendEvent(name: "switch", value: "idle", descriptionText: "Is Idle");
    sendEvent(name:"motion", value:"inactive")
}

def hold1() {
	sendEvent(name: "button", value: "held", data: [buttonNumber: "1"], descriptionText: "$device.displayName button 1 was held", isStateChange: true)
} 

def push1() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "1"], descriptionText: "Lock button was pushed", isStateChange: true)
}