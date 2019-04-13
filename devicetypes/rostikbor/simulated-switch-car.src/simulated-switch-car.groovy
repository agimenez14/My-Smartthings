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

    definition (name: "Simulated Switch (car)", namespace: "RostikBor", author: "RostikBor") {
		capability "Switch"
        capability "Relay Switch"
		capability "Sensor"
		capability "Actuator"
        capability "presenceSensor"

		command "onPhysical"
		command "offPhysical"
	}

	tiles(scale: 2) {
		standardTile("car", "device.car", decoration: "flat", width: 5, height: 5, canChangeIcon: true) {
			state "away", label: 'away', icon: "st.Transportation.transportation2", backgroundColor: "#ffffff"
			state "present", label: 'present', icon: "st.Transportation.transportation2", backgroundColor: "#53a7c0"
		}
		standardTile("present", "device.car", decoration: "flat", width: 1, height: 1) {
			state "default", label: 'present', action: "onPhysical", backgroundColor: "#53a7c0"
		}
		standardTile("away", "device.car", decoration: "flat", width: 1, height: 1) {
			state "default", label: 'away', action: "offPhysical", backgroundColor: "#ffffff"
		}
        main (["car"])
		details(["car"])//,"present","away"])
	}
}

def parse(description) {
}

def on() {
	log.debug "$version on()"
	sendEvent(name: "car", value: "present")
    sendEvent(name: "presence", value: "present");
}

def off() {
	log.debug "$version off()"
	sendEvent(name: "car", value: "away")
    sendEvent(name: "presence", value: "not present");
}

def onPhysical() {
	log.debug "$version onPhysical()"
	sendEvent(name: "car", value: "present", type: "physical")
    sendEvent(name: "presence", value: "present");
}

def offPhysical() {
	log.debug "$version offPhysical()"
	sendEvent(name: "car", value: "away", type: "physical")
    sendEvent(name: "presence", value: "not present");
}

private getVersion() {
	"PUBLISHED"
}