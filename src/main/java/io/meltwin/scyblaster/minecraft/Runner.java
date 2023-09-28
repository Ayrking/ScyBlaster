package io.meltwin.scyblaster.minecraft;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.meltwin.scyblaster.common.OSInfos;
import io.meltwin.scyblaster.common.files.ResourceHandler;
import io.meltwin.scyblaster.config.Configs;
import io.meltwin.scyblaster.minecraft.dto.version.Rule;
import io.meltwin.scyblaster.minecraft.dto.version.Version;
import io.meltwin.scyblaster.types.ClassPath;
import io.meltwin.scyblaster.types.GameConfigs;

/**
 * Class that will run the minecraft process
 */
public class Runner {
    private static final Logger logger = LogManager.getLogger("Runner");
    private final @NotNull Configs configs;
    private final @NotNull Version version;
    private final @NotNull GameConfigs game;
    private final @NotNull StringBuilder classPath = new StringBuilder();

    private String arguments = "";

    /**
     * The runner class will be the one making the arguments and launching MC
     * 
     * @param configs the launcher configs
     * @param version the MC version configs
     * @param game    the game configs
     */
    public Runner(@NotNull Configs configs, @NotNull Version version, @Nullable GameConfigs game,
            @NotNull ClassPath cp) {
        this.configs = configs;
        this.version = version;
        this.game = (game == null) ? new GameConfigs() : game;
        configs.getMinecraftConfigs().setQuickPlayM("127.0.0.1");

        arguments = makeArguments(cp);
        launchGame();
    }

    private void launchGame() {
        try {
            logger.debug(
                    String.format("Launching MC with the command line %n%s",
                            "java " + arguments));
            Process proc = Runtime.getRuntime().exec("java " + arguments);
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();

            String outBytes = "";
            while (proc.isAlive()) {
                outBytes = new String(in.readAllBytes());
                if (!outBytes.isEmpty())
                    logger.debug(outBytes);
                outBytes = new String(err.readAllBytes());
                if (!outBytes.isEmpty())
                    logger.warn(outBytes);
            }
            logger.warn(String.format("Minecraft closed with code %d", proc.exitValue()));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * This function is in charge of making the arguments for launching minecraft
     * 
     * @return the arguments for launching MC
     */
    private String makeArguments(@NotNull ClassPath cp) {
        String result = "";
        StringBuilder builder = new StringBuilder();

        // JVM
        for (int idx = 0; idx < version.arguments.jvm.length; idx++) {
            for (int valIdx = 0; valIdx < version.arguments.jvm[idx].values.length; valIdx++) {
                boolean toCheck = true;
                for (Rule r : version.arguments.jvm[idx].rules) {
                    if (r.os != null) {
                        if ((r.os.name != null) && !r.os.name.equals(OSInfos.getOS())) {
                            toCheck = false;
                            break;
                        }
                        if ((r.os.arch != null) && !r.os.arch.equals(OSInfos.ARCH)) {
                            toCheck = false;
                            break;
                        }
                    }
                }
                result = "";
                if (toCheck)
                    result = makeJVMArguments(idx, valIdx, cp);
                builder.append(result);
            }
        }

        builder.append(" " + version.mainClass + " ");

        // Game configs
        for (int idx = 0; idx < version.arguments.game.length; idx++) {
            boolean hasValue = ((idx + 1) < version.arguments.game.length)
                    && !version.arguments.game[idx + 1].values[0].substring(0, 2).equals("-");
            for (int valIdx = 0; valIdx < version.arguments.game[idx].values.length; valIdx++) {
                boolean valHasValue = ((valIdx + 1) < version.arguments.game[idx].values.length)
                        && !version.arguments.game[idx].values[valIdx + 1].substring(0, 2).equals("-");

                boolean toCheck = true;
                for (Rule r : version.arguments.game[idx].rules) {
                    if (r.os != null) {
                        if ((r.os.name != null) && !r.os.name.equals(OSInfos.getOS())) {
                            toCheck = false;
                            break;
                        }
                        if ((r.os.arch != null) && !r.os.arch.equals(OSInfos.ARCH)) {
                            toCheck = false;
                            break;
                        }
                    }
                }

                result = "";
                if (toCheck && (hasValue || valHasValue)) {
                    result = makeGameArguments(idx, valIdx);
                    if (result.isEmpty())
                        result = makeAuthArguments(idx, valIdx);
                    if (result.isEmpty())
                        result = makeMCConfigsArguments(idx, valIdx);
                } else {
                    result = version.arguments.game[idx].values[valIdx];
                }

                builder.append(result);

                if (valHasValue)
                    valIdx++;
            }

            if (hasValue)
                idx++;
        }

        return builder.toString();
    }

    /**
     * Make the argument for all game-related tags
     * 
     * @param argIndex the argument with are parsing
     * @return the resulting argument
     */
    private String makeGameArguments(int argIndex, int valIdx) {
        switch (version.arguments.game[argIndex].values[valIdx]) {
            case MCArguments.USERNAME:
                return MCArguments.USERNAME + " " + game.playerName + " ";
            case MCArguments.VERSION:
                return MCArguments.VERSION + " " + version.id + " ";
            case MCArguments.GAME_DIR:
                return MCArguments.GAME_DIR + " " + ResourceHandler.getBaseDir() + " ";
            case MCArguments.ASSETS_DIR:
                return MCArguments.ASSETS_DIR + " " + ResourceHandler.getAssetsDir() + " ";
            case MCArguments.ASSETS_IDX:
                return MCArguments.ASSETS_IDX + " " + version.assets + " ";
            default:
                return "";
        }
    }

    private String makeAuthArguments(int argIndex, int valIdx) {
        switch (version.arguments.game[argIndex].values[valIdx]) {
            case MCArguments.UUID:
                return MCArguments.UUID + " N/A ";
            case MCArguments.ACCESS_TOKEN:
                return MCArguments.ACCESS_TOKEN + " aeef7bc935f9420eb6314dea7ad7e1e5 ";
            default:
                return "";
        }
    }

    private String makeMCConfigsArguments(int argIndex, int valIdx) {
        switch (version.arguments.game[argIndex].values[valIdx]) {
            case MCArguments.MULTIP_QUICKPLAY:
                return MCArguments.MULTIP_QUICKPLAY + " " + configs.getMinecraftConfigs().getQuickPlayM() + " ";
            case MCArguments.DEMO:
                return ((configs.getMinecraftConfigs().isDemo()) ? MCArguments.DEMO : "") + " ";
            default:
                return "";
        }
    }

    private String makeJVMArguments(int argIndex, int valIdx, @NotNull ClassPath cp) {
        switch (version.arguments.jvm[argIndex].values[valIdx]) {
            case MCArguments.LIBS_PATH:
            case MCArguments.JNA_TMP:
            case MCArguments.LWJGL_DIR:
            case MCArguments.NETTY_DIR:
                return version.arguments.jvm[argIndex].values[valIdx].replace(MCArguments.NAVTIVES_PATH,
                        ResourceHandler.getLibsDir()) + " ";

            case MCArguments.LAUNCHER_BRAND:
                return MCArguments.LAUNCHER_BRAND.replace(MCArguments.PLACEHOLDER_BRAND, "Scyblaster") + " ";
            case MCArguments.LAUNCHER_VERSION:
                return MCArguments.LAUNCHER_VERSION.replace(MCArguments.PLACEHOLDER_VERSION, "1") + " ";
            case MCArguments.CLASSPATH:
                cp.prepend(String.format("%1$s %2$s%3$s%4$s%3$s.jar", MCArguments.CLASSPATH,
                        ResourceHandler.getVersionsDir(),
                        version.id, OSInfos.SEPARATOR));
                return cp.getString();
            case MCArguments.PLACEHOLDER_CLASSPATH:
                return "";

            default:
                return version.arguments.jvm[argIndex].values[valIdx] + " ";
        }
    }
}
