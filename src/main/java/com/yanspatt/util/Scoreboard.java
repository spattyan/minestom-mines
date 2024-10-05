package com.yanspatt.util;

import com.google.common.collect.Maps;
import com.yanspatt.MinesServer;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

import java.util.Map;

public class Scoreboard {

    private Map<Player, Sidebar> sidebars;

    public Scoreboard() {
        this.sidebars = Maps.newHashMap();
    }


    public void addSidebar(Player player) {
        MinesServer.getInstance().getUserController().getUser(player.getUsername()).ifPresent(user -> {
            Sidebar sidebar = new Sidebar(LegacyComponentSerializer.legacyAmpersand().deserialize("&c&lMINA").decoration(TextDecoration.ITALIC, false));
            String[] lines = new String[] {
                    "&0",
                    "&f Minerando: &b " + MinecraftServer.getConnectionManager().getOnlinePlayerCount(),
                    "&1",
                    "&f Seu progresso",
                    " &b➥ &fTokens:&c " + user.getTokens(),
                    " &b➥ &fBlocos: &a" + BigNumbers.format(user.getBlocksMined()),
                    " &b➥ &fLevel: &b",
                    "&2",
                    "&eloja.oberon.com",


            };

            for (int i = 0; i < lines.length; i++) {
                sidebar.createLine(new Sidebar.ScoreboardLine("line-" + i,LegacyComponentSerializer.legacyAmpersand().deserialize(lines[i]).decoration(TextDecoration.ITALIC, false),lines.length-i));
            }
            sidebar.addViewer(player);
            sidebars.put(player, sidebar);
        });
    }

}
