# HomeAutomation
> Arduino project to turn lights on/off from android phone 

> Find video demonstration and breif explaination here https://www.youtube.com/watch?v=CjtIyYdGmWc

<dl>
  <dt>Environment variables to set:</dt>
    <dd>export NDK="/home/anthony/android-ndk-r13b"</dd>
    <dd>export TOOLCHAIN="/home/anthony/android-toolchain"</dd>
    <dd>export SYSROOT="/home/anthony/android-ndk-r13b/platforms/android-21/arch-arm"</dd>
    <dd>export CC="/home/anthony/android-ndk-r13b/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-gcc-4.9 --sysroot=$SYSROOT"</dd>

  
  <dt>Programs needed on top of the NDK and SDK:</dt>
    <dd>sudo apt-get install android-tools-adb android-tools-fastboot</dd>
  
  <dt>Building the standalone toolchain:</dt>
    <dd>$NDK/build/tools/make_standalone_toolchain.py --arch arm --api 21 --install-dir /home/anthony/android-toolchain</dd>

<dd>Cross compile with pie flag => $CC client.c -fPIE -pie -o client</dd>
</dl>
