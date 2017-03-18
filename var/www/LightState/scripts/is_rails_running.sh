#!/bin/bash

export RAILS_ENV=production

if [[ `ps aux | egrep --color -i "/var/www/LightState/bin/rails s -b 0.0.0.0" | wc -l` < 2 ]]; then 
	/var/www/LightState/bin/rails s -b 0.0.0.0 -p 3000;
fi

if [[ `ps aux | egrep --color -i "/var/www/LightState/bin/rails" | wc -l` > 2 ]]; then 
	kill -9 `ps aux | egrep --color -i "/var/www/LightState/bin/rails s -b 0.0.0.0"`;
fi
