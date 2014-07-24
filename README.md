Sensor Fun
===
![icon](https://raw.github.com/ss1271/sensorfun/master/sensorfun/src/main/ic_launcher-web.png)

**Sensor Fun** is an Android app for plotting and recording sensors data that available to your Android phone. At this time **Sensor Fun** can be obtained via [Sensor Recorder Testers](https://plus.google.com/communities/113002490994209834096) Google Plus Group. By joining this group, you are guaranteed to get the latest beta test version directly pushed to your phone via Google Play Store.

Subject to the different types of Android phones, currently Sensor Fun supports all the sensors, namely:

* Accelerometers
* Gyroscopes
* Magnetometer
* Linear Acceleration
* Rotation Vector / Game Rotation Vector
* Orientation
* Proximity
* Light
* Pressure
* Gravity

For a detailed list of which sensors can be plotted/recorded, please check if your phone has these sensors embedded.

The data will be recorded as _*.csv_ format in:

    /PATH_TO_SDCARD/SensorFun/
The file name will be your sensor's name.

Project Structure:
----

    |---sensorfun/
    |        |
    |        |---src/
    |             |
    |             |---hulahoop/ (seperate build for hula-hoop recording)
    |             |    
    |             |---main/ (build for sensors)
    |
    |
    
In this project, I have used several third-party libraries, which are listed below:

* [ActionBarSherlock](https://github.com/JakeWharton/ActionBarSherlock)
* [OpenCSV](http://opencsv.sourceforge.net)
* [AChartEngine](http://www.achartengine.org/)