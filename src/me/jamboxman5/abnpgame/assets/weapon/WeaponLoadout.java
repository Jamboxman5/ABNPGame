package me.jamboxman5.abnpgame.assets.weapon;

import java.util.ArrayList;
import java.util.List;

import me.jamboxman5.abnpgame.assets.weapon.firearms.Firearm;
import me.jamboxman5.abnpgame.assets.weapon.firearms.pistol.Pistol1911;
import me.jamboxman5.abnpgame.assets.weapon.firearms.rifle.RifleM4A1;
import me.jamboxman5.abnpgame.assets.weapon.firearms.shotgun.ShotgunWinchester12;

public class WeaponLoadout {

	List<Weapon> weapons;
	Weapon activeWeapon;
	
	public WeaponLoadout() {
		weapons = new ArrayList<>();
		weapons.add(new RifleM4A1());
		weapons.add(new Pistol1911());
		weapons.add(new ShotgunWinchester12());
		activeWeapon = weapons.get(0);
	}
	public WeaponLoadout(List<Weapon> weapons) {
		this.weapons = weapons;
		if (!weapons.isEmpty()) activeWeapon = weapons.get(0);
	}
	public void nextWeapon() {
		int idx = weapons.indexOf(activeWeapon) + 1;
		if (idx >= weapons.size()) idx = 0;
		activeWeapon = weapons.get(idx);
	}
	public void previousWeapon() {
		int idx = weapons.indexOf(activeWeapon) - 1;
		if (idx < 0) idx = weapons.size()-1;
		activeWeapon = weapons.get(idx);
	}
	public void addWeapon(Weapon newWeapon, boolean makeActive) {
		weapons.add(newWeapon);
		if (makeActive) {
			activeWeapon = newWeapon;
		}
	}
	public void removeWeapon(Weapon toRemove) {
		if (!weapons.contains(toRemove)) return;
		if (activeWeapon.equals(toRemove)) {
			previousWeapon();
		}
		weapons.remove(toRemove);
	}
	public Weapon getActiveWeapon() { return activeWeapon; }
	public Firearm getActiveFirearm() {
		if (activeWeapon instanceof Firearm f) {
			return f;
		} else return null;
	}
}
