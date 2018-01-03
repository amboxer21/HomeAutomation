# HomeAutomation
> A project to turn your lights on/off from your android phone with voice control abilities 

> Find video demonstration here https://www.youtube.com/watch?v=AuKCVxZyc6w

## [Directory Structure]

> **Arduino/**

`This is the directory where all of the code for the micro conroller goes. I have an ethernet and wifi server which will work on either the wireless breakout board or the Arduino`

> **C/**

`In this directory there is a client and server model that can be used for testing. Whether it be cross compiled and used on your android or compiled natively on Linux.`

> **LightAutomation/**

`This is an Android app that uses the old ant build system which is used to interface with the electromagnetic wireless switch and Raspberry PI 3. The app consists of a toggle button which is the only physical aspect of the app's UI.`

> **VoiceControl/**	

`FILL`

> **etc/init.d/**

`FILL`

> **var/www/LightState/**

`FILL`

---

> **NOTES.txt**

`FILL`

> **README.md**

`FILL`

> **crontab.txt**

`FILL`

> **light_statuses_production.mysql**

`FILL`

<br></br>
## [Cross compile C client on ARMv7]

**Environment Variables**(These are set in ~/.bashrc)**:**

`export NDK="/home/anthony/android-ndk-r13b"`

`export TOOLCHAIN="/home/anthony/android-toolchain"`

`export SYSROOT="/home/anthony/android-ndk-r13b/platforms/android-21/arch-arm"`

`export CC="/home/anthony/android-ndk-r13b/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-gcc-4.9 --sysroot=$SYSROOT"`

**Note:** 
You can unset the exported variable by running unset CC(or name of exported here). Also, you may need to source ~/.bashrc if the variables are not sticking. Once you export variables in your bashrc, they will only take effect when you open a "new" terminal window unless you source them.

**To compile from commandline**

`[anthony@anthony HomeAutomation]$ $CC client.c -fPIE -pie -o client`

**Building the standalone toolchain:**

`$NDK/build/tools/make_standalone_toolchain.py --arch arm --api 21 --install-dir /home/anthony/android-toolchain`
  
## [Dependencies]

**Debian**

sudo apt-get install android-tools-adb android-tools-fastboot


**NOTE**

light_statuses_controller_new.rb is going to be the new controller. Changes to the old controller broke LAN synchronization. I had to modify the android app as well. So now I will have to revert those changes as well.

