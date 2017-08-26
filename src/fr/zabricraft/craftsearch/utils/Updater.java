/*
 *  Copyright (C) 2017 Nathan FALLET, Michaël NASS and Jean-Baptiste EJARQUE
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 */

package fr.zabricraft.craftsearch.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.zabricraft.craftsearch.CraftSearch;

public class Updater {

	public static void checkForUpdate(Player... receive) {
		try {
			String url = "https://www.craftsearch.eu/plugin/checkforupdate.php?version="
					+ CraftSearch.getInstance().getDescription().getVersion();

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", CraftSearch.getInstance().getDescription().getName() + "/"
					+ CraftSearch.getInstance().getDescription().getVersion());

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			String msg = response.toString();

			if (!msg.isEmpty()) {
				msg = ChatColor.translateAlternateColorCodes('&', msg);
				if (receive.length != 0) {
					for (Player p : receive) {
						p.sendMessage(msg);
					}
				} else {
					CraftSearch.getInstance().getLogger().warning(msg);
				}
			}
		} catch (Exception e) {
		}
	}

}
