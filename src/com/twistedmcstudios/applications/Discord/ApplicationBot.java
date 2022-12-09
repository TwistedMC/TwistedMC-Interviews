package com.twistedmcstudios.applications.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.managers.channel.concrete.TextChannelManager;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.md_5.bungee.BungeeCord;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.logging.Level;

public class ApplicationBot extends ListenerAdapter {
    private String token;
    public static JDA jda;
    private static String NewInterviewCatChannelID = "797966936461934612";
    private static String FinishedInterviewCatChannelID = "797967043605037070";
    private static String interviewGuild = "797776730857013259";

    public static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/New York"));
    public static int year = calendar.get(Calendar.YEAR);

    public ApplicationBot(String token) {
        this.token = token;
    }

    public void start() {
        try {
            jda = JDABuilder.createDefault(token,
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_WEBHOOKS,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.GUILD_INVITES,
                            GatewayIntent.GUILD_EMOJIS_AND_STICKERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL).build();
            jda.awaitReady();
            jda.addEventListener(this);

            jda.updateCommands()
                    .addCommands(Commands.slash("create", "Create an interview channel")
                            .addOption(OptionType.USER, "user", "User taking interview.", true)
                            .addOption(OptionType.STRING, "interview_type", "Interview type.", true)
                            .addOption(OptionType.STRING, "interview_date", "Interview date.", true)
                            .addOption(OptionType.STRING, "application_link", "User's Application link.", true)
                            .addOption(OptionType.STRING, "microphone", "Microphone?", true)
                            .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                            .setGuildOnly(true))

            .queue();


            //jda.getPresence().setPresence(Ac); hm

            BungeeCord.getInstance().getLogger().log(Level.INFO, "Starting bot..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void stop() {
        jda.shutdown();
        BungeeCord.getInstance().getLogger().log(Level.INFO,"Applications Bot has shut down.");
    }


    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("create")) {
            User user = Objects.requireNonNull(event.getOption("user")).getAsUser();
            Member member = Objects.requireNonNull(event.getOption("user")).getAsMember();
            String type = Objects.requireNonNull(event.getOption("interview_type")).getAsString();
            String interview_date = Objects.requireNonNull(event.getOption("interview_date")).getAsString();
            String application_link = Objects.requireNonNull(event.getOption("application_link")).getAsString();
            String microphone = Objects.requireNonNull(event.getOption("microphone")).getAsString();

            assert member != null;
            TextChannel interview = Objects.requireNonNull(event.getGuild()).createTextChannel(member.getEffectiveName() + "-interview", event.getGuild().getCategoryById("797966936461934612")).complete();
            ChannelManager<TextChannel, TextChannelManager> interviewManager = interview.getManager()
                    .putPermissionOverride(Objects.requireNonNull(member), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                    .putRolePermissionOverride(797963097310494726L, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                    .putRolePermissionOverride(853377145177767956L, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                    .putRolePermissionOverride(868357239537279027L, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND), null)
                    .putRolePermissionOverride(797776730857013259L, null, EnumSet.of(Permission.VIEW_CHANNEL));
            interviewManager.queue();

            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle(member.getNickname() + "'s " + type + " Interview (" + user.getAsTag() + ")", null);

            eb.setColor(new Color(47,49,54));

            eb.addField("Application Link", application_link, true);
            eb.addField("Microphone", microphone, true);
            eb.addField("Interview Date", interview_date, true);

            eb.setFooter("© " + year + " TwistedMC Studios", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
            eb.setTimestamp(new Date().toInstant());

            interview.sendMessageEmbeds(eb.build()).queue();
            event.reply("Interview channel created!").setEphemeral(true).queue();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

    }
}
