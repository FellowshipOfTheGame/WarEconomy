/**
 * WeaponDictionary.java, a classe que sabe das arma tudo
 */
package war;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Dicionário de armas, que conhece todos os tipos de Weapons existentes.
 *
 * Armas são lidas de arquivos json, pra facilitar nossa vida =]
 */
public class WeaponDictionary {
	/// Estrutura que guarda todas as armas
	private HashMap<String, Weapon> dictionary;

	public WeaponDictionary () {
		dictionary = new HashMap<String, Weapon> ();
	}

	/**
	 * Lê armas de um arquivo JSON, as salvando no 'dictionary'
	 */
	public void readJSON (String filename) {
		JSONParser parser = new JSONParser ();

		try {
			JSONObject obj = (JSONObject) parser.parse (new FileReader (filename));

			// adiciona cada entrada do JSON como um Weapon
			AdicionaObjetos:
			for (Object entry : obj.entrySet ()) {
				Map.Entry weaponEntry = (Map.Entry) entry;
				JSONObject weaponObj = (JSONObject) weaponEntry.getValue ();

				String name = (String) weaponEntry.getKey ();

				// verifica se arma tem todos os campos necessários, senão não rola
				for (String key : Weapon.neededFields) {
					if (!weaponObj.containsKey (key)) {
						System.err.println ("[readJSON] @ " + filename + " - " + name + ": campo \""
								+ key + "\" não encontrado!");
						continue AdicionaObjetos;
					}
				}
				// pega as várias variáveis
				int basePrice =		((Long) weaponObj.get ("basePrice")).intValue ();
				int margin = 		((Long) weaponObj.get ("margin")).intValue ();
				int heatInc =		((Long) weaponObj.get ("heatInc")).intValue ();
				int size =			((Long) weaponObj.get ("size")).intValue ();
				int combEfecBonus =	((Long) weaponObj.get ("combEfecBonus")).intValue ();
				// e cria a Weapon
				dictionary.put (name, new Weapon (name, basePrice, margin, heatInc, size, combEfecBonus));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println ("Sorry dude, \"" + filename + "\" ain't available =/\n"
					+ e.getMessage ());
		}
		catch (IOException e) {
			e.printStackTrace ();
		}
		catch (ParseException e) {
			System.out.println ("Sorry dude, error parsing \"" + filename + "\" =/\n"
					+ e.getMessage ());
		}
		catch (Exception e) {
			System.out.println (e.getMessage ());
			e.printStackTrace ();
		}
	}

	public Collection<Weapon> getAllWeapons () {
		return dictionary.values ();
	}
}
