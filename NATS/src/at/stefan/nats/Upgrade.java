package at.stefan.nats;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;
import at.alex.nats.Player;

public class Upgrade {

	int prices[];
	String usables[];
	int equipped[] = new int[2];

	private final String movespeedDescription = "Increases Movespeed \nof the spaceship.";
	private final String gunnerDescription = "Shuttle shoots more \nenemies automatically.";
	private final String shieldDescription = "Shield rebuilds faster.\n";
	private final String shotfrequenceDescription = "Increases Speed of \nShooting.";
	private final String shotspreadingDescription = "Increases scattering \n of Shots.";
	private final String stasisfieldDescription = "Paralyzes Enemies \na few seconds.";
	private final String turboDescription = "Shuttle breaks through \nmany enemies.";
	private final String deadlytrailDescription = "Shuttle leaves Firetrack \nbehind it.";
	private final String bombDescription = "Destroys all enemies.\n";

	UpgradeMenu upgradeMenu;

	Player player;

	Rectangle movespeedSelect;
	Rectangle gunnerSelect;
	Rectangle shieldSelect;
	Rectangle shotfrequenceSelect;
	Rectangle shotspreadingSelect;
	Rectangle stasisfieldSelect;
	Rectangle turboSelect;
	Rectangle deadlytrailSelect;
	Rectangle bombSelect;

	Sprite buy;
	Sprite equip;
	Sprite discard;

	Text stasisfieldText;
	Text turboText;
	Text deadlytrailText;
	Text bombText;

	ProgressBar movespeedProgress;
	ProgressBar gunnerProgress;
	ProgressBar shieldProgress;
	ProgressBar shotfrequenceProgress;
	ProgressBar shotspreadingProgress;

	Text header;
	Text level;
	Text description;
	Text price;
	Text resources;

	nats nats;

	Finals finals;

	public enum Buy {
		MOVESPEED, GUNNER, SHIELD, SHOTFREQUENCE, SHOTSPREADING, STASISFIELD, TURBO, DEADLYTRAIL, BOMB
	}

	Buy actual = Buy.MOVESPEED;

	public Upgrade(UpgradeMenu upgrade, Player pl, Rectangle a, Rectangle b,
			Rectangle c, Rectangle d, Rectangle e, Rectangle f, Rectangle g,
			Rectangle h, Rectangle i, Sprite j, Sprite k, Sprite l, Text m,
			Text n, Text o, Text p, ProgressBar v, ProgressBar w,
			ProgressBar x, ProgressBar y, ProgressBar z, Text header,
			Text level, Text description, Text price, Text resources, nats nats) {
		this.upgradeMenu = upgrade;
		this.player = pl;
		this.movespeedSelect = a;
		this.gunnerSelect = b;
		this.shieldSelect = c;
		this.shotfrequenceSelect = d;
		this.shotspreadingSelect = e;
		this.stasisfieldSelect = f;
		this.turboSelect = g;
		this.deadlytrailSelect = h;
		this.bombSelect = i;
		this.buy = j;
		this.equip = k;
		this.discard = l;
		this.stasisfieldText = m;
		this.turboText = n;
		this.deadlytrailText = o;
		this.bombText = p;
		this.movespeedProgress = v;
		this.gunnerProgress = w;
		this.shieldProgress = x;
		this.shotfrequenceProgress = y;
		this.shotspreadingProgress = z;
		this.header = header;
		this.level = level;
		this.description = description;
		this.price = price;
		this.resources = resources;
		this.nats = nats;

		finals = new Finals();

		prices = new int[4];
		prices[finals.stasisfield()] = 500; // Factor 1.5
		prices[finals.turbo()] = 750; // Factor 1.6
		prices[finals.deadlytrail()] = 1000; // Factor 1.7
		prices[finals.bomb()] = 1500; // Factor 1.8

		usables = new String[4];
		usables[finals.stasisfield()] = "discard";
		usables[finals.turbo()] = "discard";
		usables[finals.deadlytrail()] = "equip";
		usables[finals.bomb()] = "equip";

		equipped[0] = finals.stasisfield();
		equipped[1] = finals.turbo();
	}

	public void movespeedInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Speed");
		if (player.getPermanents(finals.movespeed()) == 5) {
			level.setText("MAX");
		} else {
			level.setText("Level: " + player.getPermanents(finals.movespeed()));
		}

		description.setText(movespeedDescription);
		price.setText("Upgrade: "
				+ pricePermanents(finals.cheap(),
						player.getPermanents(finals.movespeed())));
		resources.setText("Resources: \n" + player.getRessources());
		movespeedSelect.setVisible(true);
		actual = Buy.MOVESPEED;
	}

	public void gunnerInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Gunner");
		if (player.getPermanents(finals.gunner()) == 5) {
			level.setText("MAX");
		} else {
			level.setText("Level: " + player.getPermanents(finals.gunner()));
		}
		description.setText(gunnerDescription);
		price.setText("Upgrade: "
				+ pricePermanents(finals.cheap(),
						player.getPermanents(finals.gunner())));
		resources.setText("Resources: \n" + player.getRessources());
		gunnerSelect.setVisible(true);
		actual = Buy.GUNNER;
	}

	public void shieldInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Shield");
		if (player.getPermanents(finals.shield()) == 5) {
			level.setText("MAX");
		} else {
			level.setText("Level: " + player.getPermanents(finals.shield()));
		}
		description.setText(shieldDescription);
		price.setText("Upgrade: "
				+ pricePermanents(finals.expensive(),
						player.getPermanents(finals.shield())));
		resources.setText("Resources: \n" + player.getRessources());
		shieldSelect.setVisible(true);
		actual = Buy.SHIELD;
	}

	public void shotfrequenceInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Shot Frequency");
		if (player.getPermanents(finals.shotfrequence()) == 5) {
			level.setText("MAX");
		} else {
			level.setText("Level: "
					+ player.getPermanents(finals.shotfrequence()));
		}
		description.setText(shotfrequenceDescription);
		price.setText("Upgrade: "
				+ pricePermanents(finals.moderate(),
						player.getPermanents(finals.shotfrequence())));
		resources.setText("Resources: \n" + player.getRessources());
		shotfrequenceSelect.setVisible(true);
		actual = Buy.SHOTFREQUENCE;
	}

	public void shotspreadingInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Shot Spreading");
		if (player.getPermanents(finals.shotspreading()) == 5) {
			level.setText("MAX");
		} else {
			level.setText("Level: "
					+ player.getPermanents(finals.shotspreading()));
		}
		description.setText(shotspreadingDescription);
		price.setText("Upgrade: "
				+ pricePermanents(finals.moderate(),
						player.getPermanents(finals.shotspreading())));
		resources.setText("Resources: \n" + player.getRessources());
		shotspreadingSelect.setVisible(true);
		actual = Buy.SHOTSPREADING;
	}

	public void stasisfieldInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Stasis Field");
		level.setText("Amount: " + player.getUsables(finals.stasisfield()));
		description.setText(stasisfieldDescription);
		price.setText("Next Buy: " + priceUsables(finals.stasisfield()));
		resources.setText("Resources: \n" + player.getRessources());
		stasisfieldSelect.setVisible(true);
		actual = Buy.STASISFIELD;
		if (usables[finals.stasisfield()] == "equip"
				&& player.getUsables(finals.stasisfield()) > 0) {
			equip.setPosition(150, 60);
			equip.setVisible(true);
			upgradeMenu.registerSpriteInGame(equip);
		} else {
			discard.setPosition(150, 60);
			discard.setVisible(true);
			upgradeMenu.registerSpriteInGame(discard);
		}
	}

	public void turboInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Turbo");
		level.setText("Amount: " + player.getUsables(finals.turbo()));
		description.setText(turboDescription);
		price.setText("Next Buy: " + priceUsables(finals.turbo()));
		resources.setText("Resources: \n" + player.getRessources());
		turboSelect.setVisible(true);
		actual = Buy.TURBO;
		if (usables[finals.turbo()] == "equip"
				&& player.getUsables(finals.turbo()) > 0) {
			equip.setPosition(350, 60);
			equip.setVisible(true);
			upgradeMenu.registerSpriteInGame(equip);
		} else {
			discard.setPosition(350, 60);
			discard.setVisible(true);
			upgradeMenu.registerSpriteInGame(discard);
		}
	}

	public void deadlytrailInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Deadly Trail");
		level.setText("Amount: " + player.getUsables(finals.deadlytrail()));
		description.setText(deadlytrailDescription);
		price.setText("Next Buy: " + priceUsables(finals.deadlytrail()));
		resources.setText("Resources: \n" + player.getRessources());
		deadlytrailSelect.setVisible(true);
		actual = Buy.DEADLYTRAIL;
		if (usables[finals.deadlytrail()] == "equip"
				&& player.getUsables(finals.deadlytrail()) > 0) {
			equip.setPosition(550, 60);
			equip.setVisible(true);
			upgradeMenu.registerSpriteInGame(equip);
		} else {
			discard.setPosition(550, 60);
			discard.setVisible(true);
			upgradeMenu.registerSpriteInGame(discard);
		}
	}

	public void bombInfo() {
		this.deselect();
		this.hideEquipment();
		header.setText("Bomb");
		level.setText("Amount: " + player.getUsables(finals.bomb()));
		description.setText(bombDescription);
		price.setText("Next Buy: " + priceUsables(finals.bomb()));
		resources.setText("Resources: \n" + player.getRessources());
		bombSelect.setVisible(true);
		actual = Buy.BOMB;
		if (usables[finals.bomb()] == "equip"
				&& player.getUsables(finals.bomb()) > 0) {
			equip.setPosition(750, 60);
			equip.setVisible(true);
			upgradeMenu.registerSpriteInGame(equip);
		} else {
			discard.setPosition(750, 60);
			discard.setVisible(true);
			upgradeMenu.registerSpriteInGame(discard);
		}
	}

	private void deselect() {
		movespeedSelect.setVisible(false);
		gunnerSelect.setVisible(false);
		shieldSelect.setVisible(false);
		shotfrequenceSelect.setVisible(false);
		shotspreadingSelect.setVisible(false);
		stasisfieldSelect.setVisible(false);
		turboSelect.setVisible(false);
		deadlytrailSelect.setVisible(false);
		bombSelect.setVisible(false);
	}

	private void movespeed() {
		if (player.getPermanents(finals.movespeed()) < 5
				&& player.getRessources() >= pricePermanents(finals.cheap(),
						player.getPermanents(finals.movespeed()))) {
			movespeedProgress.increaseProgress();
			player.setPermanents(player.getPermanents(finals.movespeed()) + 1,
					finals.movespeed());
			player.setRessources(player.getRessources()
					- pricePermanents(finals.cheap(),
							player.getPermanents(finals.movespeed()) - 1));
			player.increaseSpeed();
		}
	}

	private void gunner() {
		if (player.getPermanents(finals.gunner()) < 5
				&& player.getRessources() >= pricePermanents(finals.cheap(),
						player.getPermanents(finals.gunner()))) {
			gunnerProgress.increaseProgress();
			player.setPermanents(player.getPermanents(finals.gunner()) + 1,
					finals.gunner());
			player.setRessources(player.getRessources()
					- pricePermanents(finals.cheap(),
							player.getPermanents(finals.gunner()) - 1));
			player.increaseGunner();
		}
	}

	private void shield() {
		if (player.getPermanents(finals.shield()) < 5
				&& player.getRessources() >= pricePermanents(
						finals.expensive(),
						player.getPermanents(finals.shield()))) {
			shieldProgress.increaseProgress();
			player.setPermanents(player.getPermanents(finals.shield()) + 1,
					finals.shield());
			player.increaseShield();
			player.setRessources(player.getRessources()
					- pricePermanents(finals.expensive(),
							player.getPermanents(finals.shield()) - 1));
		}
	}

	private void shotfrequence() {
		if (player.getPermanents(finals.shotfrequence()) < 5
				&& player.getRessources() >= pricePermanents(finals.moderate(),
						player.getPermanents(finals.shotfrequence()))) {
			shotfrequenceProgress.increaseProgress();
			player.setPermanents(
					player.getPermanents(finals.shotfrequence()) + 1,
					finals.shotfrequence());
			player.setRessources(player.getRessources()
					- pricePermanents(finals.moderate(),
							player.getPermanents(finals.shotfrequence()) - 1));
			player.increaseShotFrequence();
			// resources.setText("Resources: \n" + player.getRessources());
		}
	}

	private void shotspreading() {
		if (player.getPermanents(finals.shotspreading()) < 5
				&& player.getRessources() >= pricePermanents(finals.moderate(),
						player.getPermanents(finals.shotspreading()))) {
			shotspreadingProgress.increaseProgress();
			player.setPermanents(
					player.getPermanents(finals.shotspreading()) + 1,
					finals.shotspreading());
			player.setRessources(player.getRessources()
					- pricePermanents(finals.moderate(),
							player.getPermanents(finals.shotspreading()) - 1));
			player.increaseShotSpreading();
		}
	}

	private void stasisfield() {
		if (player.getUsables(finals.stasisfield()) < 9
				&& player.getRessources() >= priceUsables(finals.stasisfield())) {
			player.setUsables(player.getUsables(finals.stasisfield()) + 1,
					finals.stasisfield());
			stasisfieldText.setText("x"
					+ player.getUsables(finals.stasisfield()));
			player.setRessources(player.getRessources()
					- priceUsables(finals.stasisfield()));
			prices[finals.stasisfield()] = (int) (((int) (prices[finals
					.stasisfield()] * 1.5 / 100)) * 100 + 100);
		}
	}

	private void turbo() {
		if (player.getUsables(finals.turbo()) < 9
				&& player.getRessources() >= priceUsables(finals.turbo())) {
			player.setUsables(player.getUsables(finals.turbo()) + 1,
					finals.turbo());
			turboText.setText("x" + player.getUsables(finals.turbo()));
			player.setRessources(player.getRessources()
					- priceUsables(finals.turbo()));
			prices[finals.turbo()] = (int) (((int) (prices[finals.turbo()] * 1.6 / 100)) * 100 + 100);
		}
	}

	private void deadlytrail() {
		if (player.getUsables(finals.deadlytrail()) < 9
				&& player.getRessources() >= priceUsables(finals.deadlytrail())) {
			player.setUsables(player.getUsables(finals.deadlytrail()) + 1,
					finals.deadlytrail());
			deadlytrailText.setText("x"
					+ player.getUsables(finals.deadlytrail()));
			player.setRessources(player.getRessources()
					- priceUsables(finals.deadlytrail()));
			prices[finals.deadlytrail()] = (int) (((int) (prices[finals
					.deadlytrail()] * 1.7 / 100)) * 100 + 100);
		}
	}

	private void bomb() {
		if (player.getUsables(finals.bomb()) < 9
				&& player.getRessources() >= priceUsables(finals.bomb())) {
			player.setUsables(player.getUsables(finals.bomb()) + 1,
					finals.bomb());
			bombText.setText("x" + player.getUsables(finals.bomb()));
			player.setRessources(player.getRessources()
					- priceUsables(finals.bomb()));
			prices[finals.bomb()] = (int) (((int) (prices[finals.bomb()] * 1.8 / 100)) * 100 + 100);
		}
	}

	public void buy() {
		switch (this.actual) {
		case MOVESPEED:
			this.movespeed();
			this.movespeedInfo();
			break;
		case GUNNER:
			this.gunner();
			this.gunnerInfo();
			break;
		case SHIELD:
			this.shield();
			this.shieldInfo();
			break;
		case SHOTFREQUENCE:
			this.shotfrequence();
			this.shotfrequenceInfo();
			break;
		case SHOTSPREADING:
			this.shotspreading();
			this.shotspreadingInfo();
			break;
		case STASISFIELD:
			this.stasisfield();
			this.stasisfieldInfo();
			break;
		case TURBO:
			this.turbo();
			this.turboInfo();
			break;
		case DEADLYTRAIL:
			this.deadlytrail();
			this.deadlytrailInfo();
			break;
		case BOMB:
			this.bomb();
			this.bombInfo();
			break;
		}
	}

	private int pricePermanents(int permanent, int level) {
		int price = 0;

		if (permanent == finals.cheap()) {
			switch (level) {
			case 0:
				price = 100;
				break;
			case 1:
				price = 250;
				break;
			case 2:
				price = 500;
				break;
			case 3:
				price = 1000;
				break;
			case 4:
				price = 2000;
				break;
			}
		} else if (permanent == finals.moderate()) {
			switch (level) {
			case 0:
				price = 150;
				break;
			case 1:
				price = 400;
				break;
			case 2:
				price = 1000;
				break;
			case 3:
				price = 2250;
				break;
			case 4:
				price = 3500;
				break;
			}
		} else if (permanent == finals.expensive()) {
			switch (level) {
			case 0:
				price = 250;
				break;
			case 1:
				price = 600;
				break;
			case 2:
				price = 1500;
				break;
			case 3:
				price = 2750;
				break;
			case 4:
				price = 4500;
				break;
			}
		}

		Log.i("NATS", "Permanent costs " + price);

		return price;
	}

	private int priceUsables(int usables) {

		int price = 0;
		if (usables == finals.stasisfield()) {
			price = prices[finals.stasisfield()];
			// prices[finals.stasisfield()] = (int) (price * 1.5);
		} else if (usables == finals.turbo()) {
			price = prices[finals.turbo()];
			// prices[finals.turbo()] = (int) (price * 1.6);
		} else if (usables == finals.deadlytrail()) {
			price = prices[finals.deadlytrail()];
			// prices[finals.deadlytrail()] = (int) (price * 1.7);
		} else if (usables == finals.bomb()) {
			price = prices[finals.bomb()];
			// prices[finals.bomb()] = (int) (price * 1.8);
		}

		Log.i("NATS", "Usable costs " + price);
		return price;
	}

	public void actualizeResources() {
		resources.setText("Resources: \n" + player.getRessources());
	}

	private void hideEquipment() {
		equip.setVisible(false);
		discard.setVisible(false);
		upgradeMenu.unregisterSpriteInGame(equip);
		upgradeMenu.unregisterSpriteInGame(discard);
	}

	public void equip() {
		switch (this.actual) {
		case STASISFIELD:
			if (player.getUsables(finals.stasisfield()) == 0) { // wenn kein
																// Item
																// verfügbar ist
				Log.i("NATS", "stasisfield-kein Item vorhanden");
				// break;
			} else if (equipped[0] == -1) {
				equipped[0] = finals.stasisfield();
				usables[finals.stasisfield()] = "discard";
			} else if (equipped[1] == -1) {
				equipped[1] = finals.stasisfield();
				usables[finals.stasisfield()] = "discard";
			} else {
				Log.i("Usable", "equip stasisfield, no slot");
				// Toast.makeText(nats.getBaseContext(), "Both Slots in Use!",
				// Toast.LENGTH_SHORT).show();
			}
			this.stasisfieldInfo();
			break;
		case TURBO:
			if (player.getUsables(finals.turbo()) == 0) { // wenn kein Item
															// verfügbar ist
				Log.i("NATS", "turbo-kein Item vorhanden");
				// break;
			} else if (equipped[0] == -1) {
				equipped[0] = finals.turbo();
				usables[finals.turbo()] = "discard";
			} else if (equipped[1] == -1) {
				equipped[1] = finals.turbo();
				usables[finals.turbo()] = "discard";
			} else {
				Log.i("Usable", "equip turbo, no slot");
				// Toast.makeText(nats.getBaseContext(), "Both Slots in Use!",
				// Toast.LENGTH_SHORT).show();
			}
			this.turboInfo();
			break;
		case DEADLYTRAIL:
			if (player.getUsables(finals.deadlytrail()) == 0) { // wenn kein
																// Item
																// verfügbar ist
				Log.i("NATS", "deadlytrail-kein Item vorhanden");
				// break;
			} else if (equipped[0] == -1) {
				equipped[0] = finals.deadlytrail();
				usables[finals.deadlytrail()] = "discard";
			} else if (equipped[1] == -1) {
				equipped[1] = finals.deadlytrail();
				usables[finals.deadlytrail()] = "discard";
			} else {
				Log.i("Usable", "equip deadlytrail, no slot");
				// Toast.makeText(nats.getBaseContext(), "Both Slots in Use!",
				// Toast.LENGTH_SHORT).show();
			}
			this.deadlytrailInfo();
			break;
		case BOMB:
			if (player.getUsables(finals.bomb()) == 0) { // wenn kein Item
															// verfügbar ist
				Log.i("NATS", "bomb-kein Item vorhanden");
				// break;
			} else if (equipped[0] == -1) {
				equipped[0] = finals.bomb();
				usables[finals.bomb()] = "discard";
			} else if (equipped[1] == -1) {
				equipped[1] = finals.bomb();
				usables[finals.bomb()] = "discard";
			} else {
				Log.i("Usable", "equip bomb, no slot");
				// Toast.makeText(nats.getBaseContext(), "Both Slots in Use!",
				// Toast.LENGTH_SHORT).show();
			}
			this.bombInfo();
			break;
		default:

		}
		upgradeMenu.actualizeEquipment(equipped);
	}

	public void discard() {
		Log.i("Usable", "actual = " + this.actual);
		switch (this.actual) {
		case STASISFIELD:
			Log.i("Usable", "case stasisfield");
			if (equipped[0] == finals.stasisfield()) {
				Log.i("Usable", "equipped-0");
				equipped[0] = -1;
				usables[finals.stasisfield()] = "equip";
			} else if (equipped[1] == finals.stasisfield()) {
				Log.i("Usable", "equipped-1");
				equipped[1] = -1;
				usables[finals.stasisfield()] = "equip";
			} else {
				Log.i("Usable", "else");
				Log.i("Usable", "discard stasisfield, no slot");
				// Toast.makeText(nats.getBaseContext(), "Item isn't equipped!",
				// Toast.LENGTH_SHORT).show();
			}
			this.stasisfieldInfo();
			break;
		case TURBO:
			Log.i("Usable", "case turbo");
			if (equipped[0] == finals.turbo()) {
				equipped[0] = -1;
				usables[finals.turbo()] = "equip";
			} else if (equipped[1] == finals.turbo()) {
				equipped[1] = -1;
				usables[finals.turbo()] = "equip";
			} else {
				Log.i("Usable", "discard turbo, no slot");
				// Toast.makeText(nats.getBaseContext(), "Item isn't equipped!",
				// Toast.LENGTH_SHORT).show();
			}
			this.turboInfo();
			break;
		case DEADLYTRAIL:
			Log.i("Usable", "case deadlytrail");
			if (equipped[0] == finals.deadlytrail()) {
				equipped[0] = -1;
				usables[finals.deadlytrail()] = "equip";
			} else if (equipped[1] == finals.deadlytrail()) {
				equipped[1] = -1;
				usables[finals.deadlytrail()] = "equip";
			} else {
				Log.i("Usable", "discard deadlytrail, no slot");
				// Toast.makeText(nats.getBaseContext(), "Item isn't equipped!",
				// Toast.LENGTH_SHORT).show();
			}
			this.deadlytrailInfo();
			break;
		case BOMB:
			Log.i("Usable", "case bomb");
			if (equipped[0] == finals.bomb()) {
				equipped[0] = -1;
				usables[finals.bomb()] = "equip";
			} else if (equipped[1] == finals.bomb()) {
				equipped[1] = -1;
				usables[finals.bomb()] = "equip";
			} else {
				Log.i("Usable", "discard bomb, no slot");
				// Toast.makeText(nats.getBaseContext(), "Item isn't equipped!",
				// Toast.LENGTH_SHORT).show();
			}
			this.bombInfo();
			break;
		default:

		}
		Log.i("Usable", "actualizeEquipment");
		upgradeMenu.actualizeEquipment(equipped);
	}

	public int[] getEquipped() {
		return equipped;
	}

	public void setUsableEquip(int pos) {
		usables[pos] = "equip";
	}
	
	public void reset() {
		prices[finals.stasisfield()] = 500; // Factor 1.5
		prices[finals.turbo()] = 750; // Factor 1.6
		prices[finals.deadlytrail()] = 1000; // Factor 1.7
		prices[finals.bomb()] = 1500; // Factor 1.8

		usables[finals.stasisfield()] = "discard";
		usables[finals.turbo()] = "discard";
		usables[finals.deadlytrail()] = "equip";
		usables[finals.bomb()] = "equip";

		equipped[0] = finals.stasisfield();
		equipped[1] = finals.turbo();
	}

}
