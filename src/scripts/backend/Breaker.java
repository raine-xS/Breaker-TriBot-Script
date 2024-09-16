package scripts.backend;

import com.sun.tools.javac.Main;
import org.tribot.script.sdk.Waiting;
import org.tribot.script.sdk.script.TribotScript;
import org.tribot.script.sdk.script.TribotScriptManifest;
import scripts.frontend.GUI;
import scripts.frontend.mainUI.MainUI;

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
		GUI mainUI = MainUI.newMainUIWithTitleStylesheetIcon("Breaker", "scripts/resources/MainUIStyles.css", "scripts/resources/icon.png");
		mainUI.show();


		while (mainUI.isOpen()) {
			Waiting.wait(1000);
		}

	}
}
