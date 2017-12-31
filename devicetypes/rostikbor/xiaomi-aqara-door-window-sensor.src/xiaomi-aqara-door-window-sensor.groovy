/**
 *  Xiaomi Aqara Door/Window Sensor
 *
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
 *  https://github.com/bspranger/Xiaomi/tree/master/devicetypes/bspranger/xiaomi-aqara-door-window-sensor.src
 *  https://github.com/GvnCampbell/SmartThings-Xiaomi
 *
 * Based on original DH by Eric Maycock 2015 and Rave from Lazcad
 *  change log:
 *	added DH Colours
 *  added 100% battery max
 *  fixed battery parsing problem
 *  added lastcheckin attribute and tile
 *  added extra tile to show when last opened
 *  colours to confirm to new smartthings standards
 *  added ability to force override current state to Open or Closed.
 *  added experimental health check as worked out by rolled54.Why
 *  Bspranger - Adding Aqara Support
 *  Rinkelk - added date-attribute support for Webcore
 *  Rinkelk - Changed battery percentage with code from cancrusher
 *  Rinkelk - Changed battery icon according to Mobile785
 *  sulee - Added endpointId copied from GvnCampbell's DH - Detects sensor when adding
 *  sulee - Track battery as average of min and max over time
 *  sulee - Clean up some of the code
 *  bspranger - renamed to bspranger to remove confusion of a4refillpad
 */
metadata {
   definition (name: "Xiaomi Aqara Door/Window Sensor", namespace: "RostikBor", author: "RostikBor") {
      capability "Configuration"
      capability "Sensor"
      capability "Contact Sensor"
      capability "Refresh"
      capability "Battery"
      capability "Health Check"

      attribute "lastCheckin", "String"
      attribute "lastOpened", "String"
      attribute "lastOpenedDate", "Date" 
      attribute "lastCheckinDate", "Date"

      fingerprint endpointId: "01", profileId: "0104", deviceId: "5F01", inClusters: "0000,0003,FFFF,0006", outClusters: "0000,0004,FFFF", manufacturer: "LUMI", model: "lumi.sensor_magnet.aq2", deviceJoinName: "Xiaomi Aqara Door Sensor"

      command "Refresh"
   }
   preferences
   {
   input("IsGarage", "bool", title: "Using for a garage?", required: false, displayDuringSetup: false, defaultValue: false)
   //input("IsGarage", "enum", title: "Using for a garage?", description: "", options: ["Yes", "No"], defaultValue: "No", required: false, displayDuringSetup: false)
   }
   simulator {
      status "closed": "on/off: 0"
      status "open": "on/off: 1"
   }
    
   tiles(scale: 2) {
      multiAttributeTile(name:"contact", type: "generic", width: 6, height: 4){
         tileAttribute ("device.contact", key: "PRIMARY_CONTROL") {
            attributeState "open", label:'Open', icon:"st.contact.contact.open", backgroundColor:"#ffa81e"
            attributeState "closed", label:'Closed', icon:"st.contact.contact.closed", backgroundColor:"#79b821"
            attributeState "open-garage", label:'Open', icon:"st.doors.garage.garage-open", backgroundColor:"#ffa81e"
            attributeState "closed-garage", label:'Closed', icon:"st.doors.garage.garage-closed", backgroundColor:"#79b821"
         }
            tileAttribute("device.lastOpen", key: "SECONDARY_CONTROL") {
    			attributeState("default", label:'Last Opened: ${currentValue}')
            }
      }
      valueTile("lastcheckin", "device.lastCheckin", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Checkin: ${currentValue}'
		}
      valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 2, height: 2) {
			state "battery", label:'${currentValue}% battery', unit:""
		}
      standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
      }
      standardTile("fclose", "device.contact", inactiveLabel: false, decoration: "flat", width: 1, height: 1) {
            state "default", action:"forceClose", icon:"st.contact.contact.closed"
      }
      standardTile("configure", "device.configure", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
	  }
      main (["contact"])
      details(["contact","battery","configure","refresh", "lastcheckin","fclose"])
   }
}

def parse(String description) {
   def linkText = getLinkText(device)
   def result = zigbee.getEvent(description)
   
	//  send event for heartbeat    
   def now = new Date().format("MMM d h:mm a", location.timeZone)
   sendEvent(name: "lastCheckin", value: now, descriptionText: "Check-in", displayed: false)
    
   Map map = [:]

   if (result) {
   	   log.debug "${linkText} Event: ${result}"
       map = getContactResult(result);
       sendEvent(name: "lastOpen", value: now, descriptionText: "", displayed: false)
   } else if (description?.startsWith('catchall:')) {
       map = parseCatchAllMessage(description)
   }
   log.debug "${linkText}: Parse returned ${map}"
   def results = map ? createEvent(map) : null

   return results;
}

private Map getBatteryResult(rawValue) {
    def linkText = getLinkText(device)
    def result = [
		name: 'battery',
		value: '--',
        unit: "%",
        translatable: true
    ]
    
    def rawVolts = rawValue / 1000

	def maxBattery = state.maxBattery ?: 0
    def minBattery = state.minBattery ?: 0

	if (maxBattery == 0 || rawVolts > minBattery)
    	state.maxBattery = maxBattery = rawVolts
        
    if (minBattery == 0 || rawVolts < minBattery)
    	state.minBattery = minBattery = rawVolts
    
    def volts = (maxBattery + minBattery) / 2

	def minVolts = 2.7
    def maxVolts = 3.0
    def pct = (volts - minVolts) / (maxVolts - minVolts)
    def roundedPct = Math.round(pct * 100)
    result.value = Math.min(100, roundedPct)
    result.descriptionText = "${linkText}: raw battery is ${rawVolts}v, state: ${volts}v, ${minBattery}v - ${maxBattery}v"
    
    return result
}

private Map parseCatchAllMessage(String description) {
    def linkText = getLinkText(device)
	Map resultMap = [:]
	def cluster = zigbee.parse(description)
	log.debug "${linkText}: Parsing CatchAll: '${cluster}'"
	if (cluster) {
		switch(cluster.clusterId) {
			case 0x0000:
            	if ((cluster.data.get(4) == 1) && (cluster.data.get(5) == 0x21)) // Check CMD and Data Type
            		resultMap = getBatteryResult((cluster.data.get(7)<<8) + cluster.data.get(6))
			break
		}
	}

	return resultMap
}

def configure() {
	def linkText = getLinkText(device)
    log.debug "${linkText}: configuring"
    return zigbee.readAttribute(0x0001, 0x0021) + zigbee.configureReporting(0x0001, 0x0021, 0x20, 600, 21600, 0x01)
}

def refresh() {
	def linkText = getLinkText(device)
    log.debug "${linkText}: refreshing"
    return zigbee.readAttribute(0x0001, 0x0021) + zigbee.configureReporting(0x0001, 0x0021, 0x20, 600, 21600, 0x01)
}

private Map getContactResult(result) {
   def linkText = getLinkText(device)
   def value = result.value == "on" ? "open" : "closed"
   def descriptionText = "${linkText} was ${value == "open" ? value + "ed" : value}"
   if (IsGarage == true) {
   		if (value == 'open')
        	value = 'open-garage'
        else 
           value = 'closed-garage'
   }
   return [
      name: 'contact',
      value: value,
      descriptionText: descriptionText
	]
}

def installed() {
	checkIntervalEvent("installed");
}

def updated() {
	checkIntervalEvent("updated");
}

private checkIntervalEvent(text) {
// Device wakes up every 1 hours, this interval allows us to miss one wakeup notification before marking offline
    def linkText = getLinkText(device)
    log.debug "${linkText}: Configured health checkInterval when ${text}()"
	sendEvent(name: "checkInterval", value: 2 * 60 * 60 + 2 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
}