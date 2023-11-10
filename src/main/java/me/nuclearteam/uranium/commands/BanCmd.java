package me.nuclearteam.uranium.commands;

import me.nuclearteam.uranium.Uranium;
import me.nuclearteam.uranium.models.BannedPlayer;
import me.nuclearteam.uranium.models.CachePlayer;
import me.nuclearteam.uranium.utils.DateUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BanCmd extends Command implements TabExecutor {

    private final Uranium uranium;

    public BanCmd(Uranium uranium) {
        super("uban", "uranium.moderation.ban");
        this.uranium = uranium;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length >= 1) {
            String name = args[0];
            String reason;
            if (args.length == 1) {
                reason = "Banned by an operator.";
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            }
            UUID source = sender instanceof ProxiedPlayer ? ((ProxiedPlayer) sender).getUniqueId() : UUID.fromString("0000 0000 0000 0000");

            Optional<CachePlayer> cachedPlayer = this.uranium.cachedPlayers.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findAny();
            if (cachedPlayer.isPresent()) {
                if (this.uranium.bannedPlayers.stream().noneMatch(p -> p.getUuid().equals(this.uranium.getProxy().getPlayer(name).getUniqueId()))) {
                    this.uranium.bannedPlayers.add(new BannedPlayer(cachedPlayer.get().getUuid(), source, reason, DateUtils.getCurrent(), "forever"));
                    this.uranium.cache.SaveCache();

                    sender.sendMessage(new TextComponent("Banned " + name + ": " + reason));

                    ProxiedPlayer proxiedPlayer = this.uranium.getProxy().getPlayer(name);
                    if (proxiedPlayer != null) {
                        proxiedPlayer.disconnect(new TextComponent("You are banned from this server"));
                    }
                } else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Nothing changed. The player is already banned"));
                }
            } else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "That player does not exist"));
            }
        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Incomplete command"));
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
