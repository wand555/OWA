package me.wand555.OWA.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.wand555.OWA.Main.Perk;

public class PlayerProfile {
	
	private static HashMap<UUID, PlayerProfile> profiles = new HashMap<UUID, PlayerProfile>();

	private UUID playerUUID;
	private int thirst;
	private int maxThirst;
	private BossBar thirstBar;
	private int temperature;
	private int maxTemperature;
	private BossBar temperatureBar;
	private int zombieKills;
	private int playerKills;
	private int kills;
	private int deaths;
	private long experience;
	private ArrayList<Perk> perks;
	private ArrayList<Campfire> campfires;

	/**
	 * Called on the first join ever. When no information for that player is stored.
	 * 
	 * @param playerUUID
	 */
	public PlayerProfile(Player p) {
		this.playerUUID = p.getUniqueId();
		this.thirst = 10;
		this.maxThirst = 20;
		this.thirstBar = Bukkit.createBossBar("Thirst", BarColor.BLUE, BarStyle.SEGMENTED_20);
		if(p.hasPermission("owa.thirst")) this.thirstBar.addPlayer(p);
		this.thirstBar.setProgress(this.thirst/20d);
		this.temperature = 10;
		this.maxTemperature = 20;
		this.temperatureBar = Bukkit.createBossBar("Temeperature", BarColor.WHITE, BarStyle.SEGMENTED_20);
		if(p.hasPermission("owa.temp")) this.temperatureBar.addPlayer(p);		
		this.temperatureBar.setProgress(this.temperature/20d);
		this.playerKills = 0;
		this.zombieKills = 0;
		this.kills = 0;
		this.deaths = 0;
		this.experience = 0;
		this.perks = new ArrayList<Perk>();
		this.campfires = new ArrayList<Campfire>();
		profiles.put(this.playerUUID, this);
	}
	
	
	/**
	 * @return the thirst
	 */
	public int getThirst() {
		return thirst;
	}

	/**
	 * @param thirst the thirst to set
	 */
	public void setThirst(int thirst) {
		this.thirst = thirst;
	}

	/**
	 * @return the thirstBar
	 */
	public BossBar getThirstBar() {
		return thirstBar;
	}

	/**
	 * @param thirstBar the thirstBar to set
	 */
	public void setThirstBar(BossBar thirstBar) {
		this.thirstBar = thirstBar;
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the temperatureBar
	 */
	public BossBar getTemperatureBar() {
		return temperatureBar;
	}

	/**
	 * @param temperatureBar the temperatureBar to set
	 */
	public void setTemperatureBar(BossBar temperatureBar) {
		this.temperatureBar = temperatureBar;
	}

	/**
	 * @return the experience
	 */
	public long getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(long experience) {
		this.experience = experience;
	}

	/**
	 * @return the perks
	 */
	public ArrayList<Perk> getPerks() {
		return perks;
	}

	/**
	 * @param perks the perks to set
	 */
	public void setPerks(ArrayList<Perk> perks) {
		this.perks = perks;
	}
	
	public static HashMap<UUID, PlayerProfile> getProfiles() {
		return profiles;
	}
	
	/**
	 * Returns Profile from given UUID (Player). If not existent, a new instance is created, added and returned.
	 * 
	 * @param uuid
	 * @return found or new profile
	 */
	public static PlayerProfile getProfileFromPlayer(UUID uuid) {
		return profiles.get(uuid);
		//return profiles.getOrDefault(uuid, new PlayerProfile(Bukkit.getPlayer(uuid)));
	}


	public int getMaxThirst() {
		return maxThirst;
	}
	
	public int getMaxTemperature() {
		return maxTemperature;
	}


	/**
	 * @return the playerUUID
	 */
	public UUID getPlayerUUID() {
		return playerUUID;
	}


	/**
	 * @return the zombieKills
	 */
	public int getZombieKills() {
		return zombieKills;
	}


	/**
	 * @param zombieKills the zombieKills to set
	 */
	public void setZombieKills(int zombieKills) {
		this.zombieKills = zombieKills;
	}


	/**
	 * @return the playerKills
	 */
	public int getPlayerKills() {
		return playerKills;
	}


	/**
	 * @param playerKills the playerKills to set
	 */
	public void setPlayerKills(int playerKills) {
		this.playerKills = playerKills;
	}


	public int getKills() {
		return kills;
	}


	public void setKills(int kills) {
		this.kills = kills;
	}


	public int getDeaths() {
		return deaths;
	}


	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}


	public void playerDeath(Player p) {
		p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));
		p.setHealth(0);
		this.setDeaths(this.getDeaths()+1);
		//rest is called on respawn event to avoid people stalling on death screen
	}


	/**
	 * @return the campfires
	 */
	public ArrayList<Campfire> getCampfires() {
		return campfires;
	}


	/**
	 * @param campfires the campfires to set
	 */
	public void setCampfires(ArrayList<Campfire> campfires) {
		this.campfires = campfires;
	}
	
	public void addNewCampfire(Campfire camp) {
		this.campfires.add(camp);
	}
	
}