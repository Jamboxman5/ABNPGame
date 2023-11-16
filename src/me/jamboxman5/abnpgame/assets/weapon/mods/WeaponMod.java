package me.jamboxman5.abnpgame.assets.weapon.mods;

public class WeaponMod {
	
	protected double accuracyModifier;
	protected double damageModifier;
	protected double fireRateModifier;
	protected double bulletSpreadModifier;
	protected double rangeModifier;

	public enum ModType {
		RedDotSight, Silencer;
	}
	
	public static WeaponMod getByType(ModType type) {
		switch(type) {
		case RedDotSight:
			return new RedDotSight();
		case Silencer:
			return new Silencer();
		}
		return new RedDotSight();
	}
	
}
