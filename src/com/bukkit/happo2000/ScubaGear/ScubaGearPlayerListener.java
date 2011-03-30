package com.bukkit.happo2000.ScubaGear;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScubaGearPlayerListener extends PlayerListener 
{
    private final ScubaGear 	plugin;

    public ScubaGearPlayerListener(ScubaGear instance) 
    {
        plugin = instance;
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) 
    {
    	plugin.removeNotifiedPlayer(event.getPlayer());
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) 
    {
    	plugin.removeNotifiedPlayer(event.getPlayer());
    }
}
