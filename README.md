<div style="display:flex;flex-direction:row;align-items:center;margin-bottom:3em">
     <div style="flex:2"></div>
     <div style="flex:1; border:1px solid orange;"></div>
     <div style="padding:0 1em;border:1px solid orange;border-radius:10%; margin-bottom:0;">
          <h1 align="center">SCYBLASTER</h1>
          <p style="padding-top:0;margin-top:-1.5em;" align="center">by Meltwin</p>
     </div>
     <div style="flex:1; border:1px solid orange;"></div>
     <div style="flex:2"></div>
</div>

ScyBlaster is a project of an easily customizable Java Minecraft Launcher. 
Its goal is to let anyone make his own launcher. While it's more axed to small server project than for one person, it may be used in several cases.

This project is under GNU AGPLv3 license.

## Minecraft datas

To make this API, I had to look and investigate for some informations on how to make a working launcher. You can found everything (the scripts and results) on [this repository](https://github.com/Meltwin/Scyblaster-Data), or if you only want a summary from the raw results you can check [the online documentation](https://meltwin.github.io/Scyblaster-Data/).

## Making your own launcher

The simplest launcher you can make can be written as:

```Java
package io.meltwin.mylauncher;

import io.meltwin.scyblaster.common.types.Logging;
import io.meltwin.scyblaster.config.ProjectConfiguration;
import io.meltwin.scyblaster.config.ProjectConfigurationBuilder;

public class Launcher {
    public static void main(String[] args) {
        ProjectConfiguration configs = new ProjectConfigurationBuilder("project.xml").make();
        try (ScyblasterAPI api = new ScyblasterAPI(configs)) {
            api.launchMC("1.20.2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

To make a more advanced launcher, check [the documentation](https://meltwin.github.io/ScyBlaster/docs/get-started/) for a detailled explanation about the undergoing working schema.

## Directories structures

The directories are decomposed as:

- **common/** for classes and interfaces that will be used accross the whole project,
- **config/** for the logic about the configuration system for the whole launcher
- **minecraft/** for everything deeply linked to MC API or works

For a detailled explanation of the undergoing work, please check [the documentation](https://meltwin.github.io/ScyBlaster/docs/). If you are interested in helping improving this API, you can also find everything you may need on the online documentation.