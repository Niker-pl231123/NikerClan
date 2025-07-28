package dev.nikee;

import dev.nikee.handlers.InvalidUsageHandler;
import dev.nikee.managers.MongoDbManager;
import dev.nikee.utils.TextUtil;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.message.LiteMessages;
import org.bukkit.command.CommandSender;

public class Start {

    public static LiteCommands<CommandSender> liteCommands;
    public static MongoDbManager mongoDbManager;

    public void start() {
        Main.getInstance().getLogger().info("");
        Main.getInstance().getLogger().info("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        Main.getInstance().getLogger().info("┃                                                              ┃");
        Main.getInstance().getLogger().info("┃ ┌ Plugin: " + Main.getInstance().getDescription().getName() + "                                          ┃");
        Main.getInstance().getLogger().info("┃ ╞ Wersja: " + Main.getInstance().getDescription().getVersion() + "                                                ┃");
        Main.getInstance().getLogger().info("┃ └ Author: Niker                                              ┃");
        Main.getInstance().getLogger().info("┃                                                              ┃");
        Main.getInstance().getLogger().info("┃ Został włączony poprawnie!                                   ┃");
        Main.getInstance().getLogger().info("┃                                                              ┃");
        Main.getInstance().getLogger().info("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
        Main.getInstance().getLogger().info("");
//===========================================|| Commands ||===========================================\\

        this.liteCommands = LiteBukkitFactory.builder("fallback-prefix", Main.getInstance())
                .commands(
                        //register here your commands
                )
                .message(LiteMessages.MISSING_PERMISSIONS, permissions -> TextUtil.format("&#ff0000☹ &cNie posiadasz wymaganej permisji do wykonania tej komendy &#ff0000(" + permissions.asJoinedText() + ")"))
                .invalidUsage(new InvalidUsageHandler())
                .build();

//===========================================|| Listeners ||===========================================\\

//===========================================|| MangoDB ||===========================================\\

        String url = "mongodb://user:password@IP:port/?tls=false"; //Empty string for preodactyl
        String databaseName = "dataName";
        String collectionName = "CollectionName";
        mongoDbManager = new MongoDbManager(url, databaseName, collectionName);
        Main.getInstance().getLogger().info(TextUtil.format("Succeed connected with " + databaseName + " and whit " + collectionName));
    }

    public void stop() {
        mongoDbManager.closeConnection();
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }
}
