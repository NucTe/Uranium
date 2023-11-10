package me.nuclearteam.uranium.commands;

import me.nuclearteam.uranium.Uranium;
import me.nuclearteam.uranium.models.CachePlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PermissionCmd extends Command implements TabExecutor {

    private final Uranium uranium;

    public PermissionCmd(Uranium uranium) {
        super("uperm", "uranium.moderator.givepermission");
        this.uranium = uranium;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String name = args[0];
            ProxiedPlayer plr = this.uranium.getProxy().getPlayer(name);
            String perm = args[1];

            plr.setPermission(perm, true);

            sender.sendMessage(new TextComponent("Permission given."));
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incomplete command. /uperm <target> <perm>"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return this.uranium.cachedPlayers.stream().map(CachePlayer::getName).filter(n -> n.startsWith(args[0])).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
