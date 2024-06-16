# FFmpeg Android CLI

This project allows you to use the `ffmpeg` and `ffprobe` commands in Android on the CLI.
Hardware acceleration with MediaCodec is enabled. Based on https://github.com/arthenica/ffmpeg-kit.

## System requirements

- Android 4.1 or later (API level 16+)
- Shell access (`adb shell`, serial port or terminal app)
- **No** root required

## Installation

1. Download your preferred version
   from https://gitea.antonlyap.pp.ua/antonlyap/ffmpeg-android-cli/releases
2. Extract the archive into a folder on your device (e.g. `/data/local/tmp`)
3. Make sure commands are executable:
    ```shell
    chmod 755 ffmpeg ffprobe
    ```
4. Test it out:
    ```
    $ ./ffmpeg -hwaccels                                       
    ffmpeg version n7.0 Copyright (c) 2000-2024 the FFmpeg developers
    built with Android (7155654, based on r399163b1) clang version 11.0.5 (https://android.googlesource.com/toolchain/llvm-project 87f1315dfbea7c137aa2e6d362dbb457e388158d)
    configuration: --cross-prefix=aarch64-linux-android- --sysroot=/home/user/Android/Sdk/ndk/22.1.7171670/toolchains/llvm/prebuilt/linux-x86_64/sysroot --prefix=/home/user/Projects/ffmpeg-kit/prebuilt/android-arm64-lts/ffmpeg --pkg-config=/usr/bin/pkg-config --enable-version3 --arch=aarch64 --cpu=armv8-a --target-os=android --enable-neon --enable-asm --enable-inline-asm --ar=aarch64-linux-android-ar --cc=aarch64-linux-android21-clang --cxx=aarch64-linux-android21-clang++ --ranlib=aarch64-linux-android-ranlib --strip=aarch64-linux-android-strip --nm=aarch64-linux-android-nm --extra-libs='-L/home/user/Projects/ffmpeg-kit/prebuilt/android-arm64-lts/cpu-features/lib -lndk_compat' --disable-autodetect --enable-cross-compile --enable-pic --enable-jni --enable-optimizations --enable-swscale --disable-static --enable-shared --enable-pthreads --enable-v4l2-m2m --disable-outdev=fbdev --disable-indev=fbdev --enable-small --disable-xmm-clobber-test --disable-debug --enable-lto --disable-neon-clobber-test --disable-programs --disable-postproc --disable-doc --disable-htmlpages --disable-manpages --disable-podpages --disable-txtpages --disable-sndio --disable-schannel --disable-securetransport --disable-xlib --disable-cuda --disable-cuvid --disable-nvenc --disable-vaapi --disable-vdpau --disable-videotoolbox --disable-audiotoolbox --disable-appkit --disable-alsa --disable-cuda --disable-cuvid --disable-nvenc --disable-vaapi --disable-vdpau --disable-sdl2 --disable-openssl --disable-zlib --enable-mediacodec
    libavutil      59.  8.100 / 59.  8.100
    libavcodec     61.  3.100 / 61.  3.100
    libavformat    61.  1.100 / 61.  1.100
    libavdevice    61.  1.100 / 61.  1.100
    libavfilter    10.  1.100 / 10.  1.100
    libswscale      8.  1.100 /  8.  1.100
    libswresample   5.  1.100 /  5.  1.100
    Hardware acceleration methods:
    mediacodec

    ```

   Passing arguments via environment variables (`FFMPEG_OPTIONS`) is also possible,
   to work
   around `app_process` [stack corruption due to command length](https://github.com/Genymobile/scrcpy/issues/2841):
    ```
    $ FFMPEG_OPTIONS="-hwaccels" ./ffmpeg                                       
    ffmpeg version n7.0 Copyright (c) 2000-2024 the FFmpeg developers
    built with Android (7155654, based on r399163b1) clang version 11.0.5 (https://android.googlesource.com/toolchain/llvm-project 87f1315dfbea7c137aa2e6d362dbb457e388158d)
    configuration: --cross-prefix=aarch64-linux-android- --sysroot=/home/user/Android/Sdk/ndk/22.1.7171670/toolchains/llvm/prebuilt/linux-x86_64/sysroot --prefix=/home/user/Projects/ffmpeg-kit/prebuilt/android-arm64-lts/ffmpeg --pkg-config=/usr/bin/pkg-config --enable-version3 --arch=aarch64 --cpu=armv8-a --target-os=android --enable-neon --enable-asm --enable-inline-asm --ar=aarch64-linux-android-ar --cc=aarch64-linux-android21-clang --cxx=aarch64-linux-android21-clang++ --ranlib=aarch64-linux-android-ranlib --strip=aarch64-linux-android-strip --nm=aarch64-linux-android-nm --extra-libs='-L/home/user/Projects/ffmpeg-kit/prebuilt/android-arm64-lts/cpu-features/lib -lndk_compat' --disable-autodetect --enable-cross-compile --enable-pic --enable-jni --enable-optimizations --enable-swscale --disable-static --enable-shared --enable-pthreads --enable-v4l2-m2m --disable-outdev=fbdev --disable-indev=fbdev --enable-small --disable-xmm-clobber-test --disable-debug --enable-lto --disable-neon-clobber-test --disable-programs --disable-postproc --disable-doc --disable-htmlpages --disable-manpages --disable-podpages --disable-txtpages --disable-sndio --disable-schannel --disable-securetransport --disable-xlib --disable-cuda --disable-cuvid --disable-nvenc --disable-vaapi --disable-vdpau --disable-videotoolbox --disable-audiotoolbox --disable-appkit --disable-alsa --disable-cuda --disable-cuvid --disable-nvenc --disable-vaapi --disable-vdpau --disable-sdl2 --disable-openssl --disable-zlib --enable-mediacodec
    libavutil      59.  8.100 / 59.  8.100
    libavcodec     61.  3.100 / 61.  3.100
    libavformat    61.  1.100 / 61.  1.100
    libavdevice    61.  1.100 / 61.  1.100
    libavfilter    10.  1.100 / 10.  1.100
    libswscale      8.  1.100 /  8.  1.100
    libswresample   5.  1.100 /  5.  1.100
    Hardware acceleration methods:
    mediacodec
    ```

## Building

1. Get your preferred version of FFmpeg from https://github.com/arthenica/ffmpeg-kit/releases
   or https://gitea.antonlyap.pp.ua/antonlyap/ffmpeg-kit/releases (includes updated FFmpeg and
   resolution alignment patch).
2. Place the AAR file under `app/libs`
3. Build the APK:
    ```shell
    ./gradlew assembleRelease
    ```
4. Extract the APK:
    ```shell
    unzip app/app/build/outputs/apk/release/app-release-unsigned.apk
    ```
5. Create an archive with the following files (`ffmpeg` and `ffprobe` can be found under `cmd/`):
    ```
    lib/
    classes.dex
    ffmpeg
    ffprobe
    ```

## Versioning

The project versions follow the `a.b-c-d` pattern, where:

- `a.b` is the FFmpeg version (major and minor), such as `7.0`
- `c` is the version of FFmpeg Kit
- `d` is the version of FFmpeg Android CLI

## Useful software and resources

- [MediaCodecInfo](https://github.com/Parseus/codecinfo/releases) - displays the capabilities of
  your device.
- [Qualcomm Hexagon (Wikipedia)](https://en.wikipedia.org/wiki/Qualcomm_Hexagon) - hardware
  transcoding capabilities of different Qualcomm SoCs.
- [rclone](https://rclone.org) - lets you mount a remote filesystem (e.g. to transcode files from
  your computer).
  Requires root access and the `fusermount3` binary. Some media containers
  require `--vfs-cache-mode=writes` to function properly.
- [FFmpeg Streaming](https://trac.ffmpeg.org/wiki/StreamingGuide)
  and [FFmpeg protocols](https://ffmpeg.org/ffmpeg-protocols.html) - also let you access remote
  files.
- [rffmpeg](https://github.com/joshuaboniface/rffmpeg) - lets you control transcoding from another
  machine over SSH.

## License

This project's code and build artifacts (APK file and its derivatives) are licensed under the GNU
LGPL.
However, if using a variant of FFmpeg which includes GPL-licensed components, the build artifacts
become
subject to the GPL.

More information: https://github.com/arthenica/ffmpeg-kit?tab=readme-ov-file#14-license
and https://ffmpeg.org/legal.html
