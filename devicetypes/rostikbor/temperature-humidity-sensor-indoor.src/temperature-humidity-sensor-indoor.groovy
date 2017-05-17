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
import java.text.DecimalFormat
 
metadata {
	definition (name: "Temperature/Humidity Sensor (indoor)", namespace: "RostikBor", author: "RostikBor") {
		capability "Temperature Measurement"
		capability "Relative Humidity Measurement"
		capability "Sensor"
        capability "Battery"

		fingerprint profileId: "0104", deviceId: "0302", inClusters: "0000,0001,0003,0009,0402,0405"
	}

	// simulator metadata
	simulator {
		for (int i = 0; i <= 100; i += 10) {
			status "${i}F": "temperature: $i F"
		}

		for (int i = 0; i <= 100; i += 10) {
			status "${i}%": "humidity: ${i}%"
		}
	}

	// UI tile definitions
	tiles(scale: 2) {
		valueTile("temperature", "device.temperature", width: 4, height: 4) {
			state("temperature", label:'${currentValue}°',
				backgroundColors:[
					[value: 58, color: "#153591"],
					[value: 63, color: "#1e9cbb"],
					[value: 67, color: "#90d2a7"],
					[value: 71, color: "#44b621"],
					[value: 80, color: "#f1d801"],
					[value: 89, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
				]
			)
		}
		valueTile("humidity", "device.humidity", width: 2, height: 2) {
			state "humidity", label:'${currentValue}%', unit:"",
				backgroundColors:[
                	[value: 10, color: "#ffffff"],
					[value: 20, color: "#edf1ff"],
					[value: 30, color: "#d3deff"],
                    [value: 35, color: "#beccf4"],
					[value: 40, color: "#a3baff"],
                    [value: 45, color: "#93aeff"],
					[value: 50, color: "#87a5ff"],
					[value: 65, color: "#517cff"],
					[value: 80, color: "#0041ff"]
                ]
		}
		valueTile("temperatureC", "device.temperatureC", width: 2, height: 2) {
			state "temperatureC", label:'${currentValue}°C'/*,
				backgroundColors:[
					[value: 14.4, color: "#153591"],
					[value: 17.2, color: "#1e9cbb"],
					[value: 19.4, color: "#90d2a7"],
					[value: 21.7, color: "#44b621"],
					[value: 26.7, color: "#f1d801"],
					[value: 31.7, color: "#d04e00"],
					[value: 35.6, color: "#bc2323"]
                    ]*/
		}
        valueTile("lasttemp", "device.lastTemp", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Temp: ${currentValue}'
		}
        valueTile("lasthumid", "device.lastHumid", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Humidity: ${currentValue}'
		}
        valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 1, height: 1) {
			state "default", label:'${currentValue}% battery', unit:""
		}
        valueTile("lastcheckin", "device.lastCheckin", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Check-in: ${currentValue}'
		}
		main(["temperature", "humidity"])
		details(["temperature", "humidity", "temperatureC","lasttemp","battery","lasthumid","lastcheckin"])
	}
}

// Parse incoming device messages to generate events
def parse(String description) {
	//  send event for heartbeat    
    def now = new Date().format("MMM-d-yyyy h:mm a", location.timeZone)
    sendEvent(name: "lastCheckin", value: now, descriptionText: "Check-in")
	//def realFeel = 80
	//def t = 70
	//def rh = 35
	def name = parseName(description)
	def value = parseValue(description)
    // attempt at realFeal tempurature
    /**if (name == "temperature")
    	t = value
    if (name == "humidity")
    	rh = value 
    if (name == "temperature" || name == "humidity") {
    	realFeel = -42.379 + (2.04901523* t) + (10.14333127*rh)-(0.22475541*t*rh)-(0.00683783*t*t)-(0.05481717*rh*rh)+(0.00122874*t*t*rh)+(0.00085282*t*rh*rh)-(0.00000199*t*t*rh*rh)
        sendEvent(name: "realFeel", value: realFeel)
    } */
    if (name == "temperature"){    	
    	def celsius = ((value.toInteger() - 32) * 0.5556)
        //DecimalFormat df = new DecimalFormat("0.0") 
    	sendEvent(name: "temperatureC", value: celsius.toInteger())
        // temp heartbeat
        now = new Date().format("MMM-d-yyyy h:mm a", location.timeZone)
    	sendEvent(name: "lastTemp", value: now, descriptionText: "")
    }
    if (name == "humidity"){     
    now = new Date().format("MMM-d-yyyy h:mm a", location.timeZone)
    sendEvent(name: "lastHumid", value: now, descriptionText: "")
    }
	def unit = name == "temperature" ? getTemperatureScale() : (name == "humidity" ? "%" : null)
	
    def result = createEvent(name: name, value: value, unit: unit)
	log.debug "Parse returned ${result?.descriptionText}"
	return result
}
/**private toCelsius(double value){
	temp2 = 
    return temp2
} */

private String parseName(String description) {
	if (description?.startsWith("temperature: ")) {
		return "temperature"
	} else if (description?.startsWith("humidity: ")) {
		return "humidity"
	}
	null
}

private String parseValue(String description) {
	if (description?.startsWith("temperature: ")) {
		return zigbee.parseHATemperatureValue(description, "temperature: ", getTemperatureScale())
	} 
    else if (description?.startsWith("humidity: ")) {
		def pct = (description - "humidity: " - "%").trim()
		if (pct.isNumber()) {
			return Math.round(new BigDecimal(pct)).toString()
	    } 
    }
    else if (description?.startsWith("catchall: ")) {
		return parseCatchAllMessage(description)
     }
	
	null
}
private String parseCatchAllMessage(String description) {
	def result = '--'
	def cluster = zigbee.parse(description)
	log.debug cluster
	if (cluster) {
		switch(cluster.clusterId) {
			case 0x0000:
			result = getBatteryResult(cluster.data.get(6)) 
 			break
		}
	}

	return result
}


private String getBatteryResult(rawValue) {
	log.debug 'Battery'
	def linkText = getLinkText(device)
	log.debug rawValue

	def result =  '--'
    def maxBatt = 100
    def battLevel = Math.round(rawValue * 100 / 255)

	if (battLevel > maxBatt) {
				battLevel = maxBatt
    }

	return battLevel
}

