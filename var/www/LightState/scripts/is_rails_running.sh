#!/bin/bash
 
if [[ `ps aux | egrep --color -i "rails s -b 0.0.0.0" | wc -l` != 2 ]]; then 
	/var/www/LightState/bin/rails s -b 0.0.0.0 -p 3000 &;
fi
