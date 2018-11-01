/**
 *  Copyright 2017 
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
 *  2017-03 First release of the Xiaomi Temp/Humidity Device Handler
 *  2017-03 Includes battery level (hope it works, I've only had access to a device for a limited period, time will tell!)
 *  2017-03 Last checkin activity to help monitor health of device and multiattribute tile
 *  2017-03 Changed temperature to update on .1° changes - much more useful
 *  2017-03-08 Changed the way the battery level is being measured. Very different to other Xiaomi sensors.
 *  2017-03-23 Added Fahrenheit support
 *  2017-03-25 Minor update to display unknown battery as "--", added fahrenheit colours to main and device tiles
 *  2017-03-29 Temperature offset preference added to handler
 *
 *  known issue: these devices do not seem to respond to refresh requests left in place in case things change
 *	known issue: tile formatting on ios and android devices vary a little due to smartthings app - again, nothing I can do about this
 *  known issue: there's nothing I can do about the pairing process with smartthings. it is indeed non standard, please refer to community forum for details
 *
 */
metadata {
	definition (name: "Xiaomi Aqara Temperature Humidity Sensor", namespace: "RostikBor", author: "RostikBor") {
		capability "Temperature Measurement"
		capability "Relative Humidity Measurement"
		capability "Sensor"
        capability "Battery"
        capability "Refresh"
    	capability "Thermostat"
        capability "Illuminance Measurement"
        
        attribute "lastCheckin", "String"
        attribute "pressure", "number"
        
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
    
	preferences {
    	section {
			input("IsRoundTemp", "enum", title: "Round temperature?", description: "Tap to set", options: ["Yes", "No"], defaultValue: "Yes", required: false, displayDuringSetup: false)
		}
		section {
			input title: "Temperature Offset", description: "This feature allows you to correct any temperature variations by selecting an offset. Ex: If your sensor consistently reports a temp that's 5 degrees too warm, you'd enter '-5'. If 3 degrees too cold, enter '+3'. Please note, any changes will take effect only on the NEXT temperature change.", displayDuringSetup: false, type: "paragraph", element: "paragraph"
			input "tempOffset", "number", title: "Degrees", description: "Adjust temperature by this many degrees", range: "*..*", displayDuringSetup: false
		}
        section {
            input name: "PressureUnits", type: "enum", title: "Pressure Units", options: ["mbar", "kPa", "inHg", "mmHg"], description: "Sets the unit in which pressure will be reported", defaultValue: "mbar", displayDuringSetup: true, required: true
		} 
		section {
            input title: "Pressure Offset", description: "This feature allows you to correct any pressure variations by selecting an offset. Ex: If your sensor consistently reports a pressure that's 5 too high, you'd enter '-5'. If 3 too low, enter '+3'. Please note, any changes will take effect only on the NEXT pressure change.", displayDuringSetup: false, type: "paragraph", element: "paragraph"
            input "pressOffset", "number", title: "Pressure", description: "Adjust prssure by this many units", range: "*..*", displayDuringSetup: false
		}
    }
    
	// UI tile definitions
	tiles(scale: 2) {
		valueTile("temperature", "device.temperature", width: 4, height: 4) {
			state("temperature", label:'${currentValue}°',
				backgroundColors:[
					[value: 32, color: "#153591"],
                    [value: 44, color: "#1e9cbb"],
                    [value: 59, color: "#90d2a7"],
                    [value: 74, color: "#44b621"],
                    [value: 84, color: "#f1d801"],
                    [value: 95, color: "#d04e00"],
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
        valueTile("temperatureC", "device.temperatureC", width: 2, height: 1) {
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
        valueTile("battery", "device.battery", decoration: "flat", inactiveLabel: false, width: 1, height: 1) {
			state "default", label:'${currentValue}% battery', unit:""
		}
        valueTile("lasttemp", "device.lastTemp", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Temp: ${currentValue}'
		}
        valueTile("lasthumid", "device.lastHumid", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Humidity: ${currentValue}'
		}
        valueTile("lastcheckin", "device.lastCheckin", decoration: "flat", inactiveLabel: false, width: 5, height: 1) {
			state "default", label:'Last Check-in: ${currentValue}'
		}
		standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
        valueTile("pressure", "device.pressure", inactiveLabel: false, decoration: "flat", width: 2, height: 1) {
			state "default", label:'${currentValue} hPa'
		}
		main(["temperature", "humidity"])
		details(["temperature", "humidity", "temperatureC","pressure","lasttemp","lasthumid","battery","lastcheckin"])
	}
}

// Parse incoming device messages to generate events
def parse(String description) {
	log.debug "RAW: $description"
	def name = parseName(description)
    log.debug "Parsename: $name"
	def value = parseValue(description)
    log.debug "Parsevalue: $value"
	def unit = name == "temperature" ? getTemperatureScale() : (name == "humidity" ? "%" : null)
    
	def result = createEvent(name: name, value: value, unit: unit)
    log.debug "Evencreated: $name, $value, $unit"
	log.debug "Parse returned ${result?.descriptionText}"
    
    def now = new Date().format("MMM d h:mm a", location.timeZone)
    sendEvent(name: "lastCheckin", value: now, descriptionText: "Check-in", displayed: false)
    
    if (name == "temperature"){    	
    	def celsius = ((value.toDouble() - 32) * 0.5556)
    	sendEvent(name: "temperatureC", value: celsius, displayed: false)
        // temp heartbeat
    	sendEvent(name: "lastTemp", value: now, descriptionText: "", displayed: false)
    } 
    if (name == "humidity"){
    	sendEvent(name: "lastHumid", value: now, descriptionText: "", displayed: false)
    }
    if (name == "pressure"){
    	sendEvent(name: "illuminance", value: value, unit: "hPa", displayed: false)
    }
	return result
}

private String parseName(String description) {


	if (description?.startsWith("temperature: ")) {
		return "temperature"
        
	} else if (description?.startsWith("humidity: ")) {
		return "humidity"
        
	} else if (description?.startsWith("catchall: ")) {
        return "battery"
        
	} else if (description?.startsWith("read attr - raw: "))
    {
 	   def attrId        
 	   attrId = description.split(",").find {it.split(":")[0].trim() == "attrId"}?.split(":")[1].trim()

		if(attrId == "0000")
        {
        	return "pressure"
        
    	} else if (attrId == "0005")
        {
        	return "model"
        
    	}
    }
	return null
}

private String parseValue(String description) {

	if (description?.startsWith("temperature: ")) {
		def value = ((description - "temperature: ").trim()) as Float 
        
        if (getTemperatureScale() == "C") {
        	if (tempOffset) {
				return (Math.round(value * 10))/ 10 + tempOffset as Float
            } else {
            	if (IsRoundTemp == "No") {
                    return (Math.round(value * 10))/ 10 as Float
                } 
                else {
                    return value.toInteger()
                }
			}            	
		} else {
        	if (tempOffset) {
				return (Math.round(value * 90/5))/10 + 32 + offset as Float
            } else {
            	if (IsRoundTemp == "No") {
                    return (Math.round(value * 90/5))/10 + 32 as Float
                } 
                else {
                    return ((Math.round(value * 90/5))/10 + 32).toInteger()
                }
			}            	
		}        
        
	} else if (description?.startsWith("humidity: ")) {
		def pct = (description - "humidity: " - "%").trim()
        
		if (pct.isNumber()) {
			return Math.round(new BigDecimal(pct)).toString()
		}
	} else if (description?.startsWith("catchall: ")) {
		return parseCatchAllMessage(description)
        
	}  else if (description?.startsWith("read attr - raw: ")){
        return parseReadAttrMessage(description)
        
    }else {
    log.debug "${linkText} unknown: $description"
    sendEvent(name: "unknown", value: description)
    }
	null
}

private String parseReadAttrMessage(String description) {
    def result = '--'
    def cluster
    def attrId
    def value
    def linkText = getLinkText(device)
    cluster = description.split(",").find {it.split(":")[0].trim() == "cluster"}?.split(":")[1].trim()
    attrId = description.split(",").find {it.split(":")[0].trim() == "attrId"}?.split(":")[1].trim()
    value = description.split(",").find {it.split(":")[0].trim() == "value"}?.split(":")[1].trim()
    
    
    if (cluster == "0403" && attrId == "0000") {
         result = value[0..3]
         float pressureval = Integer.parseInt(result, 16)
         log.debug "${linkText}: Converting ${pressureval} to ${PressureUnits}"
         
         switch (PressureUnits)
         {
         case "mbar":
         	pressureval = (pressureval/10) as Float
            pressureval = pressureval.round(1);
         	break;
         case "kPa":
         	pressureval = (pressureval/100) as Float
            pressureval = pressureval.round(2);
            break;
         case "inHg":
         	pressureval = (((pressureval/10) as Float) * 0.0295300)
            pressureval = pressureval.round(2);
            break;
         case "mmHg":
         	pressureval = (((pressureval/10) as Float) * 0.750062)
            pressureval = pressureval.round(2);
            break;
         }
         
         log.debug "${linkText}: ${pressureval} ${PressureUnits} before applying the pressure offset."
         
         if (pressOffset)
         {
           pressureval = (pressureval + pressOffset)
           pressureval = pressureval.round(2);
         }
         
         result = pressureval
    } 
    else if (cluster == "0000" && attrId == "0005") 
    {
        for (int i = 0; i < value.length(); i+=2) 
        {
            def str = value.substring(i, i+2);
            def NextChar = (char)Integer.parseInt(str, 16);
            result = result + NextChar
        }
    }
    return result
}

private String parseCatchAllMessage(String description) {
	def result = '--'
	def cluster = zigbee.parse(description)
	log.debug cluster
	if (cluster) {
		switch(cluster.clusterId) {
			case 0x0000:
			if ((cluster.data.get(4) == 1) && (cluster.data.get(5) == 0x21))  // Check CMD and Data Type
            {
              result = getBatteryResult((cluster.data.get(7)<<8) + cluster.data.get(6))
            }
 			break
		}
	}

	return result
}


private String getBatteryResult(rawValue) {
    def linkText = getLinkText(device)
    //log.debug '${linkText} Battery'

	//log.debug rawValue

    def result ="--"
    
    def volts = rawValue / 1000
    def minVolts = 2.0
    def maxVolts = 3.055
    def pct = (volts - minVolts) / (maxVolts - minVolts)
    def roundedPct = Math.round(pct * 100)
    pct = Math.min(100, roundedPct)
    log.debug "${device.displayName} battery was ${pct}%, ${volts} volts"
    result = pct.toString();
    return result
}

def refresh() {
	def linkText = getLinkText(device)
    log.debug "${linkText}: refresh called"
	def refreshCmds = [
		"st rattr 0x${device.deviceNetworkId} 1 1 0x00", "delay 2000",
		"st rattr 0x${device.deviceNetworkId} 1 1 0x20", "delay 2000"
	]

	return refreshCmds + enrollResponse()
}

def configure() {
	// Device-Watch allows 2 check-in misses from device + ping (plus 1 min lag time)
	// enrolls with default periodic reporting until newer 5 min interval is confirmed
	sendEvent(name: "checkInterval", value: 2 * 60 * 60 + 1 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])

	// temperature minReportTime 30 seconds, maxReportTime 5 min. Reporting interval if no activity
	// battery minReport 30 seconds, maxReportTime 6 hrs by default
	return refresh() + zigbee.batteryConfig() + zigbee.temperatureConfig(30, 900) // send refresh cmds as part of config
}

def enrollResponse() {
	log.debug "Sending enroll response"
	String zigbeeEui = swapEndianHex(device.hub.zigbeeEui)
	[
		//Resending the CIE in case the enroll request is sent before CIE is written
		"zcl global write 0x500 0x10 0xf0 {${zigbeeEui}}", "delay 200",
		"send 0x${device.deviceNetworkId} 1 ${endpointId}", "delay 2000",
		//Enroll Response
		"raw 0x500 {01 23 00 00 00}", "delay 200",
		"send 0x${device.deviceNetworkId} 1 1", "delay 2000"
	]
}

private String swapEndianHex(String hex) {
	reverseArray(hex.decodeHex()).encodeHex()
}

private byte[] reverseArray(byte[] array) {
	int i = 0;
	int j = array.length - 1;
	byte tmp;
	while (j > i) {
		tmp = array[j];
		array[j] = array[i];
		array[i] = tmp;
		j--;
		i++;
	}
	return array
}