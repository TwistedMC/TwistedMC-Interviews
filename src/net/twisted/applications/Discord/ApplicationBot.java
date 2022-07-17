package net.twisted.applications.Discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.md_5.bungee.BungeeCord;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

public class ApplicationBot extends ListenerAdapter {
    private String token;
    public static JDA jda;
    private static String NewInterviewCatChannelID = "797966936461934612";
    private static String FinishedInterviewCatChannelID = "797967043605037070";
    private static String interviewGuild = "797776730857013259";
    public ApplicationBot(String token) {
        this.token = token;
    }

    public void start() {
        try {
            jda = JDABuilder.createDefault(token).build();
            jda.awaitReady();
            jda.addEventListener(this);
            jda.getGuildById(interviewGuild).upsertCommand("create", "Create an interview channel")
                    .addOption(OptionType.USER, "user", "The user to base the application on.").queue();


            //jda.getPresence().setPresence(Ac); hm

            BungeeCord.getInstance().getLogger().log(Level.INFO, "Starting bot..");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void stop() {
        jda.shutdown();
        BungeeCord.getInstance().getLogger().log(Level.INFO,"Applications Bot has shut down.");
    }


    public void onSlashCommand(SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("create")) {
            User user = event.getOption("user").getAsUser();
            String name = jda.getGuildById(interviewGuild).getMemberById(user.getId()).getNickname();
            TextChannel ticket = event.getGuild().createTextChannel(format.format(now) + "-bugreport-" + event.getMember().getEffectiveName(), event.getGuild().getCategoryById("975287837836599316")).complete();
            ChannelManager ticketManager = ticket.getManager()
                    .putPermissionOverride(event.getMember(), 3072L, 8192L)
                    .putPermissionOverride(event.getGuild().getRolesByName("Admin", true).get(0), 3072L, 8192L)
                    .putPermissionOverride(event.getGuild().getRolesByName("@everyone", true).get(0), 0L, 1024L);
            ticketManager.queue();
        }
    }
    public void onModalInteraction(ModalInteractionEvent event) {

    }
}
