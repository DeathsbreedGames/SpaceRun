SpaceRun
========

This is DeathsbreedGame's first attempt at creating a casual mobile game. It will take the form of a _classic space shoot'em up_ game, and will be available on Linux, Windows, Mac, Android, and Web (iOS may be added later).

### Compiling
To compile the source-code please refer to the [libGDX build documentation](https://github.com/libgdx/libgdx/wiki/Gradle-on-the-Commandline).

__NOTICE:__ If you do not specify your Android SDK path then the program will not compile. The reason this is not included in the source-code is because the SDK path will depend on the OS. To specify the Android SDK path create a file called _local.properties_ and insert the following:
```
sdk.dir=/path/to/android/sdk
```

### Contributing
To contribute to the project simply create a pull request with your contribution to the code, and (if you wish) add your name/alias to __@author__ tag at the beginning of the Java file. Just remember that your code will be licensed with the GNU Affero GPLv3 license like the rest of the project's source-code.

### License
The code for this project is licensed with a [GNU Affero GPLv3 license](/LICENSE). However, the assets of the project (such as graphics, sound, music, etc.) have their own licenses which can be found in the asset's corresponding directory in the [assets directory](/android/assets/).
