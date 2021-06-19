package focusio.commands;

import picocli.CommandLine.Command;

@Command(subcommands = {
        StartCommand.class,
        StopCommand.class,
        PauseCommand.class,
        ListCommand.class
})
public class MainCommand {
}
