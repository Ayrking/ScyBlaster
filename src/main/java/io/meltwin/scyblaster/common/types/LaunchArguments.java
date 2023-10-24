package io.meltwin.scyblaster.common.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jetbrains.annotations.NotNull;

/**
 * Launch arguments container.
 * Store thems inside a hash map as (tag, args[]) where:
 * tag is the value to replace (e.g. "${classpath}"),
 * args[] is a List of the arguments using this tag (e.g. "-cp ${classpath}")
 * in where you have to replace the tag
 */
public final class LaunchArguments {

    private static final char SPACE_CHAR = ' ';
    private final StringBuilder builder = new StringBuilder();
    private final HashMap<String, List<String>> arguments = new HashMap<>();

    // ====================================================================
    // Arguments list manipulation
    // ====================================================================

    /**
     * Append a portion of a launching argument to te internal string builder for
     * further computations
     * 
     * @param str the string to add
     */
    public final void stackString(@NotNull String str) {
        builder.append(str + SPACE_CHAR);
    }

    /**
     * Process the internal stack and add everything to the object
     */
    public final void processStringStack() {
        addArg(builder.toString());
        builder.setLength(0);
    }

    /**
     * Add a launching argument to the arguments list
     * 
     * @param tag       the value to replace (e.g. ${classpath})
     * @param launching the complete argument string where you
     *                  have to replace the value within
     */
    public final void addArg(final String tag, final String launching) {
        if (!arguments.containsKey(tag))
            arguments.put(tag, new ArrayList<>());
        arguments.get(tag).add(launching);
    }

    /**
     * Add a complete launching arguments string to the object
     * 
     * @param launchingArg a complete argument string (e.g. "${auth_player_name}
     *                     ${auth_session} --gameDir ${game_directory} ...")
     */
    public final void addArg(final String launchingArg) {
        for (Pair<String, String> argument : cutString(launchingArg))
            addArg(argument.getFirst(), argument.getSecond());
    }

    // ====================================================================
    // Utility
    // ====================================================================
    /**
     * Cut the given string in arguments for launching
     * 
     * @param args the string that we are working on
     * @return a list of pair (tag, arg) with: tag the value to replace (e.g.
     *         ${classpath}), and arg the complete argument string that you have to
     *         replace the value within
     */
    private final List<Pair<String, String>> cutString(@NotNull String args) {
        ArrayList<Pair<String, String>> out = new ArrayList<>();
        boolean insideArg = false;
        boolean insideTag = false;
        int startArg = 0;
        int startTag = 0;

        // Cutting the string
        for (int idx = 0; idx < args.length(); idx++) {
            // Incrementing what needs to be
            if (!insideTag)
                startTag = idx;

            // Test begin / end of each region
            if (!insideArg && args.charAt(idx) == '-') {
                // New argument
                startArg = idx;
                insideArg = true;
            } else if (!insideTag && args.charAt(idx) == '$') {
                // New tag
                if (!insideArg)
                    startArg = idx;
                startTag = idx;
                insideTag = true;
                insideArg = true;
            } else if (args.charAt(idx) == '}') {
                // End of tag
                out.add(new Pair<>(args.substring(startTag, idx + 1), args.substring(startArg, idx + 1).trim()));
                insideArg = false;
                insideTag = false;
            } else if (insideArg
                    && ((args.charAt(idx) == '-') && (args.charAt(idx - 1) == ' ') || (idx == args.length() - 1))) {
                // New arg without tag
                out.add(new Pair<>(args.substring(startTag, idx), args.substring(startArg, idx).trim()));
                insideArg = false;
                insideTag = false;
            }
        }
        return out;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("LaunchArguments [\n");
        for (Entry<String, List<String>> entry : this.arguments.entrySet()) {
            output.append("\t- \"" + entry.getKey() + "\"\n");
            for (String arg : entry.getValue()) {
                output.append("\t\t\"" + arg + "\"\n");
            }
        }
        output.append("]");
        return output.toString();
    }
}
