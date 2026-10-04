package mcopt.metal.fabric;

import java.nio.file.Path;
import mcopt.metal.Platform;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.gui.FabricGuiEntry;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

public final class FabricPlatform implements Platform {
	@Override
	public Path gameDir() {
		return FabricLoader.getInstance().getGameDir();
	}

	@Override
	public boolean isModLoaded(String id) {
		return FabricLoader.getInstance().isModLoaded(id);
	}

	/** To the log and to Fabric's own error dialog (the one it shows for a missing dependency), then the game exits. */
	@Override
	public void fatal(String message) {
		Log.error(LogCategory.GENERAL, message);
		try {
			FabricGuiEntry.displayError(message, null, true); // exits the game
		} catch (Throwable t) {
			throw new IllegalStateException(message, t);
		}
		throw new IllegalStateException(message);
	}
}
