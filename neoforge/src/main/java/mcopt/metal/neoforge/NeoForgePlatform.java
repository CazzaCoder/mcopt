package mcopt.metal.neoforge;

import java.nio.file.Path;
import mcopt.metal.Platform;
import net.neoforged.fml.loading.FMLLoader;

public final class NeoForgePlatform implements Platform {
	@Override
	public Path gameDir() {
		return FMLLoader.getCurrent().getGameDir();
	}

	/** From the loading mod list, so it answers in mixin config plugins too, before mods are constructed. */
	@Override
	public boolean isModLoaded(String id) {
		return FMLLoader.getCurrent().getLoadingModList().getModFileById(id) != null;
	}

	@Override
	public boolean isDedicatedServer() {
		return FMLLoader.getCurrent().getDist().isDedicatedServer();
	}
}
