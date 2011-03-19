package com.bukkit.happo2000.ScubaGear;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class ScubaGear extends JavaPlugin {
	
    private final 	ScubaGearEntityListener 	entityListener 	= new ScubaGearEntityListener(this);
    private final   ScubaGearPlayerListener		playerListener	= new ScubaGearPlayerListener(this);
    private final 	List<Player> 				notifiedPlayers = new ArrayList<Player>();

    public ScubaGear() 
    {
        super();
    }

    public void onEnable() 
    {
        PluginManager pluginManager = getServer().getPluginManager();
        
        PluginDescriptionFile pdfFile = this.getDescription();
    	
    	pluginManager.registerEvent(Event.Type.ENTITY_DAMAGED, this.entityListener, Event.Priority.Lowest, this);
    	pluginManager.registerEvent(Event.Type.PLAYER_KICK, this.playerListener, Event.Priority.Lowest, this);
    	pluginManager.registerEvent(Event.Type.PLAYER_QUIT, this.playerListener, Event.Priority.Lowest, this);
    	
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onDisable() 
    {
    }
    
    public boolean isDebugging(final Player player) 
    {
		return false;
    }

    public void setDebugging(final Player player, final boolean value) 
    {
    }
    
    boolean isWearingScuba(Player currentPlayer)
    {
    	ItemStack helmet = currentPlayer.getInventory().getHelmet();
    	
    	return (helmet != null) && (helmet.getType() == Material.GOLD_HELMET);
    }
    
    boolean deductRedstone(Player currentPlayer)
    {
    	boolean bRedstoneRemoved = false;
    	int		nRedstoneLeft	 = 0;
    	
    	ItemStack[] items = currentPlayer.getInventory().getContents();
    	
    	for (int i = items.length - 1; i >= 0; --i)
    	{
    		if (items[i].getType() == Material.REDSTONE)
    		{
    			int stackSize = items[i].getAmount();

    			if (!bRedstoneRemoved)
    			{
    				if (--stackSize > 0)
	    			{
	        			items[i].setAmount(stackSize);
	    				currentPlayer.getInventory().setItem(i, items[i]);
	    			}
	    			else
	    				currentPlayer.getInventory().removeItem(new ItemStack(Material.REDSTONE, 1));
    			}

    			nRedstoneLeft += stackSize;

    			bRedstoneRemoved = true;
    		}
    	}
    	
    	if (bRedstoneRemoved && nRedstoneLeft < 6)
    	{
    		if (nRedstoneLeft == 0)
    			currentPlayer.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.WHITE + "Last unit of redstone has been used.");
    		else
    			currentPlayer.sendMessage(ChatColor.YELLOW + "[Warning] " + ChatColor.WHITE + "Only " + ChatColor.AQUA + Integer.toString(nRedstoneLeft) + ChatColor.WHITE + (nRedstoneLeft > 1 ? " units" : " unit") + " of redstone remaining.");
    	}
    		
    	return bRedstoneRemoved;
    }
    
    public boolean playerNeedsNotification(Player currentPlayer)
    {
    	if (notifiedPlayers.contains(currentPlayer))
    		return false;
    	else
    		return notifiedPlayers.add(currentPlayer);
    	
    }

    public void removeNotifiedPlayer(Player currentPlayer)
    {
    	notifiedPlayers.remove(currentPlayer);
    }
}

