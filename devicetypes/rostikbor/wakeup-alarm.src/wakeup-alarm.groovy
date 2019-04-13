metadata {
  definition (name: "Wakeup Alarm", namespace: "RostikBor", author: "RostikBor") {
    capability "Sensor"
      
    command "monUp"
    command "monDown"
    command "monSetpoint"
    command "tueUp"
    command "tueDown"
    command "tueSetpoint"
    command "wedUp"
    command "wedDown"
    command "wedSetpoint"
    command "thurUp"
    command "thurDown"
    command "thurSetpoint"
    command "friUp"
    command "friDown"
    command "friSetpoint"
    command "satUp"
    command "satDown"
    command "satSetpoint"
    command "sunUp"
    command "sunDown"
    command "sunSetpoint"
    command "reset"
    command "work"
    command "setWeekDay"
    
    attribute "mon", "number"
    attribute "tue", "number"
    attribute "wed", "number"
    attribute "thur", "number"
    attribute "fri", "number"
    attribute "sat", "number"
    attribute "sun", "number"
  }

  // simulator metadata
  simulator { }

  tiles(scale: 2) {
        valueTile("frontTile", "device.frontTile", width: 2, height: 2) {
            state "default", label:'${currentValue}'
		}
        valueTile("mon", "device.mon", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default",label:'Monday'
		}
        valueTile("monSetpoint", "device.monSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("monUp", "device.monUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"monUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("monDown", "device.monDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"monDown", icon:"st.thermostat.thermostat-down"
        }
        valueTile("tue", "device.tue", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default", label:'Tuesday'
		}
        valueTile("tueSetpoint", "device.tueSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("tueUp", "device.tueUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"tueUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("tueDown", "device.tueDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"tueDown", icon:"st.thermostat.thermostat-down"
        }
        valueTile("wed", "device.wed", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default", label:'Wednesday'
		}
        valueTile("wedSetpoint", "device.wedSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("wedUp", "devicewed.wedUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"wedUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("wedDown", "device.wedDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"wedDown", icon:"st.thermostat.thermostat-down"
        }
        valueTile("thur", "device.thur", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default", label:'Thursday'
		}
        valueTile("thurSetpoint", "device.thurSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("thurUp", "device.thurUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"thurUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("thurDown", "device.thurDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"thurDown", icon:"st.thermostat.thermostat-down"
        }
        valueTile("fri", "device.fri", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default", label:'Friday'
		}
        valueTile("friSetpoint", "device.friSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("friUp", "device.friUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"friUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("friDown", "device.friDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"friDown", icon:"st.thermostat.thermostat-down"
        }
        valueTile("sat", "device.sat", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default", label:'Saturday'
		}
        valueTile("satSetpoint", "device.satSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("satUp", "device.satUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"satUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("satDown", "device.satDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"satDown", icon:"st.thermostat.thermostat-down"
        }
        valueTile("sun", "device.sun", decoration: "flat", inactiveLabel: false, width: 2, height: 1) {
			state "default", label:'Sunday'
		}
        valueTile("sunSetpoint", "device.sunSetpoint", width: 2, height: 1) {
            state "default", label:'${currentValue}'
        }
        standardTile("sunUp", "device.sunUp", decoration: "flat", width: 1, height: 1) {
            state "default", action:"sunUp", icon:"st.thermostat.thermostat-up"
        }        
        standardTile("sunDown", "device.sunDown", decoration: "flat", width: 1, height: 1) {
            state "default", action:"sunDown", icon:"st.thermostat.thermostat-down"
        }
        standardTile("reset", "device.temperature", decoration: "flat", width: 1, height: 1) {
            state "default", action:"reset", icon:"st.secondary.refresh"
        }
        valueTile("work", "device.temperature", decoration: "flat", width: 1, height: 1) {
            state "default", action:"work", label:"Work"
        }

      main "frontTile"
      details(["mon","monUp", "monSetpoint", "monDown",
      "tue","tueUp", "tueSetpoint", "tueDown",
      "wed","wedUp", "wedSetpoint", "wedDown",
      "thur","thurUp", "thurSetpoint", "thurDown",
      "fri","friUp", "friSetpoint", "friDown",
      "sat","satUp", "satSetpoint", "satDown",
      "sun","sunUp", "sunSetpoint", "sunDown",
      "reset","work"
      ])
  }
}


// parse events into attributes
def parse(String description) {
    def map = [:]
    def activeSetpoint = "--"
    def result = null
    if (map) {
      result = createEvent(map)
    }
    log.debug "Parse returned $map"
    
    return result
}

def monUp()
{
    double nextLevel = (device.currentValue("mon") as Double) + 0.25
    monSetpoint(nextLevel)
} 
def monDown()
{
    double nextLevel = (device.currentValue("mon") as Double) - 0.25
    monSetpoint(nextLevel)
} 
def monSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"monSetpoint", "value":desc, displayed:false)
    sendEvent("name":"mon", "value":time, descriptionText: "${desc}")
}

def tueUp()
{
    double nextLevel = (device.currentValue("tue") as Double) + 0.25
    tueSetpoint(nextLevel)
} 
def tueDown()
{
    double nextLevel = (device.currentValue("tue") as Double) - 0.25
    tueSetpoint(nextLevel)
} 
def tueSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"tueSetpoint", "value":desc, displayed:false)
    sendEvent("name":"tue", "value":time, descriptionText: "${desc}")
}

def wedUp()
{
    double nextLevel = (device.currentValue("wed") as Double) + 0.25
    wedSetpoint(nextLevel)
} 
def wedDown()
{
    double nextLevel = (device.currentValue("wed") as Double) - 0.25
    wedSetpoint(nextLevel)
} 
def wedSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"wedSetpoint", "value":desc, displayed:false)
    sendEvent("name":"wed", "value":time, descriptionText: "${desc}")
}

def thurUp()
{
    double nextLevel = (device.currentValue("thur") as Double) + 0.25
    thurSetpoint(nextLevel)
} 
def thurDown()
{
    double nextLevel = (device.currentValue("thur") as Double) - 0.25
    thurSetpoint(nextLevel)
} 
def thurSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"thurSetpoint", "value":desc, displayed:false)
    sendEvent("name":"thur", "value":time, descriptionText: "${desc}")
}

def friUp()
{
    double nextLevel = (device.currentValue("fri") as Double) + 0.25
    friSetpoint(nextLevel)
} 
def friDown()
{
    double nextLevel = (device.currentValue("fri") as Double) - 0.25
    friSetpoint(nextLevel)
} 
def friSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"friSetpoint", "value":desc, displayed:false)
    sendEvent("name":"fri", "value":time, descriptionText: "${desc}")
}

def satUp()
{
    double nextLevel = (device.currentValue("sat") as Double) + 0.25
    satSetpoint(nextLevel)
} 
def satDown()
{
    double nextLevel = (device.currentValue("sat") as Double) - 0.25
    satSetpoint(nextLevel)
} 
def satSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"satSetpoint", "value":desc, displayed:false)
    sendEvent("name":"sat", "value":time, descriptionText: "${desc}")
}

def sunUp()
{
    double nextLevel = (device.currentValue("sun") as Double) + 0.25
    sunSetpoint(nextLevel)
} 
def sunDown()
{
    double nextLevel = (device.currentValue("sun") as Double) - 0.25
    sunSetpoint(nextLevel)
} 
def sunSetpoint(time) 
{
    def timed = time as Double
    def desc = "${time as Integer}:${(60 * (time - (time as Integer))) as Integer}"
	sendEvent("name":"sunSetpoint", "value":desc, displayed:false)
    sendEvent("name":"sun", "value":time, descriptionText: "${desc}")
}

def reset() {
	monSetpoint(7)
    tueSetpoint(7)
    wedSetpoint(7)
    thurSetpoint(7)
    friSetpoint(7)
    satSetpoint(7)
    sunSetpoint(7)
}
def work() {
	monSetpoint(5.25)
    tueSetpoint(5.25)
    wedSetpoint(5.25)
    thurSetpoint(5.25)
    friSetpoint(5.25)
    satSetpoint(6.5)
    sunSetpoint(6.5)
}
def setWeekDay(wkDay) {
	switch (wkDay) {
        case 1:
			sendEvent("name":"frontTile", "value":"${device.currentValue("tueSetpoint")}", displayed:false)
            break
        case 2:
			sendEvent("name":"frontTile", "value":"${device.currentValue("wedSetpoint")}", displayed:false)
            break
        case 3:
			sendEvent("name":"frontTile", "value":"${device.currentValue("thurSetpoint")}", displayed:false)
            break
        case 4:
			sendEvent("name":"frontTile", "value":"${device.currentValue("friSetpoint")}", displayed:false)
            break
        case 5:
			sendEvent("name":"frontTile", "value":"${device.currentValue("satSetpoint")}", displayed:false)
            break
        case 6:
			sendEvent("name":"frontTile", "value":"${device.currentValue("sunSetpoint")}", displayed:false)
            break
        case 7:
			sendEvent("name":"frontTile", "value":"${device.currentValue("monSetpoint")}", displayed:false)
            break
    }
    
}