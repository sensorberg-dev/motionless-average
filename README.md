# motionless-average
MotionlessAverage, because you don't need to allocate a million size array to calculate a moving average

[ ![Download](https://api.bintray.com/packages/sensorberg/maven/motionlessaverage/images/download.svg) ](https://bintray.com/sensorberg/maven/motionlessaverage/_latestVersion)

### Usage

The interface `MotionlessAverage` have only one method:

```Java
float averagedValue = averager.average(someValue);
```

Instantiate with one of the static constructors:

```Java
// This is ideal for scenarios where the update rate of the value being averaged is somewhat constant.
MotionlessAverage constantFilter = MotionlessAverage.Builder.createConstantFilterAverage(filter);

// This is ideal for scenarios where the update rate of the value being averaged varies.
MotionlessAverage timedFilter = MotionlessAverage.Builder.createTimeDependentAverage(minFilter, maxFilter, minTime, maxTime);
```

Add to gradle:

```Groovy
allprojects {
    repositories {
        jcenter()
    }
}

dependencies {
    ... others...
    compile 'com.sensorberg.motionlessaverage:motionlessaverage:<latest>'
}
```