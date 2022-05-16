package net.twisted.applications;

import net.md_5.bungee.api.plugin.Plugin;
import net.twisted.applications.Discord.ApplicationBot;

public class Main extends Plugin {

    public static ApplicationBot Bot = null;


    public void OnEnable() {
        Bot = new ApplicationBot("");
    }

}
