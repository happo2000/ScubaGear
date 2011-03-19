package com.bukkit.happo2000.ScubaGear;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

public class ScubaGearEntityListener extends EntityListener 
{
    private final ScubaGear 	plugin;

    public ScubaGearEntityListener(ScubaGear instance) 
    {
        plugin = instance;
    }
    
    @Override
    public void onEntityDamage(EntityDamageEvent event) 
    {
    	if ((event.getEntity() instanceof Player) && (event.getCause() == DamageCause.DROWNING))
    	{
    		Player currentPlayer = (Player)event.getEntity();
    		
    		if (plugin.isWearingScuba(currentPlayer))
    		{
    			if (plugin.deductRedstone(currentPlayer))
    			{
    				plugin.removeNotifiedPlayer(currentPlayer);
    				currentPlayer.setRemainingAir(currentPlayer.getMaximumAir());
					event.setCancelled(true);	
				}
    			else if (plugin.playerNeedsNotification(currentPlayer))
    				currentPlayer.sendMessage(ChatColor.RED + "[Warning] " + ChatColor.WHITE + "No redstone to power the rebreather.");
    		}
	    	else if (plugin.playerNeedsNotification(currentPlayer))
	    		currentPlayer.sendMessage(ChatColor.DARK_GRAY + "[Notice] Wearing a gold helmet while carrying redstone allows   underwater breathing.");
    	}
    }
}

