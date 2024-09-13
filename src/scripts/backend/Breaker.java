package scripts.backend;

import javafx.stage.Stage;
import org.tribot.script.sdk.Log;
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
		///scripts/frontend/mainUI/MainUIStyles.css
		GUI mainUI = new MainUI();
		mainUI.show();
		mainUI.setTitle("ferwgfws");

		while (mainUI.isOpen()) {
			Waiting.wait(1000);
		}

	}
}
