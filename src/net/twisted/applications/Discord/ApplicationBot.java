package net.twisted.applications.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.md_5.bungee.BungeeCord;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class ApplicationBot extends ListenerAdapter {
    private String token;
    public static JDA jda;
    private static String NewInterviewCatChannelID = "797966936461934612";
    private static String FinishedInterviewCatChannelID = "797967043605037070";
    private static String templateChannelID = "";
    private static String templateRoleID = "";
    private static String interviewGuild = "797776730857013259";
    public ApplicationBot(String token) {
        this.token = token;
    }

    public void start() {
        try {
            jda = JDABuilder.createDefault(token).build();
            jda.awaitReady();
            jda.addEventListener(this);
            jda.getGuildById(interviewGuild);



            //jda.getPresence().setPresence(Ac); hm

            BungeeCord.getInstance().getLogger().log(Level.INFO, "Starting bot..");
        } catch (LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


    public void stop() {
        jda.shutdown();
        BungeeCord.getInstance().getLogger().log(Level.INFO,"Applications Bot has shut down.");
    }
}
