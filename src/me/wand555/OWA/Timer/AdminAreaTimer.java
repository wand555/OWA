package me.wand555.OWA.Timer;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import me.wand555.OWA.Main.OWA;
import me.wand555.OWA.Player.AdminArea;
/**
 * A Zombie Camp zone is always stronger than a Safe Camp zone.
 * 
 * @author Felix
 *
 */
public class AdminAreaTimer extends BukkitRunnable {

	private ArrayList<Location> area;
	private int amount;
	
	public AdminAreaTimer(ArrayList<Location> area, int amount) {
		this.area = area;
		this.amount = amount;
	}

	@Override
	public void run() {
		System.out.println(OWA.RED + amount + OWA.RESET);
		outerloop:
		for(int j=0; j<amount; j++) {
			Location rLoc = area.get(ThreadLocalRandom.current().nextInt(0, area.size()));
			if(rLoc.getBlock().getType() == Material.AIR) {
				
				int i = rLoc.getBlockY()+1;
				while(i > 0) {
					Location l = new Location(rLoc.getWorld(), rLoc.getBlockX(), i, rLoc.getBlockZ());
					if(l.getBlock().getType() != Material.AIR) {
						
						for(AdminArea adminArea : AdminArea.getAdminAreas()) {
							if(adminArea.getArea().contains(l)) {
								System.out.println("avoided spawn due to safe camp");
								continue outerloop;
							}
						}
						//spawn mob here (at 'l')
						Zombie zombie = (Zombie) rLoc.getWorld().spawnEntity(l.getBlock().getRelative(BlockFace.UP).getLocation(), EntityType.ZOMBIE);
						zombie.setRemoveWhenFarAway(true);
						System.out.println(l.getBlockX() + " ABC  " + l.getBlockZ());
						continue outerloop;
					}
					i--;
				}
			}
			else {
				//try and move it up
				int i = rLoc.getBlockY()+1;
				while(i < 255) {
					Location l = new Location(rLoc.getWorld(), rLoc.getBlockX(), i, rLoc.getBlockZ());
					//when mob has space to spawn
					if(l.getBlock().getType() == Material.AIR && l.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR) {
						
						for(AdminArea adminArea : AdminArea.getAdminAreas()) {
							if(adminArea.getArea().contains(l)) {
								System.out.println("avoided spawn due to safe camp");
								continue outerloop;
							}
						}
						//spawn mob
						Zombie zombie = (Zombie) rLoc.getWorld().spawnEntity(l, EntityType.ZOMBIE);
						zombie.setRemoveWhenFarAway(true);
						System.out.println(l.getBlockX() + " DEF  " + l.getBlockZ());
						continue outerloop;
					}
					i++;
				}
			}
		}	
	}
}
