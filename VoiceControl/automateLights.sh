#!/bin/bash

if [[ `ps aux | egrep "python /home/rachel/Documents/Java/HomeAutomation/VoiceControl/automateLights.py" | wc -l` < 2 ]]; then 
  echo -e "Restarting automateLights.py now.";
  python /home/rachel/Documents/Java/HomeAutomation/VoiceControl/automateLights.py & 
fi
if [[ `ps aux | egrep "python /home/rachel/Documents/Java/HomeAutomation/VoiceControl/automateLights.py" | wc -l` > 2 ]]; then 
  kill -9 `ps aux | egrep -i "python /home/rachel/Documents/Java/HomeAutomation/VoiceControl/automateLights.py" | awk '{print $2}'`;
fi
