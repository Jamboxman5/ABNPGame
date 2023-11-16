package me.jamboxman5.abnpgame.data;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import me.jamboxman5.abnpgame.assets.entity.player.Player;
import me.jamboxman5.abnpgame.assets.weapon.Weapon;
import me.jamboxman5.abnpgame.assets.weapon.Weapon.WeaponType;
import me.jamboxman5.abnpgame.assets.weapon.WeaponLoadout;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponMod;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponMod.ModType;
import me.jamboxman5.abnpgame.assets.weapon.mods.WeaponModLoadout;
import me.jamboxman5.abnpgame.main.GamePanel;

public class ParseJson {

	public static Player loadLocalPlayer() {
		InputStreamReader isr = new InputStreamReader(ParseJson.class.getResourceAsStream("/me/jamboxman5/abnpgame/data/samplePlayerData.json"));
		JsonReader reader = new JsonReader(isr);
		JsonObject playerOBJ = JsonParser.parseReader(reader).getAsJsonObject();
		
		String name = playerOBJ.get("username").getAsString();
		int money = playerOBJ.get("money").getAsInt();
		int exp = playerOBJ.get("experience").getAsInt();
		
		JsonObject weaponsOBJ = playerOBJ.get("weaponLoadout").getAsJsonObject();
		JsonArray weaponsArr = weaponsOBJ.get("weapons").getAsJsonArray();

		List<Weapon> weapons = new ArrayList<>();
		for (int i = 0; i < weaponsArr.size(); i++) {
			JsonObject weaponOBJ = weaponsArr.get(i).getAsJsonObject();
			WeaponType type = WeaponType.valueOf(weaponOBJ.get("type").getAsString());
			Weapon weapon = Weapon.getByType(type);
			JsonArray modsArr = weaponOBJ.get("mods").getAsJsonArray();
			WeaponModLoadout loadout = new WeaponModLoadout();
			for (int j = 0; j < modsArr.size(); j++) {
				JsonObject modOBJ = modsArr.get(j).getAsJsonObject();
				ModType modType = ModType.valueOf(modOBJ.get("type").getAsString());
				loadout.addMod(WeaponMod.getByType(modType));
			}
			weapon.setMods(loadout);
			weapons.add(weapon);
		}
		WeaponLoadout loadout = new WeaponLoadout(weapons);
		Player player = new Player(GamePanel.getInstance(), GamePanel.getInstance().getKeyHandler(), name);
		player.setWeaponLoadout(loadout);
		return player; 

	}
	
}
