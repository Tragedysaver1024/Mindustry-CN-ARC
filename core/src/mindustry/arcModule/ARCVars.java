package mindustry.arcModule;

import arc.Core;
import arc.Events;
import arc.assets.Loadable;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.arcModule.toolpack.ARCTeam;
import mindustry.arcModule.ui.ARCUI;
import mindustry.core.Version;
import mindustry.game.EventType;
import mindustry.game.Gamemode;
import mindustry.game.Team;

import static arc.Core.settings;

public class ARCVars implements Loadable {
    public static ARCUI arcui = new ARCUI();
    public static final int minimapSize = 40;
    public static boolean unitHide = false;
    public static boolean limitUpdate = false;
    public static int limitDst = 0;

    /** ARC */
    public static String arcVersion = Version.arcBuild <= 0 ? "dev" : String.valueOf(Version.arcBuild);
    public static String arcVersionPrefix = "<ARC~" + arcVersion + ">";
    public static int changeLogRead = 18;
    public static Seq<District.advDistrict> districtList = new Seq<>();
    /** 服务器远程控制允许或移除作弊功能 */
    public static boolean arcCheatServer = false;
    public static boolean replaying = false;
    public static ReplayController replayController;
    public static ARCClient arcClient = new ARCClient();

    public static boolean arcInfoControl = false;

    /** Control */
    public static boolean quickBelt;

    /** UI */
    public static boolean arcSelfName;
    public static boolean arcHideName;
    public static boolean payloadPreview;

    public static final int maxBuildPlans = 100;

    public static ARCTeam arcTeam = new ARCTeam();

    static {
        // 减少性能开销
        Events.run(EventType.Trigger.update, () -> {
            arcInfoControl = !arcCheatServer && (Core.settings.getBool("showOtherTeamState") ||
                    Vars.player.team().id == 255 || Vars.state.rules.mode() != Gamemode.pvp);
            arcSelfName = settings.getBool("arcSelfName");
            arcHideName = settings.getBool("arcHideName");
            payloadPreview = settings.getBool("payloadpreview");

            quickBelt = settings.getBool("quickBelt");
        });
    }

    public static int getMaxSchematicSize(){
        int s = Core.settings.getInt("maxSchematicSize");
        return s == 501 ? Integer.MAX_VALUE : s;
    }

    public static int getMinimapSize(){
        return settings.getInt("minimapSize",minimapSize);
    }

    public static String getThemeColorCode(){
        return "[#" + getThemeColor() + "]";
    }

    public static Color getThemeColor(){
        try {
            return Color.valueOf(settings.getString("themeColor"));
        }catch(Exception e){
            return Color.valueOf("ffd37f");
        }
    }

    public static Color getPlayerEffectColor(){
        try {
            return Color.valueOf(settings.getString("playerEffectColor"));
        }catch(Exception e){
            return Color.valueOf("ffd37f");
        }
    }

    public static Boolean arcInfoControl(Team team){
        return team == Vars.player.team() || arcInfoControl;
    }
}
