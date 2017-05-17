/**
 *  Copyright 2015 SmartThings
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

    definition (name: "iSpy Button/switch", namespace: "RostikBor", author: "RostikBor") {
		capability "Switch"
        capability "Relay Switch"
		capability "Sensor"
		capability "Actuator"
		capability "Button"
        
        command "push1"
        command "hold1"
        command "push2"
        command "hold2"
	}
	tiles(scale: 2) {
		standardTile("switch", "device.switch", width: 3, height: 3, canChangeIcon: true) {
			state "off", label: '${currentValue}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
			state "on", label: '${currentValue}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#00A0DC"
		}
		standardTile("on", "device.switch", width: 3, height: 3, decoration: "flat") {
			state "default", label: 'On', action: "on", backgroundColor: "#ffffff"
		}
		standardTile("off", "device.switch", width: 3, height: 3, decoration: "flat") {
			state "default", label: 'Off', action: "off", backgroundColor: "#ffffff"
		} 
        standardTile("push1", "device.button", width: 3, height: 3, decoration: "flat") {
			state "default", label: "All On", backgroundColor: "#ffffff", action: "push1"
		} 
 		standardTile("hold1", "device.button", width: 3, height: 3, decoration: "flat") {
			state "default", label: "All Snapshot", backgroundColor: "#ffffff", action: "hold1"
		}    
        standardTile("push2", "device.button", width: 3, height: 3, decoration: "flat") {
			state "default", label: "p2", backgroundColor: "#ffffff", action: "push2"
		} 
 		standardTile("hold2", "device.button", width: 3, height: 3, decoration: "flat") {
			state "default", label: "h2", backgroundColor: "#ffffff", action: "hold2"
		}    
        main "on"
		details(["on","push1","hold1"])
	}
}

def parse(description) {
}

def on() {
	log.debug "$version on()"
	sendEvent(name: "switch", value: "on", descriptionText: "On")
}

def off() {
	log.debug "$version off()"
	sendEvent(name: "switch", value: "off", descriptionText: "")
}

def hold1() {
    sendEvent(name: "button", value: "held", data: [buttonNumber: "1"], descriptionText: "All Snapshot", isStateChange: true)
} 

def push1() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "1"], descriptionText: "All On", isStateChange: true)
}

def hold2() {
    sendEvent(name: "button", value: "held", data: [buttonNumber: "2"], descriptionText: "h2", isStateChange: true)
} 

def push2() {
	sendEvent(name: "button", value: "pushed", data: [buttonNumber: "2"], descriptionText: "p2", isStateChange: true)
}