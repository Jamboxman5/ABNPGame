package me.jamboxman5.abnpgame.assets.weapon.mods;

import java.util.ArrayList;
import java.util.List;

public class WeaponModLoadout {

	List<WeaponMod> equippedMods;
	
	public WeaponModLoadout(List<WeaponMod> mods) {
		equippedMods = mods;
	}
	public WeaponModLoadout() {
		equippedMods = new ArrayList<>();
	}
	public void addMod(WeaponMod mod) { 
		if (!equippedMods.contains(mod)) equippedMods.add(mod);
	}
	public void removeMod(WeaponMod mod) {
		if (equippedMods.contains(mod)) equippedMods.remove(mod);
	}
	public List<WeaponMod> getMods() { return equippedMods; }
	public boolean hasRedDotSight() {
		for (WeaponMod mod : equippedMods) {
			if (mod instanceof RedDotSight) return true;
		}
		return false;
	}
	
}
