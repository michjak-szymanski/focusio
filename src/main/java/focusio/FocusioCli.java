package focusio;

import focusio.commands.MainCommand;
import picocli.CommandLine;

/**
 * ### focusio start *[-q,--quiet][-c,--config]*
 * *`Started 2fshc64`*
 * <p>
 * ### focusio stop *`2fshc643`*
 * *`Stopped 2fshc643`*
 * <p>
 * ### focusio pause *`2fshc643`*
 * *`Paused 2fshc643`*
 * <p>
 * ### focusio list [-r,--running][-p,--paused][-f, --finished][-t,--time=(human|millis)]
 * [ id | duration | remaining | state ]
 * 2fshc643 25m (24m 30s) running
 */
public class FocusioCli {

    public static void main(String[] args) {
        new CommandLine(new MainCommand())
                .execute(args);
    }
}
