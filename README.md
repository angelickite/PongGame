# PongGame
Code as seen in the [youtube video](https://www.youtube.com/watch?v=hvpNGViawvo).

Using [DCEVM](https://dcevm.github.io/) and the modified libGDX classes I can run the empty game and then iteratively implement all features without ever having to restart the program. This Allows for a highly productive workflow. 

The GameAdapter class is where the auto-reset game mechanism is implemented.

The provided code does not run as-is. You have to setup a libGDX project in your prefered way and integrate this code.

This code is not intended for commerical use, rather just as a demonstration on how such a workflow can be achieved.

Following classes have been adapted from [libGDX](https://github.com/libgdx/libgdx):

MyApplicationListener.java, MyLwjglApplication.java, MyLwjglApplicationConfiguration.java, MyLwjglCursor.java, MyLwjglGL20.java, MyLwjglGL30.java, MyLwjglGraphics.java, MyLwjglInput.java

The main differences from the original libGDx code are in MyLwjglApplication.mainLoop() (changed render() -> try{render()}) and MyApplicationListener.onRenderException() (newly added), otherwise the adapted classes are just needed as dependencies.

# License
The code in this repository is licensed under the [Apache 2 License](http://www.apache.org/licenses/LICENSE-2.0.html).
