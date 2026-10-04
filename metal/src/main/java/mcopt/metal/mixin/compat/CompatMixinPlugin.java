package mcopt.metal.mixin.compat;

import java.util.List;
import java.util.Set;
import mcopt.metal.Platform;
import mcopt.metal.PlatformCheck;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/** Each compat mixin applies only where mcopt runs and the mod it is for is loaded (its target classes are that mod's). */
public final class CompatMixinPlugin implements IMixinConfigPlugin {
	@Override public void onLoad(String mixinPackage) { }
	@Override public String getRefMapperConfig() { return null; }

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (!PlatformCheck.enabled()) return false;
		if (mixinClassName.endsWith(".DhRenderApiMixin")) return Platform.get().isModLoaded("distanthorizons");
		return false;
	}

	@Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
	@Override public List<String> getMixins() { return null; }
	@Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
	@Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
