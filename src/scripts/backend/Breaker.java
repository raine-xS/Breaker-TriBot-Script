package scripts.backend;

import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import scripts.frontend.mainUI.MainUI;
import scripts.frontend.mainUI.MainUIController;

@TribotScriptManifest(
		name = "Breaker",
		author = "2024",
		category = "Tools",
		description = "A breaker intended for use with script queue."
)
public class Breaker implements TribotScript {

	@Override
	public void execute(final String args) {
		// mainUI.setSceneStyles("scripts/frontend/mainUI/MainUIStyles.css");
		MainUI mainUI = MainUI.newMainUIWithTitleStylesheetIcon("Breaker", "scripts/resources/MainUIStyles.css", "scripts/resources/icon.png");
		MainUIController mainUIController = new MainUIController(mainUI);
		mainUI.show();


		while (mainUI.isOpen()) {
			Waiting.wait(1000);
		}

	}
}
