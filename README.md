<h1 align="center">ScyBlaster</h1>

ScyBlaster is a project of a customizable Java Minecraft Launcher. 
Its goal is to let anyone make his own launcher.

It is more axed to small server project than for one person.

This project is under GNU AGPLv3 license.

## Decomposition

This project is divided in three modules :

- **Commons :** include everything that could be used by other modules,
- **Starter :** responsible for downloading the launcher, making sure it is up-to-date,
- **Launcher :** responsible for launching the predefined Minecraft.

## Making your own launcher

You can make your own launcher by cloning this repository and configuring a bit some parameters.

- If you want to customize a bit the GUI, you can just change the JSON configuration files,
- If you need an advanced customization, you can change some lines of code directly.

Once you're okay with your configurations, you can build them (see [Build](#Build)).

You can find documentation on how to customize your launcher on the [wiki](https://github.com/Meltwin/ScyBlaster/wiki).

## Build

To build the launcher, you can just type the following command at the root of the project.

     mvn install

It will generate the starter and the launcher at :

- __Starter__   : ./build/ScyblasterStarter-***Version***.jar
- __Launcher__  : ./build/ScyblasterLauncher-***Version***.jar

<p align="center"><i> Copyright © - 2022 <a href="https://github.com/Meltwin">Meltwin</a> </i></p>