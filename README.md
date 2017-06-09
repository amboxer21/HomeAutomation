# HomeAutomation
> A project to turn your lights on/off from your android phone with voice control abilities 

> Find video demonstration here https://www.youtube.com/watch?v=AuKCVxZyc6w

## [Cross compile C client on ARMv7]

**Environment Variables**(These are set in ~/.bashrc)**:**

`export NDK="/home/anthony/android-ndk-r13b"`

`export TOOLCHAIN="/home/anthony/android-toolchain"`

`export SYSROOT="/home/anthony/android-ndk-r13b/platforms/android-21/arch-arm"`

`export CC="/home/anthony/android-ndk-r13b/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-gcc-4.9 --sysroot=$SYSROOT"`

**Note:** You can unset the exported variable by running unset CC(or name of exported here). Also, you may need to source ~/.bashrc if the variables are not sticking. Once you export variables in your bashrc, they will only take effect when you open a "new" terminal window unless you source them.

**To compile from commandline**
`Cross compile with pie flag => $CC client.c -fPIE -pie -o client`

**Building the standalone toolchain:**
    <dd>$NDK/build/tools/make_standalone_toolchain.py --arch arm --api 21 --install-dir /home/anthony/android-toolchain
  
## [Dependencies]

**Debian**

sudo apt-get install android-tools-adb android-tools-fastboot

