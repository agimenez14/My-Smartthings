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

    definition (name: "Simulated Motion (by Switch)", namespace: "RostikBor", author: "RostikBor") {
		capability "Motion Sensor"
        capability "Switch"
	}

	tiles {
		multiAttributeTile(name:"motion", type: "generic", width: 3, height: 2){
			tileAttribute ("device.motion", key: "PRIMARY_CONTROL") {
				attributeState "active", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#ffa81e"
				attributeState "inactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff"
			}
            	tileAttribute("device.lastMotion", key: "SECONDARY_CONTROL") {
    				attributeState("default", label:'Last Motion: ${currentValue}')
            }
		}
        standardTile("on", "device.switch", decoration: "flat", width: 1, height: 1) {
				state "default", label: 'on', action: "on", backgroundColor: "#ffffff"
        }
        standardTile("off", "device.switch", decoration: "flat", width: 1, height: 1) {
                state "default", label: 'off', action: "off", backgroundColor: "#ffffff"
        }
        main (["motion"])
		details (["motion","on","off"])
	}
}

def parse(description) {
}

def on() {
	log.debug "$version on()"
	sendEvent(name: "switch", value: "on")
    sendEvent(name:"motion", value:"active")
    def now = new Date().format("MMM-d-yyyy h:mm a", location.timeZone)
    sendEvent(name: "lastMotion", value: now, descriptionText: "")
}

def off() {
	log.debug "$version off()"
	sendEvent(name: "switch", value: "off")
    sendEvent(name:"motion", value:"inactive")
}

private getVersion() {
	"PUBLISHED"
}