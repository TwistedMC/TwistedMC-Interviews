package com.twistedmcstudios.applications;

import com.twistedmcstudios.applications.Discord.ApplicationBot;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

    public static ApplicationBot Bot = null;


    @Override
    public void onEnable() {
        Bot = new ApplicationBot("OTc1NDkwMzQ2NDc1NzQ1MzYy.GIIoyG.OWxs7o0gTDZ7XAQSCtttuO447yBTInTKqbw4m8");
        Bot.start();
    }

    @Override
    public void onDisable() {
        Bot.stop();
    }

}
