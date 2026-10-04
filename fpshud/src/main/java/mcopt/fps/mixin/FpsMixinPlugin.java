package mcopt.fps.mixin;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/**
 * The counter ships with mcopt Metal and follows its platform check (mcopt.metal.PlatformCheck, looked up by name: this
 * module doesn't build against it): wherever mcopt switches itself off, so does the counter. Without mcopt Metal it shows.
 */
public final class FpsMixinPlugin implements IMixinConfigPlugin {
	private static final boolean ENABLED = enabled();

	private static boolean enabled() {
		try {
			return (boolean) Class.forName("mcopt.metal.PlatformCheck", true, FpsMixinPlugin.class.getClassLoader()).getMethod("enabled").invoke(null);
		} catch (ClassNotFoundException alone) {
			return true;
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException("mcopt-fps: can't ask mcopt.metal.PlatformCheck", e);
		}
	}

	@Override public void onLoad(String mixinPackage) { }
	@Override public String getRefMapperConfig() { return null; }
	@Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return ENABLED; }
	@Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
	@Override public List<String> getMixins() { return null; }
	@Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
	@Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
