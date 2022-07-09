package bms.player.beatoraja.config;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class Discord {
    public static final DiscordRichPresence presence = new DiscordRichPresence();

    private static final DiscordRPC lib = DiscordRPC.INSTANCE;

    private static final String APPLICATIONID = "995325656999137430"; // DISCORD APPLICATION ID   (https://discord.com/developers/applications)

    public String details;

    public String state;

    public Discord(String details, String state) {
        this.details = details;
        this.state = state;
    }

    public void startup() {
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(APPLICATIONID, handlers, true, steamId);
        DiscordRichPresence presence = new DiscordRichPresence();
        lib.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {
                }
            }
        }, "RPC-Callback-Handler").start();

    }

    public void update() {
        presence.largeImageKey = "lr2";
        presence.largeImageText = "LR2oraja";
        presence.state = state;
        presence.details = details;
        lib.Discord_UpdatePresence(presence);
    }

}
